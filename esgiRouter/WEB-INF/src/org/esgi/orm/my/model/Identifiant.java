package org.esgi.orm.my.model;

import java.util.Date;
import java.util.List;

import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;

@ORM_SCHEMA("esgi")
@ORM_TABLE("identifiant")
public class Identifiant {
	@ORM_PK
	public Integer id_i = null;
	public String login;
	public String password;
	public List<User> User;
	@Override
	public String toString() {
		return " IDENTIFIANT[id_i=" + id_i + ", login=" + login + ", password=" + password + "]";
	}
}
