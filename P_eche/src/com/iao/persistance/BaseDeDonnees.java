package com.iao.persistance;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import java.sql.*;




public class BaseDeDonnees {
	public static String getString(Date d) {
		   return new SimpleDateFormat("yyyy-MM-dd").format(d);
		}

	private Connection connection;

	public BaseDeDonnees() throws ClassNotFoundException, SQLException {


		Class.forName("com.mysql.cj.jdbc.Driver");

		
		String url = "jdbc:mysql://localhost/aitlhouari_boudouar?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String utilisateur = "root";
        String motDePasse = "";
        
        connection = DriverManager.getConnection( url, utilisateur, motDePasse );
           

	}
	public Connection getConnection() {
		return connection;
	}
	


}