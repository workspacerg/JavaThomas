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
import org.esgi.orm.my.model.Salle;
import org.esgi.orm.my.model.Seance;
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
				servletFileUpload.setHeaderEncoding("UTF-8");
				List<FileItem> fileList = servletFileUpload.parseRequest(context.getRequest());
				
				MapperAjax ma = new MapperAjax();

				FileItem toUploadAffiche = null;
				FileItem toUploadFond = null;
				
				Film f = new Film();
				Seance s = new Seance();
				String interval = "";
				
				for(FileItem fi : fileList){
					if(!fi.isFormField()){
						if(fi.getFieldName().equals("file"))
							toUploadAffiche = fi;
						else
							toUploadFond = fi;
					}
					else{
						String fieldname = fi.getFieldName();
						String fieldValue = fi.getString("UTF-8");
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
						else if(fieldname.equals("version")){
							s.version = fieldValue;
						}
						else if(fieldname.equals("seancePicker")){
							s.heure = fieldValue;
						}
						else if(fieldname.equals("intervalseancePicker")){
							interval = fieldValue;
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
				
				if(save != null){
					// Avant de gérer les salles
					Salle temp = new Salle();
					temp.id_salle = -1;
					s.film = save;
					s.salle = temp;
					ORM.save(s);
					int hour = Integer.parseInt(s.heure.split(":")[0]);
					int min = Integer.parseInt(s.heure.split(":")[1]);
					
					int hourInterval = Integer.parseInt(interval.split(":")[0]);
					int minInterval = Integer.parseInt(interval.split(":")[1]);
					
					while(true){
						hour += hourInterval;
						min += minInterval;
						if(min >= 60){
							min -= 60;
							hour+= 1;
						}
						
						if(hour >= 24)
							break;
						
						Seance newS = new Seance();
						newS.film = s.film;
						newS.heure = String.format("%d:%d", hour,min);
						newS.version = s.version;
						newS.salle = temp;
						ORM.save(newS);
					}
				}
				
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
