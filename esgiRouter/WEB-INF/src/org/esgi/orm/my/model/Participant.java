package org.esgi.orm.my.model;

import java.util.List;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("participant")
public class Participant {

	@ORM_PK
	public Integer id_participant;
	public String nom;
	public String prenom;
	public String nom_role;
	public String prenom_role;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Film film = null;
	
	public String toString() {
		return " Participant[id_participant=" + id_participant + ", nom=" + nom + ", prenom=" + prenom +", nom_role=" + nom_role + ", prenom_role=" + prenom_role +"]";
	}
}
