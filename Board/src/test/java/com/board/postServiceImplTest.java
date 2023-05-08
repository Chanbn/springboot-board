package com.board;



import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.board.domain.post.repository.PostRepository;
import com.board.domain.post.service.PostService;
import com.board.domain.member.Member;
import com.board.domain.member.Role;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberSignUpDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.member.service.MemberService;
import com.board.domain.post.Post;
import com.board.domain.post.dto.PostSaveDto;


@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.transaction.annotation.Transactional
public class postServiceImplTest {

//	@Autowired
//private EntityManager em;

@Autowired
private WebApplicationContext context;


@Autowired
private PostService postService;

@Autowired
private PostRepository postRepository;

@Autowired
private MemberRepository memberRepository;

@Autowired
private MemberService memberService;

@Autowired
private HttpSession session;

private String title = "제목";
private String content = "내용";

private String username = "aaa123";
private String password = "!qlalfqjsgh12";
private String nickname = "닉네임";
private String email = "eee@email.com";
private String name = "내이름";

private MockMvc mvc;

//private void clear(){
//    em.flush();
//    em.clear();
//}
//
//private List<Post> findPostList() {
//	return em.createQuery("select p from Post p", Post.class).getResultList();
//}
//
//private Post findPost() {
//	return em.createQuery("select p from Post p", Post.class).getSingleResult();
//}



//@WithUserDetails(value = "abc123")
//@Test
//@Rollback(false)
//public void writetest() {
//	PostSaveDto postSaveDto = new PostSaveDto(title,content);
//
//	for(int i=0;i<1;i++) {
//		postSaveDto.setTitle(title+i);
//		postSaveDto.setContent(content+i);
//		Post post = postSaveDto.toEntity();
//		post.confirmWriter(memberRepository.findByUsername("abc123").orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));
//		
//		postRepository.save(post);
//	}
//	List<Post> findPostList = findPostList();
//    Post post = em.find(Post.class, findPostList.get(6).getIdx());
//    assertThat(post.getContent()).isEqualTo(content);
//}


}
