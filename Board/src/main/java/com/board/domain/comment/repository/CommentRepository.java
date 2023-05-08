package com.board.domain.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.domain.comment.Comment;
import com.board.domain.comment.dto.CommentInfoDto;
import com.board.domain.post.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	public List<Comment> findByPost(Post post);
	
	@Query(value = "SELECT * FROM board_comment WHERE post_id = :postId  START WITH parent_id IS NULL CONNECT BY PRIOR idx=parent_id ORDER SIBLINGS BY created_date ASC", nativeQuery = true)
	List<Comment> findTopLevelCommentsOrderByCreatedAtDesc(@Param("postId") Long postId);
	
	Page<CommentInfoDto> findByWriterUsername(String writer,Pageable pageable);
}
