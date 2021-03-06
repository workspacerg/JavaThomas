package org.esgi.web.layout;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.esgi.json.JSONExtractor;
import org.esgi.module.index.Comment;
import org.esgi.module.index.Index;
import org.esgi.module.index.Inscription;
import org.esgi.module.index.MovieDetail;
import org.esgi.module.menu.contact.Contact;
import org.esgi.module.menu.profil.CommentHistory;
import org.esgi.module.menu.profil.Information;
import org.esgi.module.menu.profil.Profil;
import org.esgi.web.action.IAction;
import org.esgi.web.action.IContext;
import org.esgi.web.route.Router;

public class LayoutRenderer {

	public void render(IAction action, IContext context, Router router) throws ServletException {

		File tplRepo = new File(context.getProperties().getProperty("real.path")+"view");

		String[] widgets = JSONExtractor.getDepencies(context.getProperties().get("real.path") + "layout");
		
		Properties p = new Properties();
		p.setProperty("file.resource.loader.path", (String) context.getProperties().getProperty("real.path")+"view");
		Velocity.init(p);
		
		boolean current = false;
		for (String widget : widgets) {
			
			String nameVelocity;
			String[] pathTabSplit = widget.split("/");
			String pathTab = "";
					

			current = false;
			if ("__CURRENT__".equals(widget)){
				current = true;
				String[] parts = action.getClass().getName().split("\\.");
				nameVelocity = parts[parts.length-2]+"/"+parts[parts.length-1];
				widget = action.getRoute();
			}
			else{
				
				nameVelocity = widget;
				for(int u = 0 ; u < pathTabSplit.length - 1 ; u++)
				{
					pathTab += "/" + pathTabSplit[u];
				}
				
			}
			
			IAction a = router.find(widget, context);

			if (null !=a)
				try {
					a.execute(context);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			if(context.getRequest().getSession().getAttribute("login")==null && current && (action instanceof Comment || action instanceof Profil || action instanceof Information || action instanceof CommentHistory))
				nameVelocity = "index/Connexion";

			context.addCSSDependancy((String)context.getProperties().get("file.css"));
			
			File tpl = new File(tplRepo, nameVelocity+".vm");
			System.out.println(tpl.getPath());
			VelocityContext contextv = new VelocityContext();
			contextv.put("context", context);
			if (tpl.exists()) {
				Template template = null;
				
				try
				{
					
					template = Velocity.getTemplate(nameVelocity+".vm") ;
					StringWriter sw = new StringWriter();

					template.merge(contextv,sw);
					context.setFragment(current ? "__CURRENT__" : widget, sw);
					
					//System.out.println(sw.toString());

				}
				catch(Exception E) { E.printStackTrace();}
				//	System.out.println(tpl.getPath());
				
				

			}
		}

	}



}
