package org.esgi.orm.my.model;

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
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Realisateur realisateur;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Genre genre;
	
	public List<Evaluation> Evaluation;
	public List<Seance> Seance;
	
	public String getTitre(){
		return titre;
	}
	
	public Integer getId(){
		return id_film;
	}
	
	public Realisateur getRealisateur(){
		return realisateur;
	}
	
	public List<Evaluation> getEvaluations(){	
		return Evaluation;
	}
	
	public String toString() {
		return " FILM[id_film=" + id_film + ", titre=" + titre + ", " +realisateur + ", " +genre +"]";
	}
}