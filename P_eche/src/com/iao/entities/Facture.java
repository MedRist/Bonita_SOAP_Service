package com.iao.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iao.persistance.BaseDeDonnees;

public class Facture {
	private int num_fac;
	private double montant;
	private int num_reser;
	private int num_cli;
	private double somme;
	private double acompte;
	public int getNum_fac() {
		return num_fac;
	}
	public void setNum_fac(int num_fac) {
		this.num_fac = num_fac;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public int getNum_reser() {
		return num_reser;
	}
	public void setNum_reser(int num_reser) {
		this.num_reser = num_reser;
	}
	public int getNum_cli() {
		return num_cli;
	}
	public void setNum_cli(int num_cli) {
		this.num_cli = num_cli;
	}
	public double getSomme() {
		return somme;
	}
	public void setSomme(double somme) {
		this.somme = somme;
	}
	public double getAcompte() {
		return acompte;
	}
	public void setAcompte(double acompte) {
		this.acompte = acompte;
	}
	public Facture(double montant, int num_reser, int num_cli) {
		super();
		this.montant = montant;
		this.num_reser = num_reser;
		this.num_cli = num_cli;
		this.somme = montant*0.1;
		this.acompte = montant*0.1;
	}
	
	public Facture(int num_fac,double montant, int num_reser, int num_cli) {
		super();
		this.num_fac=num_fac;
		this.montant = montant;
		this.num_reser = num_reser;
		this.num_cli = num_cli;
		this.somme = montant*0.1;
		this.acompte = montant*0.1;
	}
	
	public Facture(double montant, int num_reser, int num_cli,double somme,double acompte) {
		super();
		this.montant = montant;
		this.num_reser = num_reser;
		this.num_cli = num_cli;
		this.somme = somme;
		this.acompte = acompte;
	}
	
	public Facture(int num_fac,double montant, int num_reser, int num_cli,double somme,double acompte) {
		super();
		this.num_fac=num_fac;
		this.montant = montant;
		this.num_reser = num_reser;
		this.num_cli = num_cli;
		this.somme = somme;
		this.acompte = acompte;
	}
	
	
	public Facture() {
		// TODO Auto-generated constructor stub
	}
	public static int addFacture(Facture f) throws ClassNotFoundException, SQLException {
		BaseDeDonnees bd= new BaseDeDonnees();
		  PreparedStatement ps=bd.getConnection().prepareStatement
                  ("insert into facture ( montant, num_reser, num_cli, somme, acompte) "
                  		+ "values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, f.getMontant());
        ps.setDouble(2, f.getNum_reser());
        ps.setDouble(3, f.getNum_cli());
        ps.setDouble(4, f.getSomme());
        ps.setDouble(5, f.getAcompte());
        
        int i=ps.executeUpdate();
        if(i>0)
        {
    	ResultSet rs=ps.getGeneratedKeys();
    	if(rs.next()) {
    		return rs.getInt(1);

    	}
        }
        return 0;
	}
	
	public static boolean receptionPaiement(int num_fac,double somme) {
		
		
		try {
			BaseDeDonnees bd;
			bd = new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
					  ("update facture set somme= somme+ ? where num_fac= ?");
			ps.setDouble(1, somme);		  	
			ps.setInt(2, num_fac);
			   int i=ps.executeUpdate();
			   return i==1;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		  
		
	}
	public static Facture getFacture(int num_fac) {
			try {
				BaseDeDonnees bd = new BaseDeDonnees();
				PreparedStatement ps=bd.getConnection().prepareStatement               
						  ("select * from  facture where num_fac= ?");
								ps.setInt(1, num_fac);
								ps.executeQuery();
								ResultSet r=ps.getResultSet();
								if(r.next()) {              // here
									return new Facture(r.getInt("num_fac"),r.getDouble("montant"),r.getInt("num_reser"),r.getInt("num_cli"));
								    }
								return null;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e);
				return null;
			}
		  
				
				
				
		
	}
	
	public static boolean finPaiement(int num_fac){
		double somme=0;
		double montant=0;
		try {
			BaseDeDonnees bd = new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
					  ("select montant from facture where num_fac= ?");

			ps.setInt(1, num_fac);
			ps.executeQuery();
							
			ResultSet r=ps.getResultSet();
			
			if(r.next()){
				//System.out.println(r.getFetchSize());
				montant=r.getDouble(1);
				//System.out.println("montant: "+montant);
			}
							
			PreparedStatement ps2=bd.getConnection().prepareStatement               
									  ("select somme from facture where num_fac= ?");

			ps2.setInt(1, num_fac);
			ps2.executeQuery();
			ResultSet r2=ps2.getResultSet();
			if(r2.next()){
				somme=r2.getDouble(1);
				//System.out.println("somme: "+somme);
				}

        
		
		return somme==montant;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	public static boolean rembourser(int numCli, int numReser) {
		//facture setAccompte=accompte*0.02;
		try {
			BaseDeDonnees bd;
			bd = new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
					  ("update facture set acompte= acompte*0.02,somme=somme*0.02 where num_cli= ? AND num_reser= ?");
				
			ps.setInt(1, numCli);
			ps.setInt(2, numReser);

			   int i=ps.executeUpdate();
			   return i==1;//return true si la requete a bien ete execute
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean rembourser(int num_fac) {
		//facture setAccompte=accompte*0.02;
		try {
			BaseDeDonnees bd;
			bd = new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
					  ("update facture set acompte= acompte*0.02 where num_fac= ?");
				
			ps.setInt(1, num_fac);
			   int i=ps.executeUpdate();
			   return i==1;//return true si la requete a bien ete execute
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	public static Facture getFactureByClient(int num_cli,int num_reser){
	
		try {
			BaseDeDonnees bd = new BaseDeDonnees();
			PreparedStatement ps=bd.getConnection().prepareStatement               
					  ("select * from facture where num_cli= ? AND num_reser= ?");

			ps.setInt(1, num_cli);
			ps.setInt(2, num_reser);
			ps.executeQuery();
							
			ResultSet r=ps.getResultSet();
			
			if(r.next()){
				return new Facture(r.getInt("num_fac"),r.getDouble("montant"),r.getInt("num_reser"),r.getInt("num_cli"),r.getInt("somme"),r.getInt("acompte"));
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Facture();
		
	}


}
