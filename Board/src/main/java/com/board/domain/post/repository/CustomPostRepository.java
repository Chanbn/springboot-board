package com.board.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.board.domain.post.Post;
import com.board.domain.post.cond.PostSearchCondition;

public interface CustomPostRepository {

    Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable);
}
