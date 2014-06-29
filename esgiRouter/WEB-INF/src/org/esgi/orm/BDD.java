package org.esgi.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;


public class BDD
{
	private Connection maconnexion = null;
	private String bdd_cible = "";
	PreparedStatement ps = null;

	public BDD(String bdd)
	{
		this.bdd_cible = bdd;

		chargerPilote();
		connexion();

	}

	//CHARGEMENT DU PILOTE\\
	public void chargerPilote()
	{
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		}
		catch ( ClassNotFoundException exp ) {
			System.out.println( "Erreur de chargement du pilote : "+exp );
		}
	}

	//OUVERTURE CONNEXION\\
	public void connexion()
	{
		String url_base = "jdbc:mysql://localhost/"+bdd_cible;
		try {
			this.maconnexion = DriverManager.getConnection(url_base, "root", "");
			System.out.println( "Chargement de la base reussi" );
		}
		catch (SQLException exp) {
			try {
				String url_src = "jdbc:mysql://localhost/";
				this.maconnexion = DriverManager.getConnection(url_src, "root", "");
				CreationBase();
				System.out.println( "Creation de la base reussi" );
				this.maconnexion = DriverManager.getConnection(url_base, "root", "");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Connection getmaconnexion()
	{
		return this.maconnexion;
	}

	public void insertion(String table, LinkedList<String> champs ,LinkedList<String> value) // Surcharge en fonction de la recherche
	{
		String sql ="";
		if(value == null || value.size()<1)
			return;
		try 
		{
			int i ;
			String champs_parse = "";
			for (i= 0; i < champs.size()-1; i++)
				champs_parse+=champs.get(i)+",";
			champs_parse+=champs.get(i);

			sql = "INSERT INTO "+table
					+ "("+champs_parse+") VALUES(";

			for ( i = 0; i < value.size()-1; i++)
				sql+="?,";
			sql+="?)";

			//System.out.println("-->"+sql);

			ps = this.maconnexion.prepareStatement(sql);

			for ( i = 0; i < value.size(); i++)
				ps.setString(i+1, value.get(i));

			ps.executeUpdate();
		} 
		catch (SQLIntegrityConstraintViolationException e) 
		{
			LinkedList<String> val = new LinkedList<String>();
			val.add(value.get(0));

			LinkedList<String> ch = new LinkedList<String>();
			ch.add(champs.get(0));

			update(table,  champs, value, ch, val);
		}
		catch (SQLException e) 
		{
			System.out.println(e);
			//JOptionPane.showMessageDialog(null,"Problème d'insertion : \n"+e ,"Erreur",JOptionPane.ERROR_MESSAGE);
			System.out.println("Problème d'insertion : "+sql+"\n");
			return;
		}
	}

	public boolean update(String table, LinkedList<String> champs, LinkedList<String> values, LinkedList<String> champs_where, LinkedList<String> values_where) // Surcharge en fonction de la recherche
	{
		String sql ="";
		try 
		{
			int count = 1;
			sql = "UPDATE "+ table +" SET ";


			int i;
			for (i = 0; i < champs.size()-1; i++)
				sql+= champs.get(i) + " = ?,";
			sql+= champs.get(i) + " = ?";

			if(champs_where != null && champs_where.size() > 0)
			{
				sql+=" WHERE ";
				int j = 0;
				for (j = 0; j < champs_where.size()-1; i++)
					sql+= champs_where.get(j) + " = ?,";
				sql+= champs_where.get(j) + " = ?";
			}

			ps = this.maconnexion.prepareStatement(sql);

			for (int y = 0; y < values.size(); y++)
			{
				ps.setString(count++, values.get(y));;
			}

			if(champs_where != null && champs_where.size() > 0)
			{
				for (int y = 0; y < values_where.size(); y++)
				{
					ps.setString(count++, values_where.get(y));
				}
			}



			ps.executeUpdate();		 
		} 
		catch (SQLException e) 
		{
			//System.out.println(e);
			//JOptionPane.showMessageDialog(null,"Problème d'insertion : \n"+e ,"Erreur",JOptionPane.ERROR_MESSAGE);
			System.out.println("probleme d'update "+e);
			System.out.println("Avec la requete : "+ sql);
			return false;
		}
		return true;
	}

	public boolean delete(String table, LinkedList<String> champs, LinkedList<String> values) // Surcharge en fonction de la recherche
	{
		try 
		{
			String sql = "DELETE FROM "+ table +" WHERE ";

			int i;
			for (i = 0; i < champs.size()-1; i++)
				sql+= champs.get(i) + " = ?,";
			sql+= champs.get(i) + " = ?";

			ps = this.maconnexion.prepareStatement(sql);

			for (int y = 0; y < values.size(); y++)
				ps.setString(y+1, values.get(i));

			ps.executeUpdate();		 
		} 
		catch (SQLException e) 
		{
			//System.out.println(e);
			//JOptionPane.showMessageDialog(null,"Problème de supression : \n"+e ,"Erreur",JOptionPane.ERROR_MESSAGE);
			System.out.println("probleme de supression "+e);
			return false;
		}
		return true;
	}


	public void CreationBase() 
	{
		try 
		{
			Statement st = this.maconnexion.createStatement();
			String query = "CREATE DATABASE IF NOT EXISTS "+bdd_cible;  
			st.execute(query);
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			System.out.println("Problème de creation de base");
		}
	}


	public String requeteToString(String table, String champs,LinkedList<String> champs_where, LinkedList<String> values_where)  					// Surcharge en fonction de la recherche
	{ //Resultat ligne par ligne
		String lesLignesString = "";
		try {

			String sql = "SELECT " + champs + " FROM " + table + " WHERE ";

			int i;
			for (i = 0; i < champs_where.size()-1; i++)
				sql+= champs_where.get(i) + " = ?,";
			sql+= champs_where.get(i) + " = ?";

			ps = this.maconnexion.prepareStatement(sql);

			for (int y = 0; y < values_where.size(); y++)
				ps.setString(y+1, values_where.get(i));

			ResultSet rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int nbColonnes = md.getColumnCount();

			Vector lesColonnes = new Vector(nbColonnes);

			for(i=1; i<=nbColonnes; i++)
				lesColonnes.add(md.getColumnName(i));

			Vector row;

			while(rs.next())
			{
				row = new Vector(nbColonnes);
				for(i=1; i<=nbColonnes; i++)
				{
					row.add(rs.getString(i));
				}
				lesLignesString = lesLignesString + row.toString() + "\n";
				lesLignesString = lesLignesString.replaceAll("(\\[|])", ""); 				//le vecteur genere des [] pour chaque ligne
			}
		} 

		catch (SQLException e) 
		{
			System.out.println(e);
			System.out.println("Problème de requete 3");
		}
		return lesLignesString;
	}

	public LinkedList<String> requeteToLinkedList(String table, String champs,Map<String, Object> where, Integer limit)  					// Surcharge en fonction de la recherche
	{ //Resultat ligne par ligne
		LinkedList<String> lesLignesString = new LinkedList<String>();
		
		if(countRows(table) < 1)
			return lesLignesString;
		
		String sql = "";
		try {

			sql = "SELECT " + champs + " FROM " + table + " ";

			int i;

			Map<String, Object> where_without_jointure = new Hashtable();
			if(where !=null && where.size() >0 )
			{
				sql+="WHERE ";
				StringBuilder sb = new StringBuilder();

				Iterator iterator = where.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry ent = (Map.Entry) iterator.next();
					String key = ent.getKey().toString();
					String value = ent.getValue().toString();

					if(key.contains(".") && value.contains(".")) //jointure
					{
						sb.append(key +"="+value+" and ");
					}
					else
					{
						sb.append(ent.getKey() + " = ? and ");
						where_without_jointure.put(key, value);
					}
				}

				sql += sb.delete(sb.length()-4, sb.length()-1).append(" ").toString();
			}


			if (limit != null && limit > 0)
				sql+=" LIMIT "+limit;

			ps = this.maconnexion.prepareStatement(sql);

			//System.out.println(sql);
			
			int count = 1;
			if(where_without_jointure !=null && where_without_jointure.size() >0 )
			{
				for (Object ent : where_without_jointure.values())
				{
					ps.setObject(count++, ent.toString());
					//System.out.println("? = "+ent.toString());
				}	
			}
			ResultSet rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int nbColonnes = md.getColumnCount();
			Vector lesColonnes = new Vector(nbColonnes);

			for(i=1; i<=nbColonnes; i++)
				lesColonnes.add(md.getColumnName(i));

			Vector row;

			while(rs.next())
			{
				row = new Vector(nbColonnes);
				for(i=1; i<=nbColonnes; i++)
				{
					row.add(rs.getString(i));
				}
				lesLignesString.add(row.toString());
			}
		} 

		catch (SQLException e) 
		{
			System.out.println(e);
			System.out.println("Problème de requete :"+sql);
		}
		return lesLignesString;
	}

	public LinkedList<String> requeteToLinkedList(String table, String champs,LinkedList<String> champs_where, LinkedList<String> values_where)  					// Surcharge en fonction de la recherche
	{ //Resultat ligne par ligne
		String lesLignesString = "";
		try {

			String sql = "SELECT " + champs + " FROM " + table + " WHERE ";

			int i;
			for (i = 0; i < champs_where.size()-1; i++)
				sql+= champs_where.get(i) + " = ?,";
			sql+= champs_where.get(i) + " = ?";
			
			ps = this.maconnexion.prepareStatement(sql);

			for (int y = 0; y < values_where.size(); y++)
				ps.setString(y+1, values_where.get(i));

			ResultSet rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int nbColonnes = md.getColumnCount();

			Vector lesColonnes = new Vector(nbColonnes);

			for(i=1; i<=nbColonnes; i++)
				lesColonnes.add(md.getColumnName(i));

			LinkedList<String> rows;

			if(rs.next())
			{
				rows = new LinkedList<String>();
				for(i=1; i<=nbColonnes; i++)
				{
					rows.add(rs.getString(i));
				}
				return rows;
			}
		} 

		catch (SQLException e) 
		{
			//System.out.println(e);
			//System.out.println("Problème de requete 1");
		}
		return new LinkedList<>();
	}


	public String requeteToString(String table, String champs)  // Surcharge en fonction de la recherche
	{
		Statement st;
		String lesLignesString = "";

		try {
			st = this.maconnexion.createStatement();
			String query = "SELECT " + champs + " FROM " + table ;
			ResultSet rs = st.executeQuery(query);

			ResultSetMetaData md = rs.getMetaData();
			int nbColonnes = md.getColumnCount();

			Vector lesColonnes = new Vector(nbColonnes);

			for(int i=1; i<=nbColonnes; i++)
				lesColonnes.add(md.getColumnName(i));

			Vector row;

			while(rs.next())
			{
				row = new Vector(nbColonnes);
				for(int i=1; i<=nbColonnes; i++)
				{
					row.add(rs.getString(i));
				}
				lesLignesString = lesLignesString + row.toString() + "\n";
				lesLignesString = lesLignesString.replaceAll("(\\[|])", ""); //le vecteur genere des [] pour chaque ligne
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			System.out.println("Problème de requete 2");
		}
		return lesLignesString;
	}

	public void createTable(String createTableSQL){

		Statement st;
		try {
			st = this.maconnexion.createStatement();
			st.execute(createTableSQL);
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("Problème de create table");
		}	
	}

	public void deconnexion()
	{
		try {
			if(this.maconnexion != null) this.maconnexion.close();
		}
		catch(SQLException exp)
		{
			System.out.println( "Erreur de fermeture de la connexion" );
		}
	}

	public void createTableIfNotExists(String table_name, LinkedList<String> mes_champs, LinkedList<String> mes_types) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(); 
		sb.append("CREATE TABLE IF NOT EXISTS ");
		sb.append(table_name);
		sb.append("\n(\n");

		for (int i = 0; i < mes_types.size(); i++) {
			sb.append(mes_champs.get(i));
			sb.append(" ");
			sb.append(mes_types.get(i));
			if(i == 0)
				sb.append(" not null AUTO_INCREMENT");
			sb.append(",\n");
		}
		sb.append("primary key (");
		sb.append(mes_champs.get(0));
		sb.append(")\n);");

		String res = sb.toString();


		res = res.replaceAll("String", "TEXT");
		res = res.replaceAll("Integer", "int(5)");

		//System.out.println(res);
		createTable(res);
	}
	
	public int countRows(String tableName) {
		// TODO Auto-generated method stub
		
		Statement st;
		int rowCount = -1;

		
			try {
				st = this.maconnexion.createStatement();
				ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM "+tableName);
				rs.next();
				rowCount = rs.getInt(1);
				
			} catch (SQLException e) {
				return -1;
			}	
		return rowCount;
	}
}