package com.board.domain.member.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.board.domain.member.Member;
import com.board.domain.member.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MemberSignUpDto {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-z0-9-_]{4,10}$",message = "아이디는 한글,특수문자를 제외한 4~10자리여야 합니다.")
	private String username;
	
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	private String nickname;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,4}$",message = "이름은 특수문자, 영어를 제외한 2~4자리여야 합니다.")
	private String name;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")    
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")   
	private String password;
	
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")    
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
    
    public MemberSignUpDto(String username, String nickname, String name, String password, String email){
    	this.username = username;
    	this.nickname = nickname;
    	this.name = name;
    	this.password = password;
    	this.email = email;
    }
    
    public MemberSignUpDto(Member member) {
    	this.username = member.getUsername();
    	this.nickname = member.getNickname();
    	this.name = member.getName();
    	this.password = member.getPassword();
    	this.email = member.getEmail();    	
    }
    
    public Member toEntity() {
    	return Member.builder().username(username).nickname(nickname).name(name).password(password).email(email).build();
    }
}
