package com.board.domain.member.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.member.Member;

public interface MemberRepository extends JpaRepository< Member,Long >{
			boolean existsByEmail(String email);
			boolean existsByUsername(String username);
			boolean existsByNickname(String nickname);
			
			Optional<Member> findByUsername(String username);
}
