package com.board.domain.post.controller;


import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.board.domain.manager.dto.manageListDto;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberSessionDto;
import com.board.domain.post.Post;
import com.board.domain.post.dto.LastPageDto;
import com.board.domain.post.dto.PostInfoDto;
import com.board.domain.post.dto.PostSaveDto;
import com.board.domain.post.service.PostService;
import com.board.file.dto.FileDto;
import com.board.file.service.FileService;
import com.board.global.Login.MemberDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
public class PostController {
	
	private final PostService postService;
	private final FileService fileService;
	private final MemberDetailsService memberDetailsService;
	LastPageDto lastPage = new LastPageDto();
	
	
	@GetMapping(value = { "/{category}/list", "/myList" })
	public String getList(@PathVariable String category,Model model, @PageableDefault(page = 0, size=10,sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable,@RequestParam(value = "type", defaultValue = "all") String type,@RequestParam(value = "", defaultValue = "") String word) {
		log.info("PostController - getList method--------------------------------------------------");
		Page<PostInfoDto> pageList =postService.SearchPost(type, word, pageable,category);
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
	    if (category != null) {
	        model.addAttribute("category", category);
	    }
	    
	    List<PostInfoDto> noticeList = postService.getNoticeList();
	    
	    model.addAttribute("noticeList",noticeList);
		model.addAttribute("boardList",pageList);
		model.addAttribute("prev",pageList.hasPrevious());
		model.addAttribute("next",nextPage);
		model.addAttribute("page",pageList.getNumber());
		model.addAttribute("startpage",startpage);
		model.addAttribute("endpage",endpage);
		
		boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");

		if (hasAdminRole) {
		    // "user" 역할이 있는 경우의 처리
			log.info("admin의 권한을 가지고 있습니다.");
		} else {
		    // "user" 역할이 없는 경우의 처리
			log.info("admin의 권한을 가지고 있지않습니다.");
		}
		
		
		return "board/list";
	}


	@GetMapping("/{category}/write")
	public String writeGet(@PathVariable String category,Model model,@ModelAttribute("vo") PostSaveDto vo,Pageable pageable,@SessionAttribute("user") MemberSessionDto member) {
		log.info("PostController - writeGet method--------------------------------------------------");
		if (member == null) {
			log.info("로그인한 유저가 아님. redirect:/member/login ");
	        return "redirect:/member/login";
	    }
		
		if(category.equals("notice")) {
			boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");
			if(!hasAdminRole)
				{log.error("관리자가 아닌 사용자가 공지사항 글쓰기 접근");
					return "/home";
				}
			}
		
	    if (category != null) {
	        model.addAttribute("category", category);
	    }
		
		MemberInfoDto memberinfoDto = new MemberInfoDto();
		memberinfoDto.setDto(member);
		vo.setWriter(memberinfoDto);
		model.addAttribute("vo", vo);

		List<FileDto> fileList = new ArrayList<>(); 
		model.addAttribute("fileList",fileList);
		return "board/write";
	}

	@ResponseBody
	@PostMapping(value = "/{category}/posts",consumes = "multipart/form-data")
	public ResponseEntity<String> add(@RequestParam("category") String category,PostSaveDto board, @SessionAttribute("user") MemberSessionDto member,
			@RequestParam(value = "existIdx", required = false) List<Long> existIdx) {
		log.info("PostController - add method--------------------------------------------------");

		if(category.equals("notice")) {
			boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");
			if(!hasAdminRole)
				{log.error("관리자가 아닌 사용자가 공지사항 글쓰기 접근");
					return ResponseEntity.ok("잘못된 요청입니다.");
				}
			}
		
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setDto(member);
		board.setWriter(memberInfoDto);
		Post post = postService.save(board,existIdx);

		return new ResponseEntity<>("success",HttpStatus.OK);
	}

	@GetMapping("/{category}/get")
	public String getPost(@PathVariable String category,Model model, @RequestParam("idx") long idx) {
		log.info("PostController - getPost method--------------------------------------------------");
		PostInfoDto post = postService.getPost(idx,category);
		List<FileDto> fileList = fileService.getFileList(idx);
		model.addAttribute("post", post);
		model.addAttribute("fileList",fileList);
	    if (category != null) {
	        model.addAttribute("category", category);
	    }
	    return "board/get";
	}
	
	@GetMapping(value = "/{category}/download")
	public void downloadAttachFile(@PathVariable String category,@RequestParam("idx") Long idx,Model model,HttpServletResponse response) {
		log.info("PostController - downloadAttachFile Method--------------------------------------------------");
		FileDto fileInfo = fileService.getFileDetails(idx);
		String uploadDate = fileInfo.getCreatedDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get("D:","SpringBootProject","upload",uploadDate).toString();
		String filename = fileInfo.getOriginalName();
		File file = new File(uploadPath,fileInfo.getSaveName());
	    if (category != null) {
	        model.addAttribute("category", category);
	    }
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
			
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch (Exception e) {
			// TODO: handle exception
			
		}
	}

	@ResponseBody
	@DeleteMapping(value="/{category}/delete/{boardIdx}")
	public ResponseEntity<String> deletePost(@RequestParam("boardIdx") Long boardIdx,@RequestParam("category") String category) {
		log.info("PostController - deletePost method--------------------------------------------------");
		log.info("boardIdx :'"+boardIdx+"'");
		postService.deletePost(boardIdx,category);
		return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");		
	}
	
	@GetMapping(value = "/{category}/edit")
	public String getEdit(@PathVariable String category,Model model, @RequestParam("idx") long idx) {
		log.info("PostController - getPost method--------------------------------------------------");
		PostInfoDto post = postService.getPost(idx,category);
		List<FileDto> fileList = fileService.getFileList(idx);
		model.addAttribute("post", post);
		model.addAttribute("fileList",fileList);
	    model.addAttribute("category", category);
	    return "board/edit";
	}
	
	@PostMapping(value = "/{category}/edit")
	public void postEdit(Model model, @RequestParam("boardIdx") Long boardIdx,@RequestParam("category") String category) {
		log.info("PostController editPost method--------------------------------------------------");
		log.info("post idx:'"+boardIdx+"'");
		PostInfoDto post = postService.getPost(boardIdx,category);
		log.info("username :'"+post.getWriter().getUsername()+"'");
		List<FileDto> fileList = fileService.getFileList(boardIdx);
		model.addAttribute("category", category);
		model.addAttribute("post", post);
		model.addAttribute("fileList",fileList);
	}
	
	@ResponseBody
	@PutMapping(value = "/{category}/manage/list", consumes = "application/json")
	public ResponseEntity<String> ManagePost(@RequestBody manageListDto dataList){

		String category = dataList.getCategory();
		if(dataList.getCategory().isEmpty()||dataList.getBoardIdx().isEmpty()) {
			return ResponseEntity.ok("선택된 항목이 없습니다.");
		}
		boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");
		if (hasAdminRole) {
		    // "admin" 역할이 있는 경우의 처리
			log.info("admin의 권한을 가지고 있습니다.");
			for(Long i : dataList.getBoardIdx()) {
				postService.managerDeletePost(i, category);
			}
			return ResponseEntity.ok("삭제가 완료되었습니다.");
		} else {
		    // "admin" 역할이 없는 경우의 처리
			log.info("admin의 권한을 가지고 있지않습니다.");
		}

		return ResponseEntity.ok("잘못된 요청입니다.");
	}
}
