package com.iao.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iao.persistance.BaseDeDonnees;

public class Client {
	public Client(String nom, String prenom, String telephone, String email) {

		this.nom=nom;
		this.prenom=prenom;
		this.telephone=telephone;
		this.email=email;
	}
	private int num_cli;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String adresse;
	private String profession;
	private double revenue_mensuel;
	private int blacklist;
	
	
	
	public int getNum_cli() {
		return num_cli;
	}



	public void setNum_cli(int num_cli) {
		this.num_cli = num_cli;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getProfession() {
		return profession;
	}



	public void setProfession(String profession) {
		this.profession = profession;
	}



	public double getRevenue_mensuel() {
		return revenue_mensuel;
	}



	public void setRevenue_mensuel(double revenue_mensuel) {
		this.revenue_mensuel = revenue_mensuel;
	}



	public int getBlacklist() {
		return blacklist;
	}



	public void setBlacklist(int blacklist) {
		this.blacklist = blacklist;
	}

	public int checkClientExist(Client c) {
		try {
			BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement               
		  ("select num_cli from  client where telephone= ?");

				ps.setString(1, c.getTelephone());
				ps.executeQuery();
				ResultSet r=ps.getResultSet();
				if(r.next()) {
					return r.getInt("num_cli");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public  int addClient(Client c) throws ClassNotFoundException, SQLException {
		int num_cli=checkClientExist(c);
		if(num_cli!=0)
			return num_cli;
		
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                  ("insert into Client ( nom, prenom, email, telephone) "
                  		+ "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getPrenom());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getTelephone());
        
        int i=ps.executeUpdate();
        
        if(i>0)
        {
    	ResultSet rs=ps.getGeneratedKeys();
    	if(rs.next()) {
    		c.setNum_cli(rs.getInt(1));
    		System.out.println(c.getNum_cli());
    		
    		
    	}
        }
        return c.getNum_cli();
		
	}
	
	public static boolean updateClient(int num_cli,String adresse, String profession, double revenue_mensuel) throws ClassNotFoundException, SQLException {
			BaseDeDonnees bd= new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
							("update client set addresse=? ,profession=? ,revenue_mensuel=? where num_cli= ?");//tanchofo
			ps.setString(1, adresse);
			ps.setString(2, profession);
			ps.setDouble(3, revenue_mensuel);
			ps.setInt(4, num_cli);
 
     int i=ps.executeUpdate();
     return i==1;//return true si la requette a bien été executé
	}
	public static boolean isBlackListed(int num_cli) throws ClassNotFoundException, SQLException {
		
		try {
			BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement               
		  ("select blacklist from  client where num_cli= ?");

				ps.setInt(1, num_cli);
				ps.executeQuery();
				ResultSet r=ps.getResultSet();
				if(r.next()) {
				//	System.out.println(" f "+ r.getInt("blacklist"));
				return r.getInt("blacklist")==1;
				}
				//return Integer.parseInt(r.getString(0))==1;
				//return true si le client est en blacklist
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return true;
		
	}
	
}
