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
import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.User;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadFilm extends AbstractAction{
	@Override
	public String getRoute() {
		return "^/Admin/LoadFilm(?:/)?$";
	}
	
	@Override
	public String getLayout(){
		return null;
	}

	@Override
	public void execute(IContext context) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("estAffiche", true);
		List<Film> films = (List<Film>)(List<?>)ORM.find(Film.class, map,null,null,null);
		
		MapperAjax ma = new MapperAjax();
		
		if(films.size() > 0){
			StringBuilder htmlContent = new StringBuilder("<select style='width:300px' id='filmAffiche'>");
			for(Film f : films)
				htmlContent.append("<option value='"+f.id_film+"'>"+f.titre+"</option>");
			htmlContent.append("</select>");
			
			ma.Add("select", htmlContent.toString());
			ma.Add("success", true);
		}
		else{
			ma.Add("select", "<h2> Aucun film n'est à l'affiche actuellement </h2>");
			ma.Add("success", false);
		}
		
		ma.Write(context);
	}
	
}
