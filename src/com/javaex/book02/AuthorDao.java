package com.javaex.book02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.SetOfIntegerSyntax;

public class AuthorDao {

	// 필드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	// 생성자

	// 메소드 g.s

	// 메소드 일반

	// DB접속
	private void getConnection() {
		// DB접속 기능

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			System.out.println("접속성공");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 -" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 자원 정리
	private void close() {
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

	// 작가 업데이트
	public int authorUpdate(AuthorVo authorVo) {

		// DB접속
		getConnection();

		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * update author set author_name = '서웅기', author_desc = '안산기 단원구' where
			 * author_id = 1;
			 */

			query += " update author ";
			query += " set author_name = ?, ";
			query += " author_desc = ? ";
			query += " where author_id = ? ";

			// 테스트
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());
			pstmt.setInt(3, authorVo.getAuthorId());

			count = pstmt.executeUpdate();
			// 4.결과처리

			System.out.println("(dao" + count + "건이 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원정리
		close();

		return count;

	}

	// 작가 삭제하기
	public int authorDelete(int authorId) {

		// DB접속
		getConnection();

		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * delete from author where author_id = ?;
			 */
			query += " delete from author ";
			query += " where author_id = ? ";

			// 테스트
			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("(dao)" + count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

	// 작가 리스트 가져오기
	public List<AuthorVo> getAuthorList() {

		// DB접속
		getConnection();

		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * SELECT author_id, author_name, author_desc FROM author;
			 */
			query += "  SELECT author_id, ";
			query += " 		   author_name, ";
			query += " 		   author_desc ";
			query += " FROM author ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			// rs에 있는 데이터를 List<AuthorVo> 로 구성해야 한다.
			while (rs.next()) {
				int authorId = rs.getInt("author_id");
				String authorName = rs.getNString("author_name");
				String authorDesc = rs.getNString("author_desc");

				AuthorVo vo = new AuthorVo(authorId, authorName, authorDesc);
				authorList.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return authorList;
	}

	// 작가 저장 기능
	public int authorInsert(AuthorVo authorVo) {

		// DB접속
		getConnection();

		int count = 0;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * insert into author values(seq_author_id.nextval, '이문열', '경북 양양');
			 */

			query += " insert into author ";
			query += " values(seq_author_id.nextval, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());

			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println("(dao) " + count + "건이 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

}
