package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSelectAll {
	
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
		    String query = "";
		    /*
		     SELECT  b.book_id,
			         b.title,
			         b.pubs,
			         b.pub_date,
			         b.author_id,
			         a.author_name,
			         a.author_desc
			 FROM book b, author a
			 where b.author_id = a.author_id
			 order by b.book_id asc;
		     */
			 query +=" SELECT  b.book_id, ";
			 query +=" 		   b.title, ";
			 query +=" 		   b.pubs, ";
			 query +=" 		   b.pub_date, ";
			 query +=" 		   a.author_id, ";
			 query +=" 		   a.author_name, ";
			 query +=" 		   a.author_desc ";
			 query += " FROM book b, author a ";
			 query += " where b.author_id = a.author_id ";
			 query += " order by b.book_id asc ";
		    
			 //테스트
			 System.out.println(query);
			 pstmt = conn.prepareStatement(query);
			 rs = pstmt.executeQuery();
					 
		    // 4.결과처리
			 while(rs.next()) {
				 int bookId = rs.getInt("book_id");
				 String title = rs.getNString("title");
				 String pubs = rs.getNString("pubs");
				 String pub_date = rs.getNString("pub_date");
				 int authorId = rs.getInt("author_id");
				 String authorName = rs.getNString("author_name");
				 String authorDesc = rs.getNString("author_desc");
				 
				 System.out.println(bookId+" , "+title+" , "+pubs+" , "+pub_date+" , "+authorId+" , "+authorName+" , "+authorDesc);
				 
				 
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
