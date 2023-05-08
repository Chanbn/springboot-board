package com.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberProfileDto;
import com.board.domain.member.dto.MemberSignUpDto;
import com.board.domain.member.dto.MemberUpdateDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.member.service.MemberService;
import com.board.domain.member.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class memberServiceImplTest {

	@InjectMocks
	private MemberServiceImpl memberService;
	
	@Mock
	private MemberRepository memberRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private MockMvc mockMvc;
	
	Member member;

	@BeforeEach
    public void setup() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        memberService = new MemberServiceImpl(memberRepository,bCryptPasswordEncoder); 
        
		  List<String> roles = new ArrayList<>();
		    roles.add("USER");
		     member = Member.builder()
		            .username("testId123")
		            .name("이름")
		            .email("test123@email.com")
		            .password(bCryptPasswordEncoder.encode("!qlalfqjsgh12"))
		            .nickname("nick")
		            .roles(roles)
		            .build();

    }
	
	@Nested
	class 회원가입{
		
		@Test
		public void 성공() {


			    when(memberRepository.save(any(Member.class))).thenReturn(member);

			    MemberSignUpDto memberSignUpDto = new MemberSignUpDto(member);
			    Member savedMember = memberService.signup(memberSignUpDto);
			    assertThat(savedMember).isEqualTo(member);
		}
		
		@Test
		@DisplayName("실패 - 아이디중복")
		public void 아이디() {

			    when(memberRepository.save(any(Member.class))).thenThrow(new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME));
			    MemberSignUpDto memberSignUpDto = new MemberSignUpDto(member);
			    Throwable exception = catchThrowable(()->memberService.signup(memberSignUpDto));
			    assertThat(exception).isInstanceOf(MemberException.class);	
			    assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.ALREADY_EXIST_USERNAME);
		}
		
		@Test
		@DisplayName("실패 - 이메일중복")
		public void 이메일() {

			    when(memberRepository.save(any(Member.class))).thenThrow(new MemberException(MemberExceptionType.ALREADY_EXIST_EMAIL));
			    MemberSignUpDto memberSignUpDto = new MemberSignUpDto(member);
			    Throwable exception = catchThrowable(()->memberService.signup(memberSignUpDto));
			    assertThat(exception).isInstanceOf(MemberException.class);	
			    assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.ALREADY_EXIST_EMAIL);
		}
		
		@Test
		@DisplayName("실패 - 닉네임중복")
		public void 닉네임() {

			    when(memberRepository.save(any(Member.class))).thenThrow(new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME));
			    MemberSignUpDto memberSignUpDto = new MemberSignUpDto(member);
			    Throwable exception = catchThrowable(()->memberService.signup(memberSignUpDto));
			    assertThat(exception).isInstanceOf(MemberException.class);	
			    assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.ALREADY_EXIST_NICKNAME);
		}
	}
	
	@Nested
	class 로그인유저조회{
		@Test
		@DisplayName("성공")
		public void 성공() {
			when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));
			MemberInfoDto result = memberService.currentMember(member.getUsername());
			assertEquals(result.getUsername(), member.getUsername());
			assertEquals(result.getEmail(), member.getEmail());
			assertEquals(result.getDelete_yn(), member.getDelete_yn());
			assertEquals(result.getId(), member.getId());
			assertEquals(result.getNickname(), member.getNickname());
			assertEquals(result.getRoles(), member.getRoles());
		}
		
		@Test
		@DisplayName("실패")
		public void 실패() {
			when(memberRepository.findByUsername(member.getUsername())).thenThrow(new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
			Throwable exception = catchThrowable(()->memberService.currentMember(member.getUsername()));
			assertThat(exception).isInstanceOf(MemberException.class);
			assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.NOT_FOUND_MEMBER);
		}
	}
	
	@Nested
	class 프로필조회{
		@Test
		@DisplayName("성공")
		public void 성공() {
			when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));
			MemberProfileDto result = memberService.getProfile(member.getUsername());
			assertEquals(result.getUsername(), member.getUsername());
			assertEquals(result.getEmail(), member.getEmail());
			assertEquals(result.getDelete_yn(), member.getDelete_yn());
			assertEquals(result.getId(), member.getId());
			assertEquals(result.getNickname(), member.getNickname());
			assertEquals(result.getName(), member.getName());
		}
		@Test
		@DisplayName("실패")
		public void 실패() {
			when(memberRepository.findByUsername(member.getUsername())).thenThrow(new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
			Throwable exception = catchThrowable(()->memberService.getProfile(member.getUsername()));
			assertThat(exception).isInstanceOf(MemberException.class);
			assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.NOT_FOUND_MEMBER);
		}
	}
	
	@Nested
	class 프로필업데이트{

		
		@Test
		@DisplayName("성공")
		public void 성공() {
			MemberUpdateDto dto = new MemberUpdateDto();
			dto.setNickname("newTestid");
			dto.setPassword("newPassword");
			dto.setUsername(member.getUsername());
			when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(member));
			String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
			member.updateMember(dto.getNickname(), encodePassword);
			when(memberRepository.save(member)).thenReturn(member);
			Member result = memberService.updateProfile(dto);
			assertEquals(result.getNickname(),"newTestid");
		}
		
		@Test
		@DisplayName("실패")
		public void 실패() {
			MemberUpdateDto dto = new MemberUpdateDto();
			dto.setUsername(member.getUsername());
			dto.setNickname("newTestid");
			dto.setPassword("newPassword");
			when(memberRepository.findByUsername(member.getUsername())).thenThrow(new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
			Throwable exception = catchThrowable(()->memberService.updateProfile(dto));
			assertThat(exception).isInstanceOf(MemberException.class);
			assertThat(((MemberException)exception).getExceptionType()).isEqualTo(MemberExceptionType.NOT_FOUND_MEMBER);			
		}
	}
}
