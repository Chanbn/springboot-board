package com.board.domain.post.service;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.post.Post;
import com.board.domain.post.dto.PostInfoDto;
import com.board.domain.post.dto.PostSaveDto;
import com.board.file.dto.FileDto;

public interface PostService {
	Post save(PostSaveDto postSaveDto,List<Long> existIdx);
	List<PostInfoDto> getPageList(Pageable pageable);
//	Page<Post> findByTitleContaining(String title,Pageable pageable);
	Page<PostInfoDto> SearchPost(String type, String word, Pageable pageable,String category);
	PostInfoDto getPost(Long idx,String category);
	Page<PostInfoDto> getPostList(String username,Pageable pageable);

	List<PostInfoDto> getNoticeList();
	
	void deletePost(Long boardIdx,String category);
	void managerDeletePost(Long boardIdx,String category);
}
