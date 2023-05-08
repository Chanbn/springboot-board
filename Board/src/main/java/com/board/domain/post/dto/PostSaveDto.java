package com.board.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.post.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveDto {

	private Long idx;
//	private Long userId;
//	private String writer;
	private String content;
	private String title;
//	private String email;
//	private String username;
	private MultipartFile[] files; 
	private MemberInfoDto writer;
	private String category;
	
	public PostSaveDto(String title, String content,String category) {
		// TODO Auto-generated constructor stub
		this.title=title;
		this.content=content;
		this.category=category;
	}
	
	public void setWriter(MemberInfoDto writer) {
		this.writer=writer;
	}
	
	public Post toEntity() {
		return Post.builder().title(title).content(content).category(category).build();
		
	}
}
