package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSelect {

	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		    // 3. SQL문 준비 / 바인딩 / 실행
		    String query ="";
		    
		    /*
		     SELECT  b.book_id,
        				b.title,
        				b.pubs,
        				b.pub_date,
        				a.author_name
			 FROM book b, author a
			 where b.author_id = a.author_id
			 order by b.book_id asc;
		     */
			
		    query += " SELECT  book_id, ";
		    query += " 		   title, ";
		    query += "		   pubs, ";
		    query += " 		   pub_date, ";
		    query += " 		   author_name ";
		    query += " FROM book b, author a";
		    query += " where b.author_id = a.author_id ";
		    query +=" order by b.book_id asc ";		
		    //테스트
		    System.out.println(query);
		    
		    pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
		    // 4.결과처리
		    while(rs.next()) {
		    	int bookId = rs.getInt("book_id");
		    	String title = rs.getString("title");
		    	String pubs = rs.getString("pubs");
		    	String pubDate = rs.getString("pub_date");
		    	String authorName = rs.getString("author_name");
		    	
		    	System.out.println(bookId+","+title+","+pubs+","+pubDate+","+authorName);
		    	
		    }
			} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
		    System.out.println("error:" + e);
			} finally {
		   
		    // 5. 자원정리
		    try {
		        if (rs != null) {
		            rs.close();
		        }                
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }

		}

	}

}
