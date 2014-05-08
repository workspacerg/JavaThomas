package org.esgi.orm.my.model;

import org.esgi.orm.my.annotations.ORM_COMPOSITION;
import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_RELATION;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;


@ORM_SCHEMA("esgi")
@ORM_TABLE("evaluation")
public class Evaluation {
	
	@ORM_PK
	public Integer id_evaluation;
	public Integer note;
	public String commentaire;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public User user;
	
	@ORM_COMPOSITION
	@ORM_RELATION("1-1")  // ==> 0-n
	public Film film;
	
	public Integer getNote(){
		return note;
	}
	
	public String getCommentaire(){
		return commentaire;
	}
	
	public String toString() {
		return " EVALUATION[id_evaluation=" + id_evaluation + ", note=" + note + ", commentaire=" + commentaire + ", " + user + film +"]";
	}
}
