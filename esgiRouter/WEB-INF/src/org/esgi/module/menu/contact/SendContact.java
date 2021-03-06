package org.esgi.module.menu.contact;

import java.io.File;

import org.esgi.orm.ORM;
import org.esgi.orm.my.model.Communicate_with;
import org.esgi.tools.MapperAjax;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class SendContact extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		String nom = context.getRequest().getParameter("nom");
		String prenom = context.getRequest().getParameter("prenom");
		String sujet = context.getRequest().getParameter("sujet");
		String mail = context.getRequest().getParameter("email");
		
		Communicate_with cw = new Communicate_with();
		cw.nom = nom;
		cw.prenom = prenom;
		cw.email_address = mail;
		cw.subject = sujet;
		cw.is_done = 0;
				
		Object saveContact = ORM.save(cw);
		
		boolean isSave = saveContact != null;
		
		MapperAjax ma = new MapperAjax();
		ma.Add("success", isSave);
		
		if(!isSave)
			ma.Add("message", "<h2>Une erreur a �t� rencontr� pendant la sauvegarde de votre message.</h2>");
		
		ma.Write(context);
	}

	@Override
	public String getRoute() {
		return "/Contact/SendContact";
	}
	@Override
	public String getLayout() {
		return null;
	}

}
