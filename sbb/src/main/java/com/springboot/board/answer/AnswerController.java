package com.springboot.board.answer;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.board.question.Question;
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
	
	
	

}
