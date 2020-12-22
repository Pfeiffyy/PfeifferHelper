package de.pfeiffy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class dbHelper {
	static String db = "db_generator.db";

	public static void main(String[] args) {
		insert();
	}

	public static void importInDB(ArrayList<DbFeld> dbfelder, String tabelle, String url) {

	}

	public static void insert() {
		String sql = "INSERT INTO tab1(name,capacity) VALUES('11111','222222')";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Connection connect() {
		Connection conn = null;
		try {
			// db parameters
			String url = db;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return conn;
	}

}
