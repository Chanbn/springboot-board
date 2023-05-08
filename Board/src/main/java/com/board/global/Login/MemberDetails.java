package com.board.global.Login;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.board.domain.member.Member;

import lombok.Data;

@Data
public class MemberDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Member member;
	
	public MemberDetails(Member member) {
		// TODO Auto-generated constructor stub
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return member.getRoles().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
//		Collection<GrantedAuthority> collect = new ArrayList<>();
//		collect.add(new SimpleGrantedAuthority(member.getRole().toString()));
//		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
