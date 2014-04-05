package org.esgi.module.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class FileUpload extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(context.getRequest());
		Writer writer = context.getResponse().getWriter();
		if(isMultipart){
			//La factory qui cree des FileItem, pour lire les infos de fichiers:
			FileItemFactory fif = new DiskFileItemFactory(); 

			//La classe qui parse les headers a la recherche de fichiers uploads
			ServletFileUpload servletFileUpload = new ServletFileUpload(fif); 

			File f = new File((String) context.getProperties().get("file.repository"));
			String[] url = context.getRequest().getRequestURI().split("/file/upload/");
			
			String repo = url.length > 1 ? url[1] : ""; // Repertoire courant sinon
			
			//Lire les infos du fichier
			try{

				List<FileItem> fileList = servletFileUpload.parseRequest(context.getRequest());
				if(fileList.size() == 0)
					return;
				
				int len;
				byte[] buf;
				InputStream buff;
				
				File tmp;
				for(FileItem i : fileList){
					if(i.getName() == null)
						continue;
					
					tmp = new File(repo,i.getName());
					len = -1;
					buf = new byte[4096];
					OutputStream outStream;

					outStream = new FileOutputStream(new File(f,"/"+tmp.getPath()));
					buff = i.getInputStream();
					while ((len = buff.read(buf)) != -1)
						outStream.write(buf, 0, len);

					buff.close();


					outStream.flush();
					outStream.close();

				}

				String urlTmp = context.getRequest().getRequestURI(); 
				context.getResponse().sendRedirect(urlTmp.replace("upload","list").replaceAll("//", "/"));// C'est magnifique biensur !
			}
			catch (FileUploadException fue){
				System.out.println("Exception: parseRequest a echoue");
				fue.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception: write file a echoue");
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getRoute() {
		return "/file/upload(/.+)?/";
	}

	@Override
	public String[] getRewriteGroups() {
		return new String[]{"path"};
	}

}
