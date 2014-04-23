package org.esgi.orm.my.model;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("salle")
public class Salle {

	@ORM_PK
	public Integer id_salle;
	public Integer numero;
	public Integer etage;
	
	public String toString() {
		return " SALLE[id_salle=" + id_salle + ", numero=" + numero +", etage=" + etage + "]";
	}
}
