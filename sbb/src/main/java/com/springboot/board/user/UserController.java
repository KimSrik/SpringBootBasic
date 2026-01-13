package com.springboot.board.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			//rejectValue는 필드 하나에서의 오류 처리 시 (지역적)
			//오류가 난 필드, 오류의 종류, 나타낼 메시지
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "signup_Form";
		}
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			//reject는 여러 필드에서 오류 처리시(글로벌 에러)
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/login")		// security가 제공하는 화면말고 사용자가 만든 페이지로 이동
	public String login() {
		return "login_form";
	}

}
