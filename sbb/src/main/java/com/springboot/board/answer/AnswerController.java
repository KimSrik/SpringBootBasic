package com.springboot.board.answer;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.board.question.Question;
import com.springboot.board.question.QuestionForm;
import com.springboot.board.question.QuestionService;
import com.springboot.board.user.SiteUser;
import com.springboot.board.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")	//prefix
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final UserService userService;
	
	// 답변 등록
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());

		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			// 에러가 났을 시 다시 재접속할 때 질문에 대한 정보가 필요하기 때문에 add 함.
			return "question_detail";
		}
		
		this.answerService.create(question, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
	
	// 답변 상세보기 -> 답변 수정 버튼 클릭
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm,
		@PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = this.answerService.getAnswer(id);
		
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		answerForm.setContent(answer.getContent());
		return "answer_form";
	}
	
	// 답변 수정하기
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		
		Answer answer = this.answerService.getAnswer(id);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		Question question = answer.getQuestion();
		Integer questionId = question.getId();
		
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s", questionId);	
		// 문자열로 변환하기 위해 %d 가 아닌 %s를 씀
	}
	
	// 답변 삭제하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		Question question = answer.getQuestion();
		Integer questionId = question.getId();
		
		this.answerService.delete(answer);
		
		return String.format("redirect:/question/detail/%s", questionId);
		
	}
	
	// 답변 추천 등록하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	
	

}
