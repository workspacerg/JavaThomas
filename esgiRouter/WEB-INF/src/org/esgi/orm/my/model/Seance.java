package org.esgi.orm.my.model;

import java.util.Date;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("seance")
public class Seance {

	@ORM_PK
	public Integer id_seance;
	public Integer jour;
	public Integer mois;
	public String heure;
	public String version; //VO, VF, ...
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Film film;

	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Salle salle;
	
	public String getHeure(){
		return heure;
	}
	
	public String getVersion(){
		return version;
	}
	
	public String toString() {
		return " SEANCE[id_seance=" + id_seance + ", jour=" + jour +", mois=" + mois +", heure=" + heure + ", version=" + version + "," + film + "," + salle +"]";
	}
}

