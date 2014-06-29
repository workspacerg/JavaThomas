package org.esgi.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.esgi.orm.my.annotations.ORM_SCHEMA;
import org.esgi.orm.my.model.User;



public class ORM implements IORM {

	static ORM instance;
	static BDD bdd;


	static {

		String value_SCHEMA = null;

		instance = new ORM();
		for (Annotation a : User.class.getAnnotations()) {		
			String name_SCHEMA= a.annotationType().getSimpleName();
			if ("ORM_SCHEMA".equals(name_SCHEMA)){
				value_SCHEMA = ((ORM_SCHEMA)a).value();
			}
		}
		bdd = new BDD(value_SCHEMA);
	}

	public static Object save(Object o) {
		return instance._save(o);
	}

	public static Object load(Class<?> clazz, Object id, Object father) {
		return instance._load(clazz, id, father);
	}

	public static boolean remove(Class clazz, Object id) {
		return instance._remove(clazz, id);
	}

	@Override
	public boolean _remove(Class clazz, Object id) {


		for (Field field : clazz.getFields()) {
			for(Annotation a : field.getAnnotations())
			{
				if(a.annotationType().getSimpleName().equals("ORM_COMPOSITION"))
				{
					String fk_name = getNameID(field.getType());					
					String champs_name = field.getName();
					String table = clazz.getSimpleName();

					/*System.out.println("table = "+table
										+"\nid = "+id
										+"\nid name = "+getNameID(clazz)
										+"\nchamp ="+champs_name);*/

					LinkedList<String> values_where = new LinkedList<String>();
					values_where.add(id.toString());

					LinkedList<String> champs_where = new LinkedList<String>();
					champs_where.add(getNameID(clazz));

					String fk_to_delete = bdd.requeteToString(table, champs_name, champs_where, values_where);
					//System.out.println("fk_to_delete = "+fk_to_delete);

					_remove(field.getType(), fk_to_delete);	
				}
			}
		}


		String table_name = clazz.getSimpleName();
		String champ_value = id.toString();
		String  champ_name= clazz.getFields()[getPositionID(clazz)].getName(); // l'id est forcement sur la 1er colonne de la table 		
		//System.out.println(table_name+" with "+champ_name+"="+champ_value );

		LinkedList<String> champs_where = new LinkedList<String>();
		champs_where.add(champ_name);
		LinkedList<String> values_where = new LinkedList<String>();
		values_where.add(champ_value);

		return bdd.delete(table_name, champs_where, values_where);
	}

	@Override
	public Object _save(Object o) {
		try {

			LinkedList<String> mes_champs = new LinkedList<String>();
			LinkedList<String> champsToCreate = new LinkedList<String>();
			LinkedList<String> mes_valeurs = new LinkedList<String>();
			LinkedList<String> mes_types = new LinkedList<String>();

			for (Field field : o.getClass().getFields()) {

				String name = field.getName();
				String type = field.getType().getSimpleName();

				if(type.equals("List"))
				{
					continue;
				}

				String value;
				if(o.getClass().getField(name).get(o) != null)
					{
						value =  o.getClass().getField(name).get(o).toString();
					
						if(type.toString().equals(Boolean.class.getSimpleName()))
							value = value.equalsIgnoreCase("true")?"1": "0";
					}
				else
					value = null;
				//System.out.println(name+" = "+value);


				boolean fk = false;
				for(Annotation a : field.getAnnotations())
				{
					if(a.annotationType().getSimpleName().equals("ORM_COMPOSITION"))
					{
						fk = true;
						//System.out.println(a.annotationType().getSimpleName() +" = "+name);
						//_save(champsToCreate)
						
						String fk_type = getTypeID(field.getType());
						//System.out.println("FK_TYPE = "+fk_type);

						String fk_name = getNameID(field.getType());
						//System.out.println("FK_name = "+fk_name);

						Object o_fk = o.getClass().getField(name).get(o);
						String fk_val = o_fk.getClass().getField(fk_name).get(o_fk).toString();

						//System.out.println("fk_value = "+fk_val);

						Map<String, Object> where = new Hashtable() ;
						where.put(fk_name,fk_val);



						if(find(field.getType(), where, null, 1, null).size() > 0)
						{
							//System.out.println("La voiture existe deja");
						}
						else
						{
							System.out.println("Insertion impossible la clef '"+fk_val+"' n'existe pas encore sur la table "+field.getType().getSimpleName());
							return null;
						}

						//return null;

						mes_types.add(fk_type);
						mes_valeurs.add(fk_val);
					}
				}


				if(value != null)
				{
					mes_champs.add(name);
					champsToCreate.add(name);


					if(!fk)
					{
						mes_types.add(type);
						mes_valeurs.add(value);
					}

				}		
				else
				{
					mes_types.add(type);
					champsToCreate.add(name);
				}
			}
			String table_name = o.getClass().getSimpleName();
			bdd.createTableIfNotExists(table_name, champsToCreate, mes_types);
			bdd.insertion(table_name, mes_champs, mes_valeurs);		
		} catch (Exception e) {System.out.println("--->1");e.printStackTrace();}

		return o;
	}


	@Override
	public Object _load(Class clazz, Object id, Object father) {

		try {
			//String res = bdd.requeteToString(value_TABLE, "login");
			//System.out.println("SELECT : \n" +res+"\n");

			if(id == null)
				return null;

			String table_name = clazz.getSimpleName();
			String champ_value = id.toString();



			String  champ_name= clazz.getFields()[getPositionID(clazz)].getName(); // l'id est forcement sur la 1er colonne de la table 		
			//System.out.println(table_name+" with "+champ_name+"="+champ_value );

			LinkedList<String> champs_where = new LinkedList<String>();
			champs_where.add(champ_name);
			LinkedList<String> values_where = new LinkedList<String>();
			values_where.add(champ_value);

			LinkedList<String> res = bdd.requeteToLinkedList(table_name, "*", champs_where, values_where);
			if(res.size() == 0)
				{
				//System.out.println("---DEBUG  Table="+table_name+" "+champ_name +"="+champ_value);
					return null;
				}

			Object myObject= clazz.newInstance();

			int count = 0;

			//System.out.println(">>>"+res);

			for (Field field : (clazz).getFields()) {

				if(count > res.size())
					return myObject;

				Class<?> type = field.getType();
				String name = field.getName();
				
				if(type.getSimpleName().toString().equals("List"))
				{
										
					Map<String, Object> where = new Hashtable() ;
					where.put(table_name,champ_value);
					LinkedList<String> list_id = bdd.requeteToLinkedList(name, "id_"+name, where, null);
					
					List<Object> listIntoClass = new LinkedList<Object>(); 
					
					for (String id_value : list_id)
					{
						int idToFind = Integer.valueOf(id_value.replace("[", "").replace("]", ""));
						
						
						String classLocalisation = myObject.getClass().getName();
						classLocalisation = classLocalisation.substring(0, classLocalisation.lastIndexOf(".")+1)+name;
						Class childClass = Class.forName (classLocalisation);			
						
						Object eval = ORM.load(childClass, idToFind, myObject);
						listIntoClass.add(eval);
					}
					field.set(myObject, (listIntoClass));

					continue;

				}
				String value = res.get(count++);

				//System.out.println(type.toString() +" "+name+" = "+value);

				//System.out.println("->"+clazz.getFields()[4]);

				//System.out.println(type.getCanonicalName()+" = "+Integer.class.getName());
				
				if(father != null && type.getCanonicalName().toString().equals(father.getClass().getCanonicalName()))
						field.set(myObject, (father));	
				
				else if(type.getCanonicalName().toString().equals(Integer.class.getName()) || type.getCanonicalName().toString().equalsIgnoreCase("int"))
					field.set(myObject, (Integer.valueOf(value)));

				else if(type.getCanonicalName().toString().equals(String.class.getName()))
					field.set(myObject, (value));
				
				else if(type.getSimpleName().toString().equals(Date.class.getSimpleName()))
					{
						if(value == null)
							field.set(myObject, null);
						else
							field.set(myObject, ( java.sql.Date.valueOf(value)));
					}

				else if(type.getSimpleName().toString().equals(Boolean.class.getSimpleName()))
				{
					Boolean convert = value.equals("1")?true: false ;
					field.set(myObject, (convert));
				}

				else if(containsAnotations(field.getAnnotations(), "ORM_COMPOSITION"))
				{
					//System.out.println("fk = "+value);
					field.set(myObject, ORM.load(field.getType(), value, null));
				}



				else
				{
					System.out.println(" _load type non géré "+type.getCanonicalName().toString());
				}
			}
			return myObject;
			//System.out.println("<<<"+myObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("--->2");e.printStackTrace();
		}

		return null;
	}

	private boolean containsAnotations(Annotation[] annotations, String value) {
		// TODO Auto-generated method stub

		for (int i = 0; i < annotations.length; i++) {
			if(annotations[i].annotationType().getSimpleName().equals(value))
				return true;
		}
		return false;
	}

	private static int getPositionID(Class clazz) 
	{
		int pos_id =0;

		for (int i =0; i < (clazz).getFields().length; i++)
		{
			Field field = (clazz).getFields()[i];
			for(Annotation a : field.getAnnotations())
				if(a.annotationType().getSimpleName().equals("ORM_PK"))
					pos_id=i;
		}
		return pos_id;
	}

	private static String getNameID(Class clazz) 
	{
		String name_id = null;

		for (int i =0; i < (clazz).getFields().length; i++)
		{
			Field field = (clazz).getFields()[i];
			for(Annotation a : field.getAnnotations())
				if(a.annotationType().getSimpleName().equals("ORM_PK"))
					name_id=field.getName();
		}
		return name_id;
	}


	private static String getTypeID(Class clazz) 
	{
		String type_id = null;

		for (int i =0; i < (clazz).getFields().length; i++)
		{
			Field field = (clazz).getFields()[i];
			for(Annotation a : field.getAnnotations())
				if(a.annotationType().getSimpleName().equals("ORM_PK"))
					type_id=field.getType().getSimpleName();
		}
		return type_id;
	}

	static public List <Object> find (Class<?> clazz, Map<String, Object> where, Map<String, Object> sort, Integer limit, Integer offset)
	{
		
		List res = new LinkedList<>();		
		String tables_name = clazz.getSimpleName();
		
		int pos;
		if(where != null && !where.isEmpty())
		{
			for (Entry<String,Object> ent : where.entrySet())
				if((pos = ent.getKey().indexOf(".")) > 0)//jointure necessaire
				{
					if (!tables_name.toLowerCase().contains(ent.getKey().substring(0, pos).toLowerCase()))
						tables_name += ", " + ent.getKey().substring(0, pos);
				}

			for (Object ent : where.values())
				if((pos = ent.toString().indexOf(".")) > 0)//jointure necessaire
				{
					if (!tables_name.toLowerCase().contains(ent.toString().substring(0, pos).toLowerCase()))
						tables_name += ", " + ent.toString().substring(0, pos);
				}		
		}
		LinkedList<String> find = bdd.requeteToLinkedList(tables_name, clazz.getFields()[getPositionID(clazz)].getName(), where, limit);
		//System.out.println("len ="+find.size());

		//System.out.println("String = "+find);
		for (int i = 0; i < find.size(); i++) 
		{		
			Object o = null;

			StringBuilder id_to_find = new StringBuilder();
			id_to_find.append(find.get(i));
			id_to_find.deleteCharAt(id_to_find.length()-1).deleteCharAt(0);

			if(clazz.getFields()[getPositionID(clazz)].getType().getCanonicalName().toString().equals(Integer.class.getName()))
				o =  ORM.load(clazz, Integer.valueOf(id_to_find.toString()), null);

			else if(clazz.getFields()[getPositionID(clazz)].getType().getCanonicalName().toString().equals(String.class.getName()))
				o =  ORM.load(clazz, id_to_find.toString(), null);

			else
				System.out.println("list type non géré");

			res.add(o);

			//System.out.println(">"+res);
		}				
		return res;	
	}
}
