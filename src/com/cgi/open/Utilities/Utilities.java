package com.cgi.open.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
	public static void closeResources(Connection conn, 
			PreparedStatement pstmt, ResultSet rs) throws NullPointerException {
		try  {
			if(rs!=null){
			rs.close();
			}
			if(pstmt!=null){
			pstmt.close();
			}
			if(conn!=null){
			conn.close();
			}
			System.out.println("Resources Closed!");
		}
		catch(SQLException ex) {
			System.out.println("Cudnt close resources!");
		}
	}
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql:///easyshare", "root", "root");
		}
		catch(ClassNotFoundException clex) {
			System.out.println(clex.getMessage());
			throw new IllegalStateException("SQLException");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("SQLException");
		}
		return conn;
	}
}