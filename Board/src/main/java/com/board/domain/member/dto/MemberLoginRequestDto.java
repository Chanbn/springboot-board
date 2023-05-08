package com.board.domain.member.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {

	private String username;
	private String password;
}
