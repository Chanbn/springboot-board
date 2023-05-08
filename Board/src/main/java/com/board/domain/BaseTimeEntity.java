package com.board.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime lastModifiedDate;
	
    @Column(updatable = true)
    private LocalDateTime deletedDate;
    
    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
