package com.iao.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.iao.entities.Client;
import com.iao.entities.Facture;
import com.iao.entities.Paiement;
import com.iao.entities.Reservation;
import com.iao.entities.Voyage;
import com.iao.persistance.BaseDeDonnees;

public class WebServices {
	
		public int creeClient(String nom,String prenom,String telephone,String email)  {//return num_cli
			Client c=new Client(nom,prenom,telephone,email);
			try {
				return c.addClient(c);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
			return 	0;
			}
			
			
		}
		
		public int creeReservation(String date_res,
				String date_debut, 
				String date_fin, 
				String destination,
				String activite,
				double tarif
				, int num_cli,double acompte) {
			Voyage v = new Voyage(tarif,destination,activite);
			//return num_reser
			try {
				double somme=tarif/10;//l'accompte egal la somme initial que le client paie
				int id_voyage = v.addVoyage(v);
				int num_reser =Reservation.addReservation(new Reservation(date_res, date_debut,  date_fin, id_voyage, num_cli));
				int num_fac=Facture.addFacture(new Facture(tarif, num_reser, num_cli,somme,acompte));
				Paiement.enregistrerPaiement(num_fac, somme);
				return num_reser;
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		
		public boolean confirmPreres(int num_res) throws ClassNotFoundException, SQLException {
			
			return Reservation.confirmReserv(num_res);
		}
		
		public boolean preparationDossier(int num_cli,String adresse,String profession, double revenue_mensuel) {
				try {
					return Client.updateClient(num_cli, adresse, profession, revenue_mensuel);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				
		}
		
		public boolean etudeDossier(int num_cli) {
			try {//si le client est dans la blacklist la reponse sur l'etude du dossier est false
				//si le client n'est pas dans la blacklist la reponse de la methode est true
				return !Client.isBlackListed(num_cli);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean procederReservDefinit(int num_reser) {
			return Reservation.procederReservDefinit(num_reser);
			
		}
		public boolean payeecheance(double somme,int num_fac) {
			//incrementer la somme total bla somme li ghayched db
			
			try {
				
				Paiement.enregistrerPaiement(num_fac, somme);
				return Facture.receptionPaiement( num_fac,somme);
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		
		public Facture getFacture(int num_fac) {
			return Facture.getFacture(num_fac);
			
		}
		public Facture getFactureByClient(int num_cli,int num_reser) {
			return Facture.getFactureByClient(num_cli,num_reser);
			
		}
		
		public boolean  finPaiement(int num_fac) {//retourne true si somme==montant sinon false
			return Facture.finPaiement(num_fac);
		}
		
		
		public int annuleReserv(int num_res) {
			//set type_reservation= reserv annulle;
			// confirmation annulle;
			
			return Reservation.annuleReserv(num_res);
			
		}
		public boolean rremb(int num_cli,int num_reser) {
			
			
			return Facture.rembourser(num_cli,num_reser);
			
		}
		public boolean rembourser(int num_fac) {
			//facture setAccompte=accompte*0.02;
			return Facture.rembourser(num_fac);
		}
		
		
		public boolean paiementImmediat(double somme,int num_fac) {
			//incrementer la somme 
			
			try {
				Paiement.enregistrerPaiement(num_fac, somme);
				return Facture.receptionPaiement( num_fac,somme);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return false;
				
			}
			//Reservation.confirmReserv(num_res);
			//
		}public boolean paiementImmediatClient(double somme,int num_cli,int num_res) {
			//incrementer la somme 
			
			try {
				Facture f=getFactureByClient(num_cli,num_res);
				Paiement.enregistrerPaiement(f.getNum_fac(), somme);
				return Facture.receptionPaiement(f.getNum_fac(),somme);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return false;
				
			}
			//Reservation.confirmReserv(num_res);
			//
		}
		
		
		
}
