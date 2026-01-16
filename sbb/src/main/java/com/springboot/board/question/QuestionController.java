package com.springboot.board.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
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

import com.springboot.board.answer.AnswerForm;
import com.springboot.board.user.SiteUser;
import com.springboot.board.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")		// prefix uri설정
public class QuestionController {
	
	private final QuestionService questionService;
	
	private final UserService userService;
	
	// 글 목록보기
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	// @RequestParam("[name]") 은 html에서 form내부의 name으로 지정한 값을 가져올 수 있다.
	// repository를 controller에서 직접 접근하면 안되므로 서비스에 요청해서 받아야 한다.
	
	// 글 상세보기
	@GetMapping("/detail/{id}")	// PathVariable 변수명과 같아야 한다.
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {	// 가변 경로 id, 담기 위한 id
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	// uri의 id는 가변 값이기 때문에 { } 중괄호로 묶어서 표기해준다.
	// PathVariable 받는 값도 가변 값이기 때문에 
	// @PathVariable로 받고 박싱된 타입으로 받아준다
	
	
	// 아래 두 메소드는 Overloding.
	// 맵핑 접근 방식에 따라 실행되는 메소드가 다름
	// 글 등록 버튼 -> 등록 폼
	@PreAuthorize("isAuthenticated()")	// 시큐리티에 권한을 넘겨줌
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	// 글 등록 버튼 -> insert SQL -> 목록 이동
	@PreAuthorize("isAuthenticated()")	// 시큐리티에 권한을 넘겨줌(로그인 정보가 있는지)
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}	
	// @Valid는 유효성 검사를 하기 위한 어노테이션이다.
	// BindingResult는 유효성 검사한 결과를 가지고 있다.
	
	// 글 상세보기 -> 글 수정 버튼 클릭
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm,
		@PathVariable("id") Integer id, Principal principal) {
		
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	// 질문 글 수정하기
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);	
		// 문자열로 변환하기 위해 %d 가 아닌 %s를 씀
	}
	
	// 질문 글 삭제하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		
		this.questionService.delete(question);
		
		return "redirect:/";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
}
