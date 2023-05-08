package com.board.file.dto;

import java.time.LocalDateTime;

import com.board.file.boardFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
	private Long idx;

	/** 게시글 번호 (FK) */
	private Long boardIdx;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
	private long imageSize;
	
	private String deleteYn;
	
	private LocalDateTime createdDate; 
	
	public FileDto(boardFile file) {
		this.idx = file.getIdx();
		this.boardIdx = file.getPost().getIdx();
		this.originalName = file.getOriginalName();
		this.saveName = file.getSaveName();
		this.imageSize = file.getImageSize();
		this.deleteYn = file.getDeleteYn();
		this.createdDate = file.getCreatedDate();
	}
	
	public boardFile toEntity() {
		return boardFile.builder().boardIdx(boardIdx).idx(idx).originalName(originalName).saveName(saveName).imageSize(imageSize).deleteYn(deleteYn).build();	
				}
	
}
