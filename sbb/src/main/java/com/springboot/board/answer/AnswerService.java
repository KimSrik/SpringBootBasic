package com.springboot.board.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.board.DataNotFoundException;
import com.springboot.board.question.Question;
import com.springboot.board.question.QuestionForm;
import com.springboot.board.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 답변 등록
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);		// insert
	}
	
	// 답변 불러오기
	public Answer getAnswer(Integer id) {
		// 불러온 값에 대한 유무 체크를 위한 Optional
		// id에 대한 where 조건문이 필요하므로 findById
		Optional<Answer> answer = this.answerRepository.findById(id);
		
		if(answer.isPresent()) {	// answer의 값의 유무를 체크
			return answer.get();	// answer의 데이터를 가져와서 반환.
		}else {
			throw new DataNotFoundException("객체를 찾을수가 없습니다.");
		}
	}
	
	// 답변 수정
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	// 답변 삭제하기
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	
	// 질문 추천하기
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}

}
