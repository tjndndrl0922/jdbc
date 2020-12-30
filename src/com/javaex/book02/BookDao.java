package com.javaex.book02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

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

	// 메소드일반

	// DB 접속
	private void getConnection() {

		try {
			// JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
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

	// 책 업데이트
	public int bookUpdate(BookVo bookVo) {

		// DB접속
		getConnection();

		int count = 0;

		try {
			// SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update book ";
			query += " set title = ? ";
			query += " 	   pubs  = ? ";
			query += "     pub_date =? ";
			query += " where book_id = ? ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getBookId());

			count = pstmt.executeUpdate();
			// 결과 처리
			System.out.println("(dao)" + count + "건이 수정");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;

	}

	// 책 삭제
	public int bookDelete(int bookId) {

		// DB접속
		getConnection();

		int count = 0;

		try {
			// SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";

			// 테스트
			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);

			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("(dao)" + count + "건 삭제");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	}

	// 책 +작가 리스트 가져오기
	public List<BookVo> getBookList() {

		// DB접속
		getConnection();

		List<BookVo> bookList = new ArrayList<BookVo>();

		try {
			// SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * SELECT b.book_id, b.title, b.pubs, b.pub_date, b.author_id, a.author_name,
			 * a.author_desc FROM book b, author a where b.author_id = a.author_id order by
			 * b.book_id asc;
			 */
			query += " SELECT book.book_id, ";
			query += " 		 book.title, ";
			query += " 		 book.pubs, ";
			query += " 		 to_char(book.pub_date, 'YYYY-MM-DD') pub_date, ";
			query += " 		 author.author_id, ";
			query += " 		 author.author_name, ";
			query += " 		 author.author_desc ";
			query += " FROM book , author  ";
			query += " where book.author_id = author.author_id ";
			query += " order by book.book_id asc ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			// 결과처리
			// rs에 있는 데이터를 List<BookVo>로 구성
			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String bookTitle = rs.getString("title");
				String bookPubs = rs.getString("pubs");
				String bookDate = rs.getNString("pub_date");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				BookVo vo = new BookVo(bookId, bookTitle, bookPubs, bookDate, authorId, authorName, authorDesc);
				bookList.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원정리
		close();

		return bookList;
	}

	// 책 등록
	public int bookInsert(BookVo bookVo) {

		// DB접속
		getConnection();

		int count = 0;

		try {
			// SQL문 준비 / 바인딩 / 실행
			String query = "";
			/*
			 * insert into book values(seq_book_id.nextval, '우리들의 일그러진 영웅', '다림',
			 * '1998-02-22', '1');
			 */

			query += " insert into book ";
			query += " values(seq_book_id.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("(dao)" + count + "건  book table에 저장");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원정리
		close();

		return count;
	}

}
