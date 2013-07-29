import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDB {
	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/parkdb?user=root&password=root";
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static Statement getStmt(Connection conn){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return stmt;
	}
	public static PreparedStatement getPreStmt(Connection conn,String sql){
		PreparedStatement prestmt = null;
		try {
			prestmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prestmt;
	}
	public static ResultSet executeQuery(Statement stmt,String sql){
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rs;
	}
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	public static void close(PreparedStatement prestmt){
		if(prestmt != null){
			try {
				prestmt.close();
				prestmt = null;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement stmt){
		if(stmt != null){
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	
}
