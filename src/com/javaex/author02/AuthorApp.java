package com.javaex.author02;

import java.util.List;

public class AuthorApp {
	
	public static void main(String[] args) {
		
		AuthorDao authorDao = new AuthorDao();
		List<AuthorVo> authorList;
		//등록
		AuthorVo authorVo1 = new AuthorVo("이문열", "경북영양");
		authorDao.authorInsert(authorVo1); //작가 테이블에 데이터 저장
		
		AuthorVo authorVo2 = new AuthorVo("박경리", "경상남도 통영");
		authorDao.authorInsert(authorVo2); //작가 테이블에 데이터 저장
		
		//리스트
		authorList = authorDao.getAuthorList();
		
		//리스트 전체 출력
		System.out.println("============= 작가리스트 =============");
		for(int i = 0; i<authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId()+","+vo.getAuthorName()+","+vo.getAuthorDesc());
		}
		
		//작가 삭제
		authorDao.authorDelete(2);
		
		//작가 리스트 출력
		authorList = authorDao.getAuthorList();
		
		System.out.println("============= 작가리스트 =============");
		for(int i = 0; i<authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId()+","+vo.getAuthorName()+","+vo.getAuthorDesc());
		}
		
		//작가 수정
		AuthorVo authorVo4 = new AuthorVo(2,  "김경리", "제주도" );
		authorDao.authorUpdate(authorVo4);
		
		//작가 리스트 출력
		authorList = authorDao.getAuthorList();
		System.out.println("============= 작가리스트 =============");
		for(int i = 0; i<authorList.size(); i++) {
			AuthorVo vo = authorList.get(i);
			System.out.println(vo.getAuthorId()+","+vo.getAuthorName()+","+vo.getAuthorDesc());
		}
	}
}
