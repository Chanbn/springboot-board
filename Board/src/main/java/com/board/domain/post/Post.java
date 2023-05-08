package com.board.domain.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.Page;

import com.board.domain.BaseTimeEntity;
import com.board.domain.comment.Comment;
import com.board.domain.member.Member;
import com.board.domain.post.dto.PostInfoDto;
import com.board.file.boardFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Post")
@ToString
@Slf4j
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idx;

	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;
	
	private String content;
	private String deleteYn;

	private String category;
	
	  @Builder
	    public Post(String title, String content, String category) {
	        this.title = title;
	        this.content = content;
	        this.category = category;
	    }
	  
	  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	  private List<boardFile> fileLists = new ArrayList<>();
	  
	  public void addFile(boardFile files)
	  {
		  log.info("addFile....");
		  fileLists.add(files);
		  files.setPost(this);
		  log.info("addFile....완료");
	  }
	  
	  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	  private List<Comment> comments = new ArrayList<>();
	  
	  public void addComment(Comment comment) {
		  comments.add(comment);
		  comment.setPost(this);		  
	  }
	 
	  public void update(String title,String content) {
		  this.title = title;
		  this.content = content;
	  }
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
	
    public void confirmWriter(Member writer) {
    	this.writer = writer;
    	writer.addPost(this);
    }
    
    public void setWriter(Member writer) {
    	this.writer = writer;
    }

    
	 
}
