package com.board.domain.post.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.board.domain.post.Post;
import com.board.domain.post.cond.PostSearchCondition;




@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository {


	public CustomPostRepositoryImpl(EntityManager em) {
//		query = new JPAQueryFactory(em);
	}
	
	@Override
public Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable) {
	// TODO Auto-generated method stub
//		List<Post> content = query.selectFrom(post)
//				.
		return null;
}
}
