package org.esgi.orm.my.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("film")

public class Film {
	
	@ORM_PK
	public Integer id_film;
	public String titre;
	public String description;
	public Boolean estAffiche;
	
	public String realisateur;
	public String genre;
	
	/*@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Realisateur realisateur;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Genre genre;*/
	
	public List<Evaluation> Evaluation;
	public List<Seance> Seance;
	public List<Participant> Participant;
	
	public String getTitre(){
		return titre;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Integer getId(){
		return id_film;
	}
	
	public String getRealisateur(){
		return realisateur;
	}
	
	public List<Evaluation> getEvaluations(){	
		return Evaluation;
	}
	
	public List<Participant> getParticipants(){
		return Participant;
	}
	
	public boolean getEstAffiche(){
		System.out.println(estAffiche);
		return estAffiche;
	}
	
	public List<Seance> getSeancesToday(){
		List<Seance> list = new ArrayList<Seance>();
		Date d = new Date(); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

		for(Seance s : Seance){
			if(s.jour == cal.get(Calendar.DAY_OF_MONTH) && s.mois == cal.get(Calendar.MONTH)+1)
				list.add(s);
		}
				
		return list;
	}
	
	public List<Seance> getSeancesTomorrow(){
		List<Seance> list = new ArrayList<Seance>();
		Date now = new Date();  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(now);  
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		for(Seance s : Seance){
			if(s.jour == cal.get(Calendar.DAY_OF_MONTH) && s.mois == cal.get(Calendar.MONTH)+1)
				list.add(s);
		}
		
		return list;
	}
	
	public String toString() {
		return " FILM[id_film=" + id_film + ", titre=" + titre + ", " +"estAffiche=" + estAffiche + ", " +realisateur + ", " +genre +" ,"+Participant+"]";
	}
}
