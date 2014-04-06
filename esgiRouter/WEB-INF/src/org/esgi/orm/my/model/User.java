package org.esgi.orm.my.model;

import java.util.Date;
import java.util.List;

import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;

@ORM_SCHEMA("esgi")
@ORM_TABLE("user")
public class User {
	@ORM_PK
	public Integer id = null;
	public String login;
	public String password;
	public volatile Date connectedAt;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", connectedAt=" + connectedAt + "]";
	}
}
