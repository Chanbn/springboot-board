package com.board.domain.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.comment.dto.CommentInfoDto;
import com.board.domain.comment.service.CommentService;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberProfileCheckDto;
import com.board.domain.member.dto.MemberProfileDto;
import com.board.domain.member.dto.MemberSignUpDto;
import com.board.domain.member.dto.MemberUpdateDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.member.service.MemberService;
import com.board.domain.post.dto.LastPageDto;
import com.board.domain.post.dto.PostInfoDto;
import com.board.domain.post.service.PostService;
import com.board.global.Login.MemberDetails;
import com.board.global.Login.MemberDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	private final MemberService memberService;
	private final PostService postService;
	private final CommentService commentService;
	private final MemberDetailsService memberDetailsService;

	@GetMapping(value = "/login")
	public void login() {
		log.info("get login ---------");
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public void signup() {
		
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> signup2(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception {
		log.info("member"+memberSignUpDto.getUsername());
		try {
			memberService.signup(memberSignUpDto);
			return ResponseEntity.ok("redirect:/member/login");
		} catch (MemberException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getExceptionType().getErrorMessage());
		}	
	} 
	
	@ResponseBody
	@RequestMapping(value = "/chk", method = RequestMethod.POST)
	public int NickNameCheck(@RequestParam("word") String word, @RequestParam("type") int type) throws Exception{ 
		log.info("type :'"+type+"  word:'"+word+"'");
		if(word=="") {
			return -1;
		}
		int chk = memberService.existCheck(word, type);
		
		return chk;
	}
	
	@ResponseBody
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public ResponseEntity<MemberInfoDto> currentUser(@AuthenticationPrincipal MemberDetails memberDetails){
		MemberInfoDto member = memberService.currentMember(memberDetails.getUsername());
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/profile")
	public void getProfile(@AuthenticationPrincipal UserDetails userDetails,Model model) {
		MemberProfileDto member = memberService.getProfile(userDetails.getUsername());
		model.addAttribute("member",member);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<String> updateProfile(@AuthenticationPrincipal MemberDetails memberDetails, MemberUpdateDto member) {
		log.info("MemberController updateProfile method--------------------------------------------------");
		if(!memberDetails.getUsername().equals(member.getUsername())) {
			return ResponseEntity.badRequest().body("잘못된 접근입니다.");
		}

		memberService.updateProfile(member);
		memberDetails.getMember().setPassword(member.getPassword());
		return ResponseEntity.ok("변경되었습니다.");
	}
	
	@ResponseBody
	@GetMapping("/profileCheck")
	public ResponseEntity<Map<String, String>> profileCheck(MemberProfileCheckDto memberProfileCheckDto){
		log.info("MemeberController profileCheck method--------------------------------------------------");
		int check = memberService.existCheck(memberProfileCheckDto.getWord(), memberProfileCheckDto.getType());
		int type = memberProfileCheckDto.getType();
		Map<String,String> map = new HashMap<String, String>();
		String result ="";
		String wordType="";
		if(check==1) {
			result = "이미 사용중인 ";
		}else if(check==0) {
			result = "사용가능한 ";
		}
		if(type==2) {
			wordType="닉네임입니다.";
		}
		map.put("result", result);
		map.put("type", wordType);
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping("/postinfo")
	public void getpostInfo(Model model, @PageableDefault(page = 0, size=10,sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable, String username) {
		Page<PostInfoDto> pageList =postService.getPostList(username, pageable);
		LastPageDto lastPage = new LastPageDto();
		lastPage.recentPageSet(pageable.getPageNumber());

		int startpage = 1;
		if(pageList.getNumber()!=1) {
			startpage = pageList.getNumber()-pageList.getNumber()%10+1;
		}
		System.out.println("currentPAge : "+pageList.getNumber());
		int endpage = pageList.getNumber()+(10-pageList.getNumber()%10);
		if(endpage>pageList.getTotalPages()) {
			endpage = pageList.getTotalPages();
		}
		boolean nextPage = endpage%10==0?true:false;
		
		model.addAttribute("postList",pageList);
		model.addAttribute("prev",pageList.hasPrevious());
		model.addAttribute("next",nextPage);
		model.addAttribute("page",pageList.getNumber());
		model.addAttribute("startpage",startpage);
		model.addAttribute("endpage",endpage);
	}
	
	@GetMapping("/commentInfo")
	public void getcommentInfo(Model model, @PageableDefault(page = 0, size=10,sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable, String username) {
		Page<CommentInfoDto> pageList =commentService.getCommentList(username, pageable);
		LastPageDto lastPage = new LastPageDto();
		lastPage.recentPageSet(pageable.getPageNumber());

		int startpage = 1;
		if(pageList.getNumber()!=1) {
			startpage = pageList.getNumber()-pageList.getNumber()%10+1;
		}
		System.out.println("currentPAge : "+pageList.getNumber());
		int endpage = pageList.getNumber()+(10-pageList.getNumber()%10);
		if(endpage>pageList.getTotalPages()) {
			endpage = pageList.getTotalPages();
		}
		boolean nextPage = endpage%10==0?true:false;
		
		model.addAttribute("commentList",pageList);
		model.addAttribute("prev",pageList.hasPrevious());
		model.addAttribute("next",nextPage);
		model.addAttribute("page",pageList.getNumber());
		model.addAttribute("startpage",startpage);
		model.addAttribute("endpage",endpage);
	}
	
	@GetMapping("/info")
	public void getInfo(String username,Model model) {
	log.info("username"+username);
	model.addAttribute("username", username);
	}
	
	@GetMapping("/list")
	public String getMemberList(Model model) {
		boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");
		if(!hasAdminRole) {
			return "/home";
		}
		List<MemberProfileDto> list =memberService.getList();
		model.addAttribute("list", list);
		return "/member/list";
	}
	@ResponseBody
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteMember(@RequestParam("username") String username) {
		memberDetailsService.checkAuthority("ROLE_ADMIN");
		memberService.deleteMember(username);
		return ResponseEntity.ok("회원탈퇴 시켰습니다.");
	}
}
