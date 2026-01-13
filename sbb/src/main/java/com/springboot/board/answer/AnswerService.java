package com.springboot.board.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.springboot.board.question.Question;
import com.springboot.board.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);		// insert
	}

}
