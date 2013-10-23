package ru.mail.projects.account.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class UsersDAO {

	private TExecutor execT;
	Connection conn;
	
	public UsersDAO (Connection conn) {
		
		execT = new TExecutor ();
		this.conn = conn;
	}
	
	UsersDataSet getByName (String name) {
		
		Integer id_user = new Integer (0);
		String query = "SELECT idUsers FROM Users WHERE Name = '" + name + "';";
		id_user = execT.execSelectQuery(conn, query, 
				new TResultHandler <Integer> () {
			
			public Integer handle (ResultSet result) throws SQLException {
				
				result.next();
				return result.getInt("idUsers");
			}
		});
		if (id_user == null) return null;
		UsersDataSet usData = new UsersDataSet (id_user, name); 
		return usData;
	}
	
	void add (UsersDataSet dataset) {
		
		execT.insertUser(conn, dataset.getName());
	}
	
}
