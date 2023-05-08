package com.board.domain.comment.dto;

import com.board.domain.comment.Comment;
import com.board.domain.member.Member;
import com.board.domain.post.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveDto {
	private Long postId;
	private Long parentId;
	private Long userId;
	private String username;
	private String content;
//	@Builder
//	public CommentSaveDto(Comment comment) {
//		this.username = comment.getWriter().getUsername();
//		this.content = comment.getContent();
//		this.postId = comment.getPost().getIdx();
//		this.parentId = comment.getParentComment() != null ? comment.getParentComment().getIdx() : null;
//		this.userId = comment.getWriter().getId();
//	}
	
	public Comment toEntity(Member member, Post post,Comment comment) {	
		return Comment.builder().
				content(content).
				parentComment(comment).
				writer(member).
				post(post).
				build();
	}
}
