package org.esgi.orm.my.model;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;



@ORM_SCHEMA("esgi")
@ORM_TABLE("realisateur")
public class Realisateur {

	@ORM_PK
	public Integer id_realisateur;
	public String nom;
	public String prenom;
	
	public String getIdentity(){
		return String.format("%s %s", prenom,nom);
	}
	
	public String toString() {
		return " REALISATEUR[id_realisateur=" + id_realisateur + ", nom=" + nom + ", prenom=" + prenom +"]";
	}
}

