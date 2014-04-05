package org.esgi.module.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;


public class FileDownload extends AbstractAction{
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	@Override
	public void execute(IContext context) throws Exception {
		// Pour éviter les erreurs
		Writer w = context.getResponse().getWriter();
		
		String path = (String) context.getParameter("path");
		File f = new File(context.getProperties().get("file.repository").toString() + "/" + path );
		String mimeType = getContentType(f.getPath());
		context.getResponse().reset();
		
		if(mimeType == null)
			mimeType = "application/octet-stream"; // Celui par défaut au cas ou
		
		context.getResponse().setContentType(mimeType);
		context.getResponse().setHeader( "Content-Disposition", "attachment; filename=\"" + f.getName() + "\"" );
		OutputStream outStream = context.getResponse().getOutputStream();
		
		int len = -1;
		byte buf[] = new byte[9192];
		InputStream buff = new FileInputStream(f.getPath());

		while ((len = buff.read(buf)) != -1)
			outStream.write(buf, 0, len);

		buff.close();


		outStream.flush();
		outStream.close();
	}
	
	private String getContentType(String filename)
	{
		String g = URLConnection.guessContentTypeFromName(filename);
		if( g == null)
		{
			g = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(filename);
		}
		return g;
	}

	@Override
	public String getRoute() {
		return "/file/list(/.+)?/download";
	}

	@Override
	public String[] getRewriteGroups() {
		return new String[]{"path"};
	}
	

}
