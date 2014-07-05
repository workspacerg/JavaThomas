package org.esgi.module.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.wsdl.writer.document.Part;

public class AddFilm extends AbstractAction{
	@Override
	public String getRoute() {
		return "^/Admin/AddFilm(?:/)?$";
	}
	
	@Override
	public String getLayout(){
		return null;
	}

	@Override
	public void execute(IContext context) throws Exception {	
		try{		
				FileItemFactory fif = new DiskFileItemFactory(); 
				ServletFileUpload servletFileUpload = new ServletFileUpload(fif);
				List<FileItem> fileList = servletFileUpload.parseRequest(context.getRequest());
				
				MapperAjax ma = new MapperAjax();

				FileItem toUploadAffiche = null;
				FileItem toUploadFond = null;
				Film f = new Film();
				
				for(FileItem fi : fileList){
					if(!fi.isFormField()){
						if(fi.getFieldName().equals("file"))
							toUploadAffiche = fi;
						else
							toUploadFond = fi;
					}
					else{
						String fieldname = fi.getFieldName();
						String fieldValue = fi.getString();
						if(fieldname.equals("realisateur")){
							f.realisateur = fieldValue;
						}
						else if(fieldname.equals("titre")){
							f.titre = fieldValue;
						}
						else if(fieldname.equals("genre")){
							f.genre = fieldValue;
						}
						else if(fieldname.equals("description")){
							f.description = fieldValue;
						}
					}
				}
				
				if(toUploadFond == null || toUploadAffiche == null){
					ma.Add("success", false);
					ma.Add("message", "<h2>Un fichier n'a pas été envoyé.</h2>");
					ma.Write(context);
					return;
				}
				
				f.estAffiche = true;
				Film save = (Film)ORM.save(f);
				
				boolean success = save != null && upload(context,toUploadAffiche,f,"affiche") && upload(context,toUploadFond,f,"fond") ;
				
				ma.Add("success", success);
				if(!success)
					ma.Add("message","<h2>Erreur durant l'enregistrement</h2>");
				
				ma.Write(context);
				
			}

		catch (FileUploadException fue){
			System.out.println("Exception: parseRequest a echoue");
			fue.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: write file a echoue");
			e.printStackTrace();
		}
	}
	
	private boolean upload(IContext context,FileItem i,Film f,String file){
		try{
			int len;
			byte[] buf;
			InputStream buff;
			
			File tmp;
				
			len = -1;
			buf = new byte[4096];
			OutputStream outStream;
			
			String extension = FilenameUtils.getExtension(i.getName());
	
			outStream = new FileOutputStream(new File(context.getProperties().get("real.path")+"/res/style/img/Films/"+file+"_"+f.id_film+"."+extension));
			buff = i.getInputStream();
			while ((len = buff.read(buf)) != -1)
				outStream.write(buf, 0, len);
	
			buff.close();
	
	
			outStream.flush();
			outStream.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("Exception: write file a echoue");
			e.printStackTrace();
			return false;
		}
	}
	
}
