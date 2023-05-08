package com.board.domain.post.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PagingDTO {

	private String username;
	private String title;
	private LocalDateTime createdDate;

	public PagingDTO(String username, String title, LocalDateTime creaDateTime)
	{
		this.username = username;
		this.title = title;
		this.createdDate = creaDateTime;
	}
}
