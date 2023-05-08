package com.board.domain.comment.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.board.domain.comment.Comment;
import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.post.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentGetDto {

	private Long idx;
	private String content;
	private String delete_yn;
	private LocalDateTime createDate;
	private MemberInfoDto writer;
	private CommentGetDto parentCommentDto;
	
	public CommentGetDto(Comment comment) {
		this.idx = comment.getIdx();
		this.content = comment.getContent();
		this.createDate = comment.getCreatedDate();
		this.writer = new MemberInfoDto(comment.getWriter());
		this.delete_yn = comment.getDelete_yn();
		this.parentCommentDto = comment.getParentComment() !=null? new CommentGetDto(comment.getParentComment()) : null;
	}
	
	public Comment toEntity() {
		Comment comment = Comment.builder()
				.content(content)
				.writer(this.writer.toEntity())
				.post(this.parentCommentDto != null ? this.parentCommentDto.toEntity().getPost() : null)
				.parentComment(this.parentCommentDto != null ? this.parentCommentDto.toEntity() : null)
				.delete_yn(delete_yn)
				.build();

		return comment;
	}
}
