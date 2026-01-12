package com.springboot.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration			// 스프링 환경 설정 파일을 의미하는 어노테이션
@EnableWebSecurity		// 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션		
public class SecurityConfig {

	@Bean		// 메서드가 리턴하는 결과값을 스프링 컨테이너가 관리하도록 등록
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 보안 규칙 정의
		http		
			.authorizeHttpRequests(  (authorizeHttpRequests) -> authorizeHttpRequests
					// HTTP요청이 들어오면 인증(허가) 여부 검사 
					.requestMatchers("/**").permitAll())
			//대상 지정						// 무조건 허락
			// 로그인을 하지 않은 사용자도 모든 페이지를 볼 수 있도록 허락
			;
		return http.build();	// Bean으로 객체 생성 완료.		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
