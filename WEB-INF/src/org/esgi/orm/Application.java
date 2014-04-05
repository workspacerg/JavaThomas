package org.esgi.orm;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.annotations.ORM_TABLE;
import org.esgi.orm.my.model.User;


public class Application {
	public static void main(String[] args) {

		//System.out.println(Arrays.toString(User.class.getAnnotations()));

		User u = new User(), u2;
		u.id = 9;
		u.password = "mdp";
		u.login = "ZoZO";

		u = (User) ORM.save(u);
		//ORM.remove(User.class, u.id);

		u2 = (User) ORM.load(User.class, u.id);
		//System.out.println(u2);

	
		
		Map<String, Object> where = new Hashtable() ;
		where.put("login","ToTo");
		
		Map<String, Object> sort = null;
		Integer limit = 1;
		Integer offset = null;
		 List<Object> res = ORM.find(User.class, where, sort, limit, offset);
		 
		 //System.out.println(res);

		 //System.out.println("--->\n"+res);
		/*
		System.out.println("FALSE "+(u == u2));

		System.out.println(Arrays.toString(User.class.getDeclaredFields()));
		 */
	}
}
