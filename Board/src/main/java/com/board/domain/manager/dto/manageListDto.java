package com.board.domain.manager.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class manageListDto {

	private List<Long> boardIdx;
	private String category;
}
