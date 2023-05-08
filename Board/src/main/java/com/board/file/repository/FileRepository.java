package com.board.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.file.boardFile;

public interface FileRepository extends JpaRepository<boardFile, Long> {
	List<boardFile> findByPostIdx(Long idx);
}
