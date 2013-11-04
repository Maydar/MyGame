package ru.mail.projects.account.database.impl;
import java.sql.*;

public interface TResultHandler<T> {
	
	T handle (ResultSet result) throws SQLException;
}
