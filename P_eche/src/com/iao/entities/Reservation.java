package com.iao.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iao.persistance.BaseDeDonnees;

public class Reservation {
	private int num_reser;
	private String date_res;
	private String date_debut;
	private String date_fin;
	private String type;
	private int id_voyage;
	private int num_cli;
	private String confirmationRes;
	
	
	public Reservation() {
		super();
	}
	
	public Reservation(String date_res, String date_debut, String date_fin, int id_voyage, int num_cli) {
		super();
		this.date_res = date_res;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.id_voyage = id_voyage;
		this.num_cli = num_cli;
	}
	public int getNum_reser() {
		return num_reser;
	}
	public void setNum_reser(int num_reser) {
		this.num_reser = num_reser;
	}
	public String getDate_res() {
		return date_res;
	}
	public void setDate_res(String date_res) {
		this.date_res = date_res;
	}
	public String getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}
	public String getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId_voyage() {
		return id_voyage;
	}
	public void setId_voyage(int id_voyage) {
		this.id_voyage = id_voyage;
	}
	public int getNum_cli() {
		return num_cli;
	}
	public void setNum_cli(int num_cli) {
		this.num_cli = num_cli;
	}
	public String getConfirmationRes() {
		return confirmationRes;
	}
	public void setConfirmationRes(String confirmationRes) {
		this.confirmationRes = confirmationRes;
	}
	
	
	public static int addReservation(Reservation r) throws ClassNotFoundException, SQLException {
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                  ("insert into reservation ( date_res,date_debut,date_fin,id_voyage,num_cli,type_reservation) "
                  		+ "values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, r.getDate_res());
        ps.setString(2, r.getDate_debut());
        ps.setString(3, r.getDate_fin());
        ps.setLong(4, r.getId_voyage());
        ps.setLong(5, r.getNum_cli());
        ps.setString(6, "prereservation");

        
        
		
        int i=ps.executeUpdate();
        
        if(i>0)
        {
    	ResultSet rs=ps.getGeneratedKeys();
    	if(rs.next()) {
    		r.setNum_reser(rs.getInt(1));
    		System.out.println(r.getNum_reser());
    		return rs.getInt(1);
    	}
        }
        return 0;
	}
	
	
	public static boolean confirmReserv(int num_reser) throws ClassNotFoundException, SQLException {
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                  ("update reservation set  type_reservation =	'prereservation' where num_reser= "+num_reser);
       
		  int i=ps.executeUpdate();
       return i==1;//return true si la requet s'est bien ex�cut� sinon false
		
	}
	
	public static int annuleReserv(int num_reser) {
		//set type_reservation= reserv annulle;
		// confirmation annulle;
		BaseDeDonnees bd;
		try {
			bd = new BaseDeDonnees();
			  PreparedStatement ps=bd.getConnection().prepareStatement
		                ("update reservation set  type_reservation =	'annul�e' where num_reser= "+num_reser);
		     
		     ps.executeUpdate();
		     
		     PreparedStatement ps2=bd.getConnection().prepareStatement
		                ("update reservation set   	confirmationRes = 'annul�e' where num_reser= "+num_reser);
		     
		     return (ps2.executeUpdate());
		     
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return 0;
	}
	public static boolean procederReservDefinit(int num_reser) {
		BaseDeDonnees bd;
		try {
			bd = new BaseDeDonnees();
			  PreparedStatement ps=bd.getConnection().prepareStatement
("update reservation set type_reservation =	'definitive', confirmationRes = 'confirm�e' where num_reser= "+num_reser);
		     
		    int i=ps.executeUpdate();
		     
		     return i==1;//return true si les deux requettes on bien ete execute
		     
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;//en cas d'erreur sql
		}
		
	}
	
	
	
	
}
