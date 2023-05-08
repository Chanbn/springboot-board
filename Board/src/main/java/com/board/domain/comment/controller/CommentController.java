package com.board.domain.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.comment.dto.CommentGetDto;
import com.board.domain.comment.dto.CommentSaveDto;
import com.board.domain.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	private final CommentService commentService;
	
	@PostMapping(value = "/{category}/add", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> writeComment(@PathVariable String category,@RequestBody CommentSaveDto comment) {
		log.info("CommentController - writeComment method--------------------------------------------------");
		log.info("content :'"+comment.getContent()+"' username :'"+comment.getUsername());
		commentService.save(comment,category);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@GetMapping(value = "/{postId}")
	public ResponseEntity<List<CommentGetDto>> getComment(@RequestParam("postId") Long postId){
		log.info("CommentController - getComment method--------------------------------------------------");
		log.info("postId : "+postId);
		return ResponseEntity.status(HttpStatus.OK).body(commentService.get(postId));
	}
	
	@DeleteMapping(value = "/{commentIdx}")
	public ResponseEntity<String> deleteComment(@RequestParam("commentIdx") Long Idx){
		log.info("CommentController - deleteComment method--------------------------------------------------");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			commentService.delete(Idx,auth);			
		
		return ResponseEntity.ok("success");
	}
}
