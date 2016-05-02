package com.assignment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assignment.Config;
import com.assignment.utils.PrintUtil;

public class Model {
	public static Connection connect = null;
	public static Statement statement = null;

	private static String JDBC_CONN_PREFIX = "jdbc:mysql://";
	public static Config config;

	public static Map<String, List<String>> columnNames = new HashMap<String, List<String>>();

	public static boolean init() {

		if (config == null) {
			config = new Config();
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(
					JDBC_CONN_PREFIX + config.getProperty("host") + "/" + config.getProperty("database") + "?" + "user="
							+ config.getProperty("user") + "&password=" + config.getProperty("password"));

			statement = connect.createStatement();
			PrintUtil.printInNewLine("connection established.");

			loadTableStructure(config);
			PrintUtil.printInNewLine("table structures loaded.");

			return true;

		} catch (Exception e) {

			return PrintUtil.printException(e);
		}
	}

	private static void loadTableStructure(Config config) throws SQLException {
		String tables = config.getProperty("tables");

		ResultSet resultSet = null;

		for (String table : tables.split(",")) {

			resultSet = statement.executeQuery("select * from " + table);
			List<String> columns = new ArrayList<>();

			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
				columns.add(resultSet.getMetaData().getColumnName(i));

			columnNames.put(table, columns);
		}
		resultSet.close();
	}

	public static String formInsertQuery(String table) {

		List<String> columns = columnNames.get(table);

		String query = "insert into ";
		query += config.getProperty("database") + "." + table + " ";
		query += "(" + columns.get(1);

		for (int i = 2; i < columns.size(); i++)
			query += ", " + columns.get(i);

		query += ") ";
		query += "VALUES (?";

		for (int i = 2; i < columns.size(); i++)
			query += ", ?";

		query += ")";
		return query;
	}

	public static String formUpdateQuery(String table) {

		List<String> columns = columnNames.get(table);

		// String updateTableSQL = "UPDATE DBUSER SET USERNAME = ? "
		// + " WHERE USER_ID = ?";

		String query = "UPDATE ";
		query += config.getProperty("database") + "." + table + " Set ";
		query += columns.get(1) + " = ?";

		for (int i = 2; i < columns.size(); i++)
			query += ", " + columns.get(i) + " = ?";

		query += " WHERE id = ?";

		return query;
	}

	public static String formSelectQuery(String table, List<Long> idValues) {

		String query = "SELECT" + " * ";
		query += "from" + " ";
		query += config.getProperty("database") + "." + table;

		if (idValues != null && idValues.size() > 0) {
			query += " WHERE id IN (" + idValues.get(0);

			for (int i = 1; i < idValues.size(); i++)
				query += "," + idValues.get(i);

			query += ")";

		}
		return query;
	}

	public static String formDeleteQuery(String table, List<Long> idValue) {

		// "DELETE FROM Registration WHERE id = 101";

		String query = "DELETE FROM " + table;

		if (idValue != null && idValue.size() > 0) {
			query += " WHERE id IN (" + idValue.get(0);

			for (int i = 1; i < idValue.size(); i++)
				query += "," + idValue.get(i);

			query += ")";
		}
		return query;

	}

	// You need to close the resultSet
	// private static void close() {
	// try {
	// if (resultSet != null) {
	// resultSet.close();
	// }
	// if (statement != null) {
	// statement.close();
	// }
	//
	// if (connect != null) {
	// connect.close();
	// }
	// } catch (Exception e) {
	//
	// }
	// }

}
