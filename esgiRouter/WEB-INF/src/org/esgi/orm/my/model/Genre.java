package org.esgi.orm.my.model;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("genre")
public class Genre {

	@ORM_PK
	public Integer id_genre;
	public String intitule;
	
	public String toString() {
		return " Genre[id_genre=" + id_genre + ", intitule=" + intitule + "]";
	}
}
