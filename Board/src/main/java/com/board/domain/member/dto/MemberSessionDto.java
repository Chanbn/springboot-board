package com.board.domain.member.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.board.domain.member.Member;
import com.board.domain.member.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSessionDto {

	private Long id;
	private String username;
	private String name;
	private String email;
	private String nickname;
	private String delete_yn;
	private List<String> roles;
	
	@Builder
	public MemberSessionDto(Member member)
	{
		this.id = member.getId();
		this.username = member.getUsername();
		this.name = member.getName();
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.roles = member.getRoles();
		this.delete_yn = member.getDelete_yn();
	}
}
