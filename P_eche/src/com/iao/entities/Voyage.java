package com.iao.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iao.persistance.BaseDeDonnees;

public class Voyage {
	private int num_voy;
	private double tarif;
	private String typeDestin;
	private String typeLog;
	private String activites;
	
	public Voyage(double tarif, String typeDestin, String typeLog, String activites) {
		super();
		this.tarif = tarif;
		this.typeDestin = typeDestin;
		this.typeLog = typeLog;
		this.activites = activites;
	}
	public Voyage(double tarif, String typeDestin, String activites) {
		super();
		this.tarif = tarif;
		this.typeDestin = typeDestin;
		this.activites = activites;
	}
	public int getNum_voy() {
		return num_voy;
	}
	public void setNum_voy(int num_voy) {
		this.num_voy = num_voy;
	}
	public double getTarif() {
		return tarif;
	}
	public void setTarif(double tarif) {
		this.tarif = tarif;
	}
	public String getTypeDestin() {
		return typeDestin;
	}
	public void setTypeDestin(String typeDestin) {
		this.typeDestin = typeDestin;
	}
	public String getTypeLog() {
		return typeLog;
	}
	public void setTypeLog(String typeLog) {
		this.typeLog = typeLog;
	}
	public String getActivites() {
		return activites;
	}
	public void setActivites(String activites) {
		this.activites = activites;
	}
	
	
	
	
	public int addVoyage(Voyage v) throws ClassNotFoundException, SQLException {
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                  ("insert into voyage ( tarif, typeDestin, typeLog, activites) "
                  		+ "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, v.getTarif());
        ps.setString(2, v.getTypeDestin());
        ps.setString(3, "");
        ps.setString(4, v.getActivites());
        
        
        int i=ps.executeUpdate();
        if(i>0)
        {
    	ResultSet rs=ps.getGeneratedKeys();
    	if(rs.next()) {
    	
    		return rs.getInt(1);
    	}
		
	}return 0;
	
	
	}
	
}
