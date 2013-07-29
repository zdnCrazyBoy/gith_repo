
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.SAXReader;


//解析xml的类
public class XmlResolve {
	/*public Element xmlInput(File file){
		 SAXReader saxReader=new SAXReader();
	    
	     Document doc = null;
	     Element root = null;
	     Element elementSon = null;
	     String elementValue = null;
	     
		 try {
			doc = saxReader.read(file);
			root = doc.getRootElement();
			Iterator i = root.elementIterator();
			
			//elementSon = (Element)i.next();
			elementSon = root.element("command");
			if(elementSon.getText().equals("LOG_IN")){
				Element username = root.element("userName");
				Element password = root.element("password");
				
			}
			elementValue = elementSon.getText();
			
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		return elementSon;
		
	}*/
	
	public void estimate(File file,PrintWriter writer){
		 SAXReader saxReader=new SAXReader();
		    
	     Document doc = null;
	     Element root = null;
	     Element elementSon = null;
	     String elementValue = null;
	     
		 try {
			doc = saxReader.read(file);
			root = doc.getRootElement();
			Iterator i = root.elementIterator();
			
			//elementSon = (Element)i.next();
			elementSon = root.element("command");
			
			elementValue = elementSon.getText();
			
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		//判断command
			if(elementSon.getText().equals("REGIST")){
				//查询数据库
			String status = "1";
				Element username = root.element("username");
				Element password = root.element("password");
				String sql = "select * from user where username =" +"'"+username.getText()+"'";
System.out.println(sql);
				String update = "insert into user values("+"'"+username.getText()+"'"+","+"'"+password.getText()+"'"+","+3+")";
System.out.println(update);				
				Connection conn = UserDB.getConn();
				Statement statement = UserDB.getStmt(conn);
				PreparedStatement prestmt = null;
				ResultSet rs = UserDB.executeQuery(statement, sql);
				String user_name = null;
				try {
					while(rs.next()){
						user_name = rs.getString("username");	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(username.getText().equals(user_name)){
					System.out.println("user exsit!");
					
				}else{
					System.out.println("ok!可以使用此用户名");
					prestmt = UserDB.getPreStmt(conn, update);
					try {
						prestmt.executeUpdate(update);
						System.out.println("insert ok!");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					status = "0";
				}
				UserDB.close(conn);
				UserDB.close(prestmt);
				UserDB.close(rs);
				response re = new response();
				re.respinseTO(writer, "REGIST", status);
			}else if(elementSon.getText().equals("CONNECTSERVER")){
				//向服务器发送请求
				
			}
			else if(elementSon.getText().equals("LOG_IN")){
				//查询数据库
				String status = "1";
				Element username = root.element("username");
				Element password = root.element("password");
				
				String sql = "select * from user where username = " + "'" + username.getText()+"'";
				
				Connection conn = UserDB.getConn();
				Statement statement = UserDB.getStmt(conn);
				ResultSet rs = UserDB.executeQuery(statement, sql);
				String user_name =null;
				int id = 0;
				String pass_word = null;
				try {
					while(rs.next()){
						user_name = rs.getString("username");
						id = rs.getInt("id");
						pass_word = rs.getString("password");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(username.getText().equals(user_name) || password.getText().equals(pass_word)){
					status = "0";
					System.out.println("login success!");
				}
				System.out.println("username :" + user_name +"-------"+ "password"+"----"+"id :" +id);
				response re = new response();
				re.respinseTO(writer, "LOGIN_IN",status );
			}
			else if(elementSon.getText().equals("CALCULATE")){
				response re = new response();
				re.responseTo(writer,"CALCULATE","caihongcheng","10");
				System.out.println("------------");
			
			}
			else if(elementSon.getText().equals("FRESH")){
				String[][] parkData = {{"abc","人民路"},{"hhh","建东街"},{"nnn","大差市"}};
				response re = new response();
				re.responseTo(writer,"FRESH","0","3",parkData);
				System.out.println("++++++");
				
			}
		
		
	}
	
	/*public void estimate(File file){
		 SAXReader saxReader=new SAXReader();
		    
	     Document doc = null;
	     Element root = null;
	     Element elementSon = null;
	     String elementValue = null;
	     
		 try {
			doc = saxReader.read(file);
			root = doc.getRootElement();
			Iterator i = root.elementIterator();
			
			//elementSon = (Element)i.next();
			elementSon = root.element("command");
			
			elementValue = elementSon.getText();
			
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
			if(elementSon.getText().equals("REGIST")){
				//查询数据库
				String status = "1";
				Element username = root.element("username");
				Element password = root.element("password");
				String sql = "select * from user where username =" +"'"+username.getText()+"'";
System.out.println(sql);
				String update = "insert into user values("+"'"+username.getText()+"'"+","+"'"+password.getText()+"'"+","+3+")";
System.out.println(update);				
				Connection conn = UserDB.getConn();
				Statement statement = UserDB.getStmt(conn);
				PreparedStatement prestmt = null;
				ResultSet rs = UserDB.executeQuery(statement, sql);
				String user_name = null;
				try {
					while(rs.next()){
						user_name = rs.getString("username");	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(username.getText().equals(user_name)){
					System.out.println("user exsit!");
					return; 
				}else{
					System.out.println("ok!可以使用此用户名");
					prestmt = UserDB.getPreStmt(conn, update);
					try {
						prestmt.executeUpdate(update);
						System.out.println("insert ok!");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					status = "0";
				}
				UserDB.close(conn);
				UserDB.close(prestmt);
				UserDB.close(rs);
				response re = new response();
				//re.respinseTO(writer, "REGIST", status);
				
			}else if(elementSon.getText().equals("CONNECTSERVER")){
				//向服务器发送请求
				
			}
			else if(elementSon.getText().equals("LOG_IN")){
				//查询数据库
				
				Element username = root.element("username");
				Element password = root.element("password");
				
				String sql = "select * from user where username = " + "'" + username.getText()+"'";
System.out.println(sql);
				Connection conn = UserDB.getConn();
				Statement statement = UserDB.getStmt(conn);
				ResultSet rs = UserDB.executeQuery(statement, sql);
				String user_name =null;
				int id = 0;
				String pass_word = null;
				try {
					while(rs.next()){
						user_name = rs.getString("username");
						id = rs.getInt("id");
						pass_word = rs.getString("password");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(username.getText().equals(user_name) || password.getText().equals(pass_word)){
					System.out.println("login success!");
				}
				System.out.println("username :" + user_name + "--------------" +"id :" +id);
				UserDB.close(conn);
				UserDB.close(statement);
				UserDB.close(rs);
			}
			else if(elementSon.getText().equals("CALCULATE")){
				response re = new response();
				//re.responseTo(writer,"CALCULATE","caihongcheng","10");
				System.out.println("------------");
			
			}
			else if(elementSon.getText().equals("FRESH")){
				String[][] parkData = {{"abc","人民路"},{"hhh","建东街"},{"nnn","大差市"}};
				response re = new response();
				//re.responseTo(writer,"FRESH","0","3",parkData);
				System.out.println("++++++");
				
				
				
				
				
			}
		
		
	}
	*/
	/*public static void main(String[] args) {
		File file = new File("e:/Troy/test5.xml");
		XmlResolve x = new XmlResolve();
		x.estimate(file);
	}*/
}
