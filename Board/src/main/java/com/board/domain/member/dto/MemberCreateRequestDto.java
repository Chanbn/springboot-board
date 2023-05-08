package com.board.domain.member.dto;

import javax.validation.constraints.NotNull;

import com.board.domain.member.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateRequestDto {

	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private Role role;
}
