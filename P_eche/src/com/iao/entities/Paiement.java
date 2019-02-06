package com.iao.entities;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.iao.persistance.BaseDeDonnees;

public class Paiement {
	private int num_pai;
	private double echeance;
	private String date;
	private int num_fac;
	
	
	
	public int getNum_pai() {
		return num_pai;
	}
	public void setNum_pai(int num_pai) {
		this.num_pai = num_pai;
	}
	public double getEcheance() {
		return echeance;
	}
	public void setEcheance(double echeance) {
		this.echeance = echeance;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getNum_fac() {
		return num_fac;
	}
	public void setNum_fac(int num_fac) {
		this.num_fac = num_fac;
	}
	public Paiement(double echeance, String date, int num_fac) {
		super();
		this.echeance = echeance;
		this.date = date;
		this.num_fac = num_fac;
	}
	
	public static void enregistrerPaiement(int num_fac,double somme) throws ClassNotFoundException, SQLException {
		//echeance f bd hya la somme 
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                ("insert into paiement ( echeance,date_paiement,num_fac) "
                		+ "values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
      ps.setDouble(1, somme);
      LocalDate dateCredit=java.time.LocalDate.now();
      ps.setString(2, dateCredit.toString());
      ps.setInt(3, num_fac);
      
      ps.executeUpdate();
    
	}

}
