package VendingMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Backend {
	//Creating variables for the database connection and the markup on the costprice of items
	private static String user = "myuser";
	private static String password = "12345";
	private static String path = "jdbc:mysql://localhost:3306";
	private static String newPath = "jdbc:mysql://localhost:3306/VendingMachine_db?useSSL=false";
	static double markUp = 1.25;

	Backend() throws SQLException {

		//Establishing the connection and creating database with a table
		System.out.println("Establishing connection...");
		Connection database = DriverManager.getConnection(path, user, password);
		System.out.println("Establishing statement...");
		Statement stateMent = database.createStatement();
		System.out.println("Creating database...");
		String sqlQuery = "create database if not exists VendingMachine_db;";
		stateMent.executeUpdate(sqlQuery);
		System.out.println("using Vending Machine database...");
		sqlQuery = "use VendingMachine_db";
		stateMent.executeUpdate(sqlQuery);
		System.out.println("Creating table inventory for database...");
		sqlQuery = "create table if not exists inventory (id int, item varchar(30), price int, qty int, supplier varchar(40))";
		stateMent.executeUpdate(sqlQuery);

		System.out.println("Generated database called VendingMachine with table inventory...");

	}

	//Views all the items currently in the database
	public static DefaultListModel<String> viewAll() throws SQLException {
		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		DefaultListModel<String> listOfItems = new DefaultListModel<String>();
		String sqlSelect = "select * from inventory";
		System.out.println("The SQL query is: " + sqlSelect);
		ResultSet rset = stateMent.executeQuery(sqlSelect);

		while (rset.next()) {

			String str = rset.getInt("id") + " - " + rset.getString("item") + " - " + rset.getInt("price") + " - "
					+ rset.getInt("qty") + " - " + rset.getString("supplier");
			listOfItems.addElement(str);

		}

		return listOfItems;

	}

	//Views only a limited amount of information for the user (Item, price and quantity)
	public static DefaultListModel<String> viewAllLimited() throws SQLException {
		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		DefaultListModel<String> listOfItems = new DefaultListModel<String>();
		String sqlSelect = "select * from inventory";
		System.out.println("The SQL query is: " + sqlSelect);
		ResultSet rset = stateMent.executeQuery(sqlSelect);

		while (rset.next()) {

			String str = rset.getString("item") + " - "
					+ (Math.round(rset.getInt("price") * markUp) + " - " + rset.getInt("qty"));
			listOfItems.addElement(str);

		}

		return listOfItems;

	}

	//Adds an item to the database, using the fields at the top of the administrator page
	public static void add(int id, String item, int price, int qty, String supplier) throws SQLException {

		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		PreparedStatement prepareStatement = database.prepareStatement("insert into inventory values (?,?,?,?,?)");
		prepareStatement.setInt(1, id);
		prepareStatement.setString(2, item);
		prepareStatement.setInt(3, price);
		prepareStatement.setInt(4, qty);
		prepareStatement.setString(5, supplier);

		prepareStatement.executeUpdate();

		System.out.println(prepareStatement + " records inserted.\n");

	}

	//After selecting an item; the administrator can update any information; the id is fixed and wont be able to be updated
	public static void update(int id, String item, int price, int qty, String supplier, int id2) throws SQLException {
		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		PreparedStatement prepareStatement = database.prepareStatement(
				"update inventory set id = ?, item = ?, price = ?, qty = ?, supplier = ? where id = ?");
		prepareStatement.setInt(1, id);
		prepareStatement.setString(2, item);
		prepareStatement.setInt(3, price);
		prepareStatement.setInt(4, qty);
		prepareStatement.setString(5, supplier);
		prepareStatement.setInt(6, id2);

		prepareStatement.executeUpdate();

	}

	//This method is for the quanitity update when the user purchases an item
	public static void updateQty(String item, int qty) throws SQLException {

		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		PreparedStatement prepareStatement = database.prepareStatement("update inventory set qty = ? where item = ?");
		prepareStatement.setInt(1, qty);
		prepareStatement.setString(2, item);
		prepareStatement.executeUpdate();

	}

	//This deletes an item selected from the database, 
	public static void delete(int id, String item, int price, int qty, String supplier) throws SQLException {

		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		PreparedStatement prepareStatement = database.prepareStatement(
				"delete from inventory where id = ? and item = ? and price = ? and qty = ? and supplier = ? ");
		prepareStatement.setInt(1, id);
		prepareStatement.setString(2, item);
		prepareStatement.setInt(3, price);
		prepareStatement.setInt(4, qty);
		prepareStatement.setString(5, supplier);
		prepareStatement.executeUpdate();
	}

	//This searches for an item in the database
	public static DefaultListModel<String> search(int id) throws SQLException {

		Connection database = DriverManager.getConnection(newPath, user, password);
		Statement stateMent = database.createStatement();
		DefaultListModel<String> listOfItems = new DefaultListModel<String>();
		PreparedStatement prepareStatement = database.prepareStatement("select * from inventory where id = ? ");
		System.out.println("Selecting from table...");
		prepareStatement.setInt(1, id);

		ResultSet resultSet = prepareStatement.executeQuery();

		while (resultSet.next()) {

			String str = resultSet.getInt("id") + " - " + resultSet.getString("item") + " - "
					+ resultSet.getInt("price") + " - " + resultSet.getInt("qty") + " - "
					+ resultSet.getString("supplier");
			listOfItems.addElement(str);

		}
		System.out.println("Selected...");
		return listOfItems;

	}

}
