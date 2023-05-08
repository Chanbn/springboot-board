package com.board.domain.comment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.board.domain.comment.Comment;
import com.board.domain.comment.dto.CommentGetDto;
import com.board.domain.comment.dto.CommentInfoDto;
import com.board.domain.comment.dto.CommentSaveDto;

public interface CommentService {
	void save(CommentSaveDto commentData,String category);
	void delete(Long idx,Authentication auth);
	List<CommentGetDto> get(Long postId);
	
	Page<CommentInfoDto> getCommentList(String username, Pageable pageable);
}
