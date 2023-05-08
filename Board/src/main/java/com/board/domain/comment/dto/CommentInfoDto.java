package com.board.domain.comment.dto;

import java.time.LocalDateTime;

import com.board.domain.comment.Comment;
import com.board.domain.member.dto.MemberInfoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentInfoDto {
	private Long idx;
	private String content;
	private String delete_yn;
	private LocalDateTime createdDate;
	private MemberInfoDto writer;
	
	public CommentInfoDto(Comment comment) {
		this.idx = comment.getIdx();
		this.content = comment.getContent();
		this.delete_yn = comment.getDelete_yn();
		this.createdDate = comment.getCreatedDate();
		this.writer = new MemberInfoDto(comment.getWriter()); 
	}
}
