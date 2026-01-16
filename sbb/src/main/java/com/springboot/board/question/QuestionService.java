package com.springboot.board.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.board.DataNotFoundException;
import com.springboot.board.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	// 글 목록보기
	/*
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	*/
	
	public Page<Question> getList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
			
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
	}
	
	// 글 상세보기
	public Question getQuestion(Integer id) {
		// 불러온 값에 대한 유무 체크를 위한 Optional
		// id에 대한 where 조건문이 필요하므로 findById
		Optional<Question> question = this.questionRepository.findById(id);
		
		if(question.isPresent()) {	// question의 값의 유무를 체크
			return question.get();	// question의 데이터를 가져와서 반환.
		}else {
			throw new DataNotFoundException("객체를 찾을수가 없습니다.");
		}
	}
	
	// 질문 등록하기
	public void create(String subject, String content, SiteUser user) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}
	
	// 질문 글 수정하기
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	// 질문 글 삭제하기
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	// 질문 글 추천하기
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	

}
