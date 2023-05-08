package com.board.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberProfileDto;
import com.board.domain.member.dto.MemberSignUpDto;
import com.board.domain.member.dto.MemberUpdateDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
//	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Member signup(MemberSignUpDto memberSignUpDto) {
		// TODO Auto-generated method stub
		Member member = memberSignUpDto.toEntity();
		member.addRoles_USER();
		member.encodePassword(bCryptPasswordEncoder);

		return memberRepository.save(member);
	} 

	@Override
	public int existCheck(String word,int type) {
		// TODO Auto-generated method stub
		int check = 0;
		switch (type) {
		case 1:
			check = memberRepository.existsByUsername(word)==true?1:0;	
			throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
		case 2:
			check = memberRepository.existsByNickname(word)==true?1:0;
			throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);
		case 3:
			check = memberRepository.existsByEmail(word)==true?1:0;
			throw new MemberException(MemberExceptionType.ALREADY_EXIST_EMAIL);
		default:
			break;
		}
		return check;
	}

	@Override
	public MemberInfoDto currentMember(String username) {
		// TODO Auto-generated method stub
		MemberInfoDto member = new MemberInfoDto(memberRepository.findByUsername(username).orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));
		return member;
	}

	@Override
	public MemberProfileDto getProfile(String username) {
		// TODO Auto-generated method stub
		MemberProfileDto member = new MemberProfileDto(memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));
		return member;
	}

	@Override
	public Member updateProfile(MemberUpdateDto memberUpdateDto) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(memberUpdateDto.getUsername()).orElseThrow(()->new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
		String encodePassword = bCryptPasswordEncoder.encode(memberUpdateDto.getPassword());
		member.updateMember(memberUpdateDto.getNickname(), encodePassword);
		member = memberRepository.save(member);
		return member;

	}

	@Override
	public List<MemberProfileDto> getList() {
		// TODO Auto-generated method stub
		List<MemberProfileDto> list = memberRepository.findAll().stream()
											   .map(member -> new MemberProfileDto(member))
											   .filter(member->("N").equals(member.getDelete_yn()))
											   .collect(Collectors.toList());
		return list;
	}

	@Override
	public void deleteMember(String username) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		member.setDeleteYn("Y");
		memberRepository.save(member);
	}



}
