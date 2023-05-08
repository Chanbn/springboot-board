package com.board.domain.member.dto;

import com.board.domain.member.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberUpdateDto {

	private String username;
	private String nickname;
	private String password;
	
	
	public Member toEntity() {
		Member member = Member.builder()
				.nickname(nickname)
				.password(password)
				.build();

		return member;
	}
}
