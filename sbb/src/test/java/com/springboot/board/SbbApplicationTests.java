package com.springboot.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.board.answer.Answer;
import com.springboot.board.answer.AnswerRepository;
import com.springboot.board.question.Question;
import com.springboot.board.question.QuestionRepository;
import com.springboot.board.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Transactional
	@Test
	void contextLoads() {
		
		// insert sql 쿼리문
		/*
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
		*/
		
		/*
		// select 쿼리문
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		System.out.println(q.getContent());
		System.out.println(q.getCreateDate());
		System.out.println(q.getId());
		assertEquals(1, q.getId());
		// assertEquals는 매개변수 둘의 값이 같으면 JUnit에 초록불, 아닐시에는 빨간불
		*/
		
		/*
		List<Question> qList = this.questionRepository.findBySubjectLike("%sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
		System.out.println(q.getContent());
		System.out.println(q.getId());
		*/
		
		/*
		// update 쿼리문
		// select문을 먼저 수행한 후 insert(save)를 하게 되면 update query문이 실행된다.
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목2");
		this.questionRepository.save(q);
		*/
		
		/*
		// delete 쿼리문
		assertEquals(2, this.questionRepository.count());
		
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
		*/
		
		/*
		// 관계맺기를 한 데이터에 대한 insert 쿼리문
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
		*/
		
		/*
		// 두 테이블 관계 지었을 때 쿼리문
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
		*/
		
		/*
		// test data 300개 추가
		for(int i=1; i<=300; i++) {
			String subject = String.format("테스트 데이터 입니다:[%03d]", i);
			String content = "내용없음";
			this.questionService.create(subject, content);
		}
		*/
		
	}

}
