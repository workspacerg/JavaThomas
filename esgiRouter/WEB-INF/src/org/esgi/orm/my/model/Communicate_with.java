package org.esgi.orm.my.model;


import org.esgi.orm.my.annotations.ORM_PK;
import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;



@ORM_SCHEMA("esgi")
@ORM_TABLE("communicate_with")
public class Communicate_with {
	@ORM_PK
	public Integer id_contact = null;
	public String nom;
	public String prenom;
	
	public String email_address;
	public String subject;
	public Integer is_done;

	
	public String toString() {
		return "USER[id=" + id_contact + ", nom=" + nom + ", prenom=" + prenom
				+ ", email_address=" + email_address +", subject=" + subject + ", "+ ", is_done=" + is_done +"]";
	}
}


