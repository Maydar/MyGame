package ru.mail.projects.account.database.impl;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class TExecutor {

	@SuppressWarnings("finally")
	public <T> T execSelectQuery (Connection connection, String query,
							TResultHandler<T> handler) {
		T value = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			
			stmt = connection.createStatement();
			stmt.execute(query);
			result = stmt.getResultSet ();
			if (result.next()) { result.previous(); value = handler.handle(result); }
			else value = null;
		}
		catch (SQLException sql) {
			
			System.err.println (sql.getMessage());
			System.err.println ("Error with the query");
		}
		finally {
			
			try {
				if (result != null) result.close ();
				if (stmt != null) stmt.close ();
			}
			catch (SQLException sqlE) { System.err.println (sqlE.getMessage()); }
			return value;
		}
	}
	
	public void insertUser (Connection connection, String UserName) {
		
		String insert = "INSERT INTO Users (Name, GameNum, WinNum, LostNum) values (?, 0, 0, 0)";
		PreparedStatement stmt = null;
		try {
			
			stmt = connection.prepareStatement(insert);
			stmt.setString(1, UserName);
			stmt.executeUpdate();
		}
		
		catch (SQLException sql) {
			
			System.err.println (sql.getMessage());
		}
		finally {
			
			try {
				if (stmt != null) stmt.close ();
			}
			catch (SQLException sqlE) { System.err.println (sqlE.getMessage()); }
		}
	}
	
	public void updateStatistics (Connection connection, String User1, String User2, boolean first_win) {
		
		String update_win =  "UPDATE Users SET GameNum = GameNum + 1, WinNum = WinNum + 1 WHERE " +
		                     "UserName = ?;";
		String update_lost = "UPDATE Users SET GameNum = GameNum + 1, WinLost = WinLost + 1 WHERE " +
                			 "UserName = ?;";
		PreparedStatement stmt_win = null;
		PreparedStatement stmt_lost = null;
		
		try {
			
			connection.setAutoCommit(false);
			stmt_win  = connection.prepareStatement(update_win);
			stmt_lost = connection.prepareStatement(update_lost);
			
			if (first_win) {
				
				stmt_win.setString(1, User1);
				stmt_lost.setString(1, User2);
			}
			else {
				stmt_win.setString(1, User2);
				stmt_lost.setString(1, User1);
			}
			
			stmt_win.executeUpdate();
			stmt_lost.executeUpdate ();
			connection.commit ();
		}
		catch (SQLException sql) {
			
			System.err.println (sql.getMessage());
		}
		finally {
			
			try {
				if (stmt_win  != null) stmt_win.close ();
				if (stmt_lost != null) stmt_lost.close ();
			}
			catch (SQLException sqlE) { System.err.println (sqlE.getMessage()); }
		}
	}
}
