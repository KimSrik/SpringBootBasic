package com.springboot.board.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// insert 글 삽입 -> save

	// select 글 검색 -> [테이블명] findBy[필드명].([자료형] [필드명])
	// findBy뒤에 캐멀스타일이라서 대문자가 오는데 헷갈릴수 있으니 주의!
	
	// 제목을 가지고 데이터 검색 
	// select * from question where subject = ?;		
	Question findBySubject(String subject);
	
	// 제목과 내용을 가지고 데이터 검색
	// select * from question where subject = ? and content = ?;
	Question findBySubjectAndContent(String subject, String content);
	
	// 제목의 일부가 일치하는 경우의 데이터 검색 (LIKE)
	// 검색 결과가 여러개 일 경우는 List 컬렉션 활용
	List<Question> findBySubjectLike(String subject);	// %sbb sbb% %sbb%
	
	// 페이징
	Page<Question> findAll(Pageable pageable);
	
	
	
}
