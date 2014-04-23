package org.esgi.orm;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.esgi.orm.my.model.Evaluation;
import org.esgi.orm.my.model.Film;
import org.esgi.orm.my.model.Genre;
import org.esgi.orm.my.model.Identifiant;
import org.esgi.orm.my.model.Realisateur;
import org.esgi.orm.my.model.Salle;
import org.esgi.orm.my.model.Seance;
import org.esgi.orm.my.model.User;


public class Application {
	public static void main(String[] args) 
	{
		//System.out.println(Arrays.toString(User.class.getAnnotations()));

		createBDD_ifNotExist();
		ShowDataUsingClass();
		ShowDataUsingNoClass();
	}

	public static void createBDD_ifNotExist()
	{
		////////////////////////////IDENTIFIANT//////////////////////////
		Identifiant id = new Identifiant();
		id.id_i = 1;
		id.login = "Admin";
		id.password = "azerty";
		ORM.save(id);
		//ORM.remove(Identifiant.class, id.id_i);

		Identifiant id2 = new Identifiant();
		id2.id_i = 2;
		id2.login = "Rog";
		id2.password = "titi";
		ORM.save(id2);

		//////////////////////////USER//////////////////////////
		User u = new User();
		u.id_user = 10;
		u.nom = "Bourda";
		u.prenom = "Arnaud";
		u.identifiant = id;
		ORM.save(u);

		User u2 = new User();
		u2.id_user = 11;
		u2.nom = "Rabbit";
		u2.prenom = "Roger";
		u2.identifiant = id;
		ORM.save(u2);

		User u3 = new User();
		u3.id_user = 12;
		u3.nom = "Dupont";
		u3.prenom = "Arnaud";
		u3.identifiant = id2;
		ORM.save(u3);
		
		//////////////////////////REALISATEUR//////////////////////////
		Realisateur r1 = new Realisateur();
		r1.id_realisateur = 1;
		r1.nom = "Speilberg";
		r1.prenom = "Steven";
		ORM.save(r1);
		
		Realisateur r2 = new Realisateur();
		r2.id_realisateur = 2;
		r2.nom = "Lucas";
		r2.prenom = "George";
		ORM.save(r2);

		//////////////////////////GENRE//////////////////////////
		Genre g1 = new Genre();
		g1.id_genre = 1;
		g1.intitule = "Science-Fiction";
		ORM.save(g1);
		
		Genre g2 = new Genre();
		g2.id_genre = 2;
		g2.intitule = "Aventure";
		ORM.save(g2);
		
		//////////////////////////FILM//////////////////////////
		Film f1 = new Film();
		f1.id_film = 1;
		f1.titre = "Jurassic Park";
		f1.realisateur = r1;
		f1.genre = g1;
		ORM.save(f1);
		
		Film f2 = new Film();
		f2.id_film = 2;
		f2.titre = "Star wars : episode 1";
		f2.realisateur = r2;
		f2.genre = g2;
		ORM.save(f2);

		//////////////////////////EVALUATION//////////////////////////
		Evaluation e1 = new Evaluation();
		e1.id_evaluation = 1;
		e1.note = 15;
		e1.commentaire = "Ce film est tr�s bien realis�";
		e1.film = f1;
		e1.user = u2;
		ORM.save(e1);
		
		Evaluation e2 = new Evaluation();
		e2.id_evaluation = 2;
		e2.note = 8;
		e2.commentaire = "Ce film n'est pas du tout r�aliste";
		e2.film = f1;
		e2.user = u3;
		ORM.save(e2);
		
		Evaluation e3 = new Evaluation();
		e3.id_evaluation = 3;
		e3.note = 17;
		e3.commentaire = "Tr�s bon film";
		e3.film = f2;
		e3.user = u2;
		ORM.save(e3);
		
		//////////////////////////SALLE//////////////////////////
		Salle s1 = new Salle();
		s1.id_salle = 1;
		s1.numero = 404;
		s1.etage = 4;
		ORM.save(s1);
		
		Salle s2 = new Salle();
		s2.id_salle = 2;
		s2.numero = 42;
		s2.etage = 0;
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
		ORM.save(se1);
		
		Seance se2 = new Seance();
		se2.id_seance = 2;
		se2.mois = 5;
		se2.jour = 23;
		se2.heure = "17:45";
		se2.version = "VO";
		se2.salle = s1;
		se2.film = f1;
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
		float sommeNotes = 0;
		int nbFilm = 0;
		Film film = (Film)ORM.load(Film.class, id_film);
		System.out.println("Affichage de la classe FILM :\n\t"+film);
		
		Map<String, Object> where3 = new Hashtable() ;
		where3.put("film.id_film","evaluation.film"); //jointure
		where3.put("film",id_film);
		List<Object> res3 = ORM.find(Evaluation.class, where3, sort, limit, offset);
		System.out.println("Ses evaluation :");
		for(Object o : res3)
			{
				nbFilm++;
				sommeNotes += ((Evaluation) o).note;
				System.out.println("\t"+(Evaluation)o);
			}
		
		System.out.println("Sa moyenne :"+sommeNotes/nbFilm);
		
		Map<String, Object> where4 = new Hashtable() ;
		where4.put("film.id_film","seance.film"); //jointure
		where4.put("film",id_film);
		List<Object> res4 = ORM.find(Seance.class, where4, sort, limit, offset);
		System.out.println("Ses seances:");
		for(Object o : res4)
		{
			Seance s = (Seance) o;
			System.out.println("\n\tDate : "+s.jour+"/"+s.mois+" � "+s.heure
								+"\n\tVersion : " +s.version
								+"\n\tNumero de salle:"+s.salle.numero + " etage:"+s.salle.etage);
		}
		
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
