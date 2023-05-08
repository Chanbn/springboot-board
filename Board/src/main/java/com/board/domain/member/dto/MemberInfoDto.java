package com.board.domain.member.dto;

import java.util.List;

import com.board.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoDto {
	private String username;
	private String nickname;
	private String email;
	private Long id;
	private List<String> roles;
	private String delete_yn;
	@Builder
	public MemberInfoDto(Member member) {
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.email = member.getEmail();
		this.id = member.getId();
		this.roles = member.getRoles();
		this.delete_yn = member.getDelete_yn();
	}
	
	@Builder
	public void setDto(MemberSessionDto member) {
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.email = member.getEmail();
		this.id = member.getId();
	}
	
	public Member toEntity() {
		return Member.builder()
				.id(id)
				.username(username)
				.nickname(nickname)
				.email(email)
				.build();
	}
}
