package org.esgi.orm;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.Genre;
import org.esgi.orm.my.model.Identifiant;
import org.esgi.orm.my.model.Participant;
import org.esgi.orm.my.model.Realisateur;
import org.esgi.orm.my.model.Salle;
import org.esgi.orm.my.model.Seance;
import org.esgi.orm.my.model.User;


public class Application {
	public static void main(String[] args) 
	{
		//System.out.println(Arrays.toString(User.class.getAnnotations()));



		//System.out.println("--------------------TEST----------------------");
		createBDD_ifNotExist();
		ShowDataUsingClass();
		ShowDataUsingNoClass();
	}

	public static void createBDD_ifNotExist()
	{

		//////////////////////////USER//////////////////////////
		User u = new User();
		u.id_user = 10;
		u.nom = "Bourda";
		u.prenom = "Arnaud";
		u.login = "Admin";
		u.password = "azerty";
		ORM.save(u);

		User u2 = new User();
		u2.id_user = 11;
		u2.nom = "Rabbit";
		u2.prenom = "Roger";
		u2.login = "DarkAngel64";
		u2.password = "qerty";
		ORM.save(u2);

		User u3 = new User();
		u3.id_user = 12;
		u3.nom = "Dupont";
		u3.prenom = "Arnaud";
		u3.login = "Rog";
		u3.password = "titi";
		ORM.save(u3);


		//////////////////////////REALISATEUR//////////////////////////
		Realisateur r1 = new Realisateur();
		r1.id_realisateur = 1;
		r1.nom = "Speilberg";
		r1.prenom = "Steven";
		if(ORM.load(Realisateur.class, r1.id_realisateur, null) == null)
			ORM.save(r1);

		Realisateur r2 = new Realisateur();
		r2.id_realisateur = 2;
		r2.nom = "Lucas";
		r2.prenom = "George";
		if(ORM.load(Realisateur.class, r2.id_realisateur, null) == null)
			ORM.save(r2);

		//////////////////////////GENRE//////////////////////////
		Genre g1 = new Genre();
		g1.id_genre = 1;
		g1.intitule = "Science-Fiction";
		if(ORM.load(Genre.class, g1.id_genre, null) == null)
			ORM.save(g1);

		Genre g2 = new Genre();
		g2.id_genre = 2;
		g2.intitule = "Aventure";
		if(ORM.load(Genre.class, g2.id_genre, null) == null)
			ORM.save(g2);

		//////////////////////////FILM//////////////////////////
		Film f1 = new Film();
		f1.id_film = 1;
		f1.titre = "Jurassic Park";
		f1.realisateur = r1;
		f1.genre = g1;
		f1.description = "John Parker Hammond, le PDG de la puissante compagnie InGen, parvient à donner vie à des dinosaures grâce au clonage et décide de les utiliser dans le cadre d’un parc d'attractions qu’il compte ouvrir sur une île. Avant l'ouverture, il fait visiter le parc à un groupe d'experts pour obtenir leur aval. Pendant la visite, une tempête éclate et un informaticien corrompu par une entreprise rivale en profite pour couper les systèmes de sécurité afin de voler des embryons de dinosaures. En l'absence de tout système de sécurité pendant plusieurs heures,les dinosaures s'échappent sans mal, mais le cauchemar des visiteurs ne fait que commencer...";
		if(ORM.load(Film.class, f1.id_film, null) == null)
			ORM.save(f1);

		Film f2 = new Film();
		f2.id_film = 2;
		f2.titre = "Star wars : episode 1";
		f2.description = "La galaxie est gérée sous la forme d'une République galactique, par le Sénat galactique qui regroupe les représentants des différentes entités appartenant à la République et dont le siège se situe sur la planète Coruscant. La République existe depuis plus de 25 000 ans, dont mille pendant lesquels elle a fonctionné sous cette forme sans crise majeure.\n\nLes chevaliers Jedi, détenteurs d'un pouvoir mystique grâce à la Force, sont les gardiens de la paix et de la justice à travers la galaxie.";
		f2.realisateur = r2;
		f2.genre = g2;
		if(ORM.load(Film.class, f2.id_film, null) == null)
			ORM.save(f2);

		//////////////////////////PARTICIPANT/////////////////////
		Participant p1 = new Participant();
		p1.id_participant = 1;
		p1.nom = "Neil";
		p1.prenom = "Sam";
		p1.nom_role = "Grant";
		p1.prenom_role = "Alan";
		p1.film = f1;
		if(ORM.load(Participant.class, p1.id_participant, null) == null)
			ORM.save(p1);

		Participant p2 = new Participant();
		p2.id_participant = 2;
		p2.nom = "Dern";
		p2.prenom = "Laura";
		p2.nom_role = "Sattler";
		p2.prenom_role = "Ellie";
		p2.film = f1;
		if(ORM.load(Participant.class, p2.id_participant, null) == null)
			ORM.save(p2);

		Participant p3 = new Participant();
		p3.id_participant = 3;
		p3.nom = "McGregor";
		p3.prenom = "Ewan";
		p3.nom_role = "kenobi";
		p3.prenom_role = "Obi-Wan";
		p3.film = f2;
		if(ORM.load(Participant.class, p3.id_participant, null) == null)
			ORM.save(p3);
		//////////////////////////EVALUATION//////////////////////////
		Evaluation e1 = new Evaluation();
		e1.id_evaluation = 1;
		e1.note = 15;
		e1.commentaire = "Ce film est trés bien realisé";
		e1.film = f1;
		e1.user = u2;
		if(ORM.load(Evaluation.class, e1.id_evaluation, null) == null)
			ORM.save(e1);

		Evaluation e2 = new Evaluation();
		e2.id_evaluation = 2;
		e2.note = 8;
		e2.commentaire = "Ce film n'est pas du tout réaliste";
		e2.film = f1;
		e2.user = u3;
		if(ORM.load(Evaluation.class, e2.id_evaluation, null) == null)
			ORM.save(e2);

		Evaluation e3 = new Evaluation();
		e3.id_evaluation = 3;
		e3.note = 17;
		e3.commentaire = "Trés bon film";
		e3.film = f2;
		e3.user = u2;
		if(ORM.load(Evaluation.class, e3.id_evaluation, null) == null)
			ORM.save(e3);

		//////////////////////////SALLE//////////////////////////
		Salle s1 = new Salle();
		s1.id_salle = 1;
		s1.numero = 404;
		s1.etage = 4;
		if(ORM.load(Salle.class, s1.id_salle, null) == null)
			ORM.save(s1);

		Salle s2 = new Salle();
		s2.id_salle = 2;
		s2.numero = 42;
		s2.etage = 0;
		if(ORM.load(Salle.class, s2.id_salle, null) == null)
			ORM.save(s2);

		//////////////////////////SEANCE//////////////////////////

		Seance se1 = new Seance();
		se1.id_seance = 1;
		se1.mois = 5;
		se1.jour = 24;
		se1.heure = "09:15";
		se1.version = "VF";
		se1.salle = s2;
		se1.film = f1;
		if(ORM.load(Seance.class, se1.id_seance, null) == null)
			ORM.save(se1);

		Seance se2 = new Seance();
		se2.id_seance = 2;
		se2.mois = 5;
		se2.jour = 23;
		se2.heure = "17:45";
		se2.version = "VO";
		se2.salle = s1;
		se2.film = f1;
		if(ORM.load(Seance.class, se2.id_seance, null) == null)
			ORM.save(se2);


	}

	private static void ShowDataUsingClass() {


		Map<String, Object> where = new Hashtable() ;
		where.put("prenom","Arnaud");
		Map<String, Object> sort = null;
		Integer limit = null;
		Integer offset = null;
		List<Object> res = ORM.find(User.class, where, sort, limit, offset);
		System.out.println("\nrecherche 1 : monotable:");
		for(Object o : res)
			System.out.println("\t"+(User)o);

		Map<String, Object> where2 = new Hashtable() ;
		where2.put("user.identifiant","identifiant.id_i"); //jointure
		where2.put("prenom","Arnaud");
		where2.put("identifiant.login","Rog");	
		List<Object> res2 = ORM.find(User.class, where2, sort, limit, offset);
		System.out.println("\nrecherche 2 : multitable:");
		for(Object o : res2)
			System.out.println("\t"+(User)o);

		System.out.println("\n----------------------");
		int id_film = 1;
		//float sommeNotes = 0;
		//int nbFilm = 0;
		Film film = (Film)ORM.load(Film.class, id_film, null);
		System.out.println("Affichage de la classe FILM :\n\t"+film);

		System.out.println("Ses evaluation :v2");
		System.out.println("\t"+film.Evaluation);

		//System.out.println("Sa moyenne :"+sommeNotes/nbFilm);

		Map<String, Object> where4 = new Hashtable() ;
		System.out.println("Ses seances:v2");
		System.out.println("\t"+film.Seance);
		System.out.println("----------------------");
	}

	private static void ShowDataUsingNoClass() {
		BDD bdd = new BDD("esgi");
		String table = "FILM, GENRE";
		String champs = "titre, intitule";
		Integer limit = null;

		Map<String, Object> where= new Hashtable() ;
		where.put("GENRE.id_genre","FILM.GENRE"); //jointure
		where.put("GENRE.intitule", "Aventure");		

		LinkedList<String> result = bdd.requeteToLinkedList(table, champs, where, limit);
		System.out.println("ShowDataUsingNoClass : \n\t"+result);

		bdd.deconnexion();
	}
}
