package com.board.domain.member.dto;

import java.util.List;

import com.board.domain.comment.dto.CommentGetDto;
import com.board.domain.post.dto.PostInfoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDetailDto {
	private String username;
	private String nickname;
	private String email;
	private Long id;
	private List<CommentGetDto> commentList;
	private List<PostInfoDto> hh;
	private String delete_yn;

}
