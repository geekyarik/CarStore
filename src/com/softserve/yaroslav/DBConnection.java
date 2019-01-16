package com.softserve.yaroslav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Using this class you can make the connection with SQL Server and execute
 * Query statements. Result you get in Result set.
 * 
 * @author y3809
 *
 */
public class DBConnection {

	private static Connection connection = null; // connection for DB
	private static Statement statement = null; // Query statement for DB
	private static ResultSet resSet = null; // result set of DB Query executing

	private static String username = "y3809"; // user name for singing in
	private static String password = ""; // password for singing in
											// dbc:mysql://localhost:3306/TIGER?createDatabaseIfNotExist=true";
	private static String URL = "jdbc:sqlserver://GEEKS-LAPTOP;integratedSecurity=true;"; // "Server=localhost;Database=master;Trusted_Connection=True;"
	private static String DBName = "CARSTORE"; // "COMPANY";

	/*
	 * Constructor with initializing connection
	 */
	public DBConnection(String dbName) {
		DBName = dbName;

		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

			connection = DriverManager.getConnection(URL, username, password);

			if (connection != null) {
				System.out.println("Connection Successful! \n");
			} else {
				System.out.println("Connection ERROR \n");
				System.exit(1);
			}

			statement = connection.createStatement();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/*
	 * Method for disconnecting
	 */
	public void disconnect() {
		try {
			if (resSet != null) {
				resSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/*
	 * Getter for ResultSet
	 */
	public ResultSet getResultSet() {
		return resSet;
	}

	/*
	 * Method for showing result from result set
	 */
	public void showResultSet() {

		try {
			int columnCount = resSet.getMetaData().getColumnCount();

			while (resSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(resSet.getString(i) + "\t");
				}
				System.out.println();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	/*
	 * Method for checking if database is created by name
	 */
	public boolean checkIsDBCreated(String name) {

		String chekingStatement = "DECLARE @dbname nvarchar(128) SET @dbname = '" + name + "' "
				+ "IF (EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE ('[' + name + ']' = @dbname "
				+ "OR name = @dbname))) select name from master.dbo.sysdatabases where name like '" + name + "'";

		try {
			resSet = statement.executeQuery(chekingStatement);

			if (resSet.next() && name.equals((resSet.getString(1)))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	/*
	 * Method for database creating by name
	 */
	public boolean createDB() {

		String name = DBName;

		try {

			if (checkIsDBCreated(name))
				return true;

			statement.executeQuery("CREATE DATABASE " + name);

			return checkIsDBCreated(name);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}

	}

	/*
	 * Method for creating table called Cars
	 */
	public boolean createTableCars() {

		String tableName = "CARS";
		String creatingQuery = String.format("USE %s CREATE TABLE %s (id INT PRIMARY KEY, "
				+ "brand VARCHAR(30), model VARCHAR(30), gearbox VARCHAR(30), details VARCHAR(30), "
				+ "id_user INT, engine_capacity INT, year INT, mileage INT, price INT, FOREIGN KEY(ID_USER) REFERENCES USERS(ID));",
				DBName, tableName);
		String addKeyQuery = String.format(
				"USE %s ALTER TABLE %s ADD FOREIGN KEY(ID_USER) REFERENCES USERS(ID) ON DELETE CASCADE", DBName,
				tableName);

		try {
			statement.executeQuery(creatingQuery);
			// statement.executeQuery(addKeyQuery);
			return checkIsTableCreated(tableName);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	/*
	 * Method for creating table called Cars
	 */
	public boolean createTableUsers() {

		String tableName = "USERS";
		String query = String.format(
				"USE %s CREATE TABLE %s (id INT PRIMARY KEY, "
						+ "username VARCHAR(30), firstname VARCHAR(30), secondname VARCHAR(30), login VARCHAR(30), "
						+ "password VARCHAR(30), email VARCHAR(30), phone VARCHAR(30), is_active BIT, is_admin BIT) ",
				DBName, tableName);

		try {
			statement.executeQuery(query);

			return checkIsTableCreated(tableName);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	/*
	 * Method for checking if table is created
	 */
	public boolean checkIsTableCreated(String name) {

		String isTableCreated = "IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES"
				+ "WHERE TABLE_NAME = N'%s') BEGIN PRINT 'exists' END";
		String chekingStatement = String.format(isTableCreated, name);
		String exists = new String("exists");

		try {
			resSet = statement.executeQuery(chekingStatement);

			if (resSet.next() && exists.equals(resSet.getString(1))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
}
