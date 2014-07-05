package org.esgi.web;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esgi.module.admin.AddAdmin;
import org.esgi.module.admin.AddFilm;
import org.esgi.module.admin.Admin;
import org.esgi.module.admin.LoadFilm;
import org.esgi.module.admin.RemoveFilm;
import org.esgi.module.file.FileDelete;
import org.esgi.module.file.FileDownload;
import org.esgi.module.file.FileList;
import org.esgi.module.file.FileUpload;
import org.esgi.module.index.*;
import org.esgi.module.menu.contact.*;
import org.esgi.module.menu.profil.CommentHistory;
import org.esgi.module.menu.profil.Information;
import org.esgi.module.menu.profil.Profil;
import org.esgi.module.menu.profil.SaveInformation;
import org.esgi.module.user.*;
import org.esgi.web.action.IAction;
import org.esgi.web.action.IContext;
import org.esgi.web.layout.LayoutRenderer;
import org.esgi.web.route.Router;

import com.googlecode.htmlcompressor.compressor.Compressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * Simulate an Servlet.
 * @author michaelthomas
 *
 */
public class FrontController extends HttpServlet{

	Router router = new Router();
	Properties properties = new Properties();
	
	LayoutRenderer layoutRender;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String configFile = config.getServletContext().getInitParameter("config");
		String path = config.getServletContext().getRealPath("/");

		try {
			properties.load(new FileInputStream(path +"/" + configFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

		properties.put("real.path", path);
		properties.put("context", config.getServletContext().getContextPath());
		
		//		for (String initName : Collections.list(config.getInitParameterNames())) {
		//			if(initName.lastIndexOf("module-") == 0){
		//				registerAction(Class.forName(config.getInitParameter(initName)));
		//			}
		//		}

		
		/*registerAction(new FileList());
		registerAction(new FileDownload());
		registerAction(new FileUpload());
		registerAction(new FileDelete());*/
		registerAction(new Contact());
		registerAction(new Index());
		registerAction(new Connect());
		registerAction(new Connexion());
		registerAction(new Delete());
		registerAction(new SaveInscription());
		registerAction(new Inscription());
		registerAction(new SendContact());
		registerAction(new MovieDetail());
		registerAction(new Comment());
		registerAction(new Information());
		registerAction(new Profil());
		registerAction(new AddFilm());
		registerAction(new AddAdmin());
		registerAction(new RemoveFilm());
		registerAction(new LoadFilm());
		registerAction(new CommentHistory());
		registerAction(new SaveInformation());
		registerAction(new SendComment());
		registerAction(new Admin());
		registerAction(new Logout());

		layoutRender = new LayoutRenderer();

	}
	@Override
	public void service(HttpServletRequest 
			request, HttpServletResponse response)
					throws ServletException, IOException {

		
		String url = request.getPathInfo();
		IContext context = createContext(request, response);

		System.out.println(url);
		IAction action = router.find(url, context);
		System.out.println(action);
		
		if (null != action){
			
			if (null == action.getLayout()) {
				
				try {
					action.execute(context);
				} catch (Exception e) {
					throw new ServletException(e);
				}
			}
			else {
				
				LayoutRenderer lr = new LayoutRenderer();

				lr.render(action, context, router);

				Writer writer = context.getResponse().getWriter();

				//Code non compresser

				//writer.append(context.getFragment("shared/Html").toString());	

				String html = context.getFragment("shared/Html").toString(); //your external method to get html from memory, file, url etc.

				Compressor compressor = new HtmlCompressor();

				String compressedHtml = compressor.compress(html);

				//Code compresser

				writer.append(compressedHtml);
				
				writer.close();
			}
		}


	}

	private IContext createContext(HttpServletRequest request, 
			HttpServletResponse response) {
		return new Context(request, response, properties);
	}

	public void registerAction(IAction action) {
		router.register(action);
	}
}
