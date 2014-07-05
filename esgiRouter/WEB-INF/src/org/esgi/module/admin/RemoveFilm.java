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

public class RemoveFilm extends AbstractAction{
	@Override
	public String getRoute() {
		return "^/Admin/RemoveFilm(?:/)?$";
	}
	
	@Override
	public String getLayout(){
		return null;
	}

	@Override
	public void execute(IContext context) throws Exception {
		Integer idFilm = Integer.parseInt(context.getRequest().getParameter("idFilm"));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id_film", idFilm);
		
		Film f = (Film) ORM.load(Film.class, idFilm, null);
		f.estAffiche = false;
		Object save = ORM.save(f);
		
		MapperAjax ma = new MapperAjax();
		ma.Add("success", save != null);
		if(save == null)
			ma.Add("message", "Modification du film impossible");
		
		ma.Write(context);
	}
	
}
