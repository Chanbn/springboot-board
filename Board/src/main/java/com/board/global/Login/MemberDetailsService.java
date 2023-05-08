package com.board.global.Login;




import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberSessionDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailsService implements UserDetailsService{

	private final MemberRepository memberRepository;
	private final HttpSession session;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("loadUserByUsername :: ");
		Member member = memberRepository.findByUsername(username).orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
		MemberSessionDto memberSessionDto = new MemberSessionDto(member);
		session.setAttribute("user", memberSessionDto);
		return new MemberDetails(member);
	}


	public boolean checkAuthority(String Authority) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasRole = authentication.getAuthorities().stream().anyMatch(auth->auth.getAuthority().equals(Authority));
		return hasRole;
	}
}
