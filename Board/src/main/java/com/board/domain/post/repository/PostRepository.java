package com.board.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.domain.comment.dto.CommentInfoDto;
import com.board.domain.post.Post;
import com.board.domain.post.dto.PostInfoDto;

public interface PostRepository extends JpaRepository<Post, Long>{

	Page<Post> findAllByCategoryAndDeleteYn(Pageable pageable,String category,String DeleteYn);
	Page<Post> findByTitleContainingAndCategoryAndDeleteYn(String title,Pageable pageable,String category,String DeleteYn);
	Page<Post> findByTitleContainingOrContentContainingAndCategoryAndDeleteYn(String word, String word2, Pageable pageable,String category,String DeleteYn);
	Page<Post> findByWriterUsernameContainingAndCategoryAndDeleteYn(String writer,Pageable pageable,String category,String DeleteYn);
	Page<PostInfoDto> findByWriterUsername(String writer,Pageable pageable);

	Page<PostInfoDto> findByWriterUsernameAndDeleteYn(String writer,Pageable pageable,String deleteYn);
	
	Optional<Post> findByIdxAndCategory(Long idx,String category);
	Post deleteByIdxAndCategory(Long idx,String category);
	
	Post findByIdxAndDeleteYn(Long idx, String deleteYn);
	
	List<Post> findTop5ByCategoryOrderByCreatedDateDesc(String category);

	
}  
