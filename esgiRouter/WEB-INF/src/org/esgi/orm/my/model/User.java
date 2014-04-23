package org.esgi.orm.my.model;

import java.util.Date;
import java.util.List;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("user")
public class User {
	@ORM_PK
	public Integer id_user = null;
	public String nom;
	public String prenom;

	public volatile Date connectedAt;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Identifiant identifiant = null;
	
	@Override
	public String toString() {
		return "USER[id=" + id_user + ", nom=" + nom + ", prenom=" + prenom
				+ ", connectedAt=" + connectedAt + ", "+identifiant+"]";
	}
}
