package com.springboot.board.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.board.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String password, String email) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		
		// 비밀번호 암호화 (관리자도 암호화되 열람할수 없음)
		user.setPassword(passwordEncoder.encode(password));
		
		this.userRepository.save(user);
		
		return user;
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if(siteUser.isPresent()) {	// siteUser 에 데이터가 있으면
			return siteUser.get();
		}else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
}
