package com.board.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.board.domain.comment.dto.CommentGetDto;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.post.Post;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PostInfoDto {
	private Long idx;
	private String content;
	private String title;
	private LocalDateTime createdDate;
	private MemberInfoDto writer;
	private List<CommentGetDto> comments;
	private String category;
	private String deleteYn;
	
	
	public PostInfoDto(Post post){
		
		this.idx = post.getIdx();
		this.content = post.getContent();
		this.title = post.getTitle();
		this.writer = new MemberInfoDto(post.getWriter()); 
		//post.getWriter -> Member object를 불러옴. Member object에서 MemberInfoDto에서 선언한 필드의 데이터들만 뽑아옴.
		this.comments = post.getComments().stream()
				.map(comment -> new CommentGetDto(comment))
				.collect(Collectors.toList());
		this.createdDate = post.getCreatedDate();
		this.category=post.getCategory();
		this.deleteYn = post.getDeleteYn();
	}

	
}
