package com.board.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.post.Post;
import com.board.domain.post.repository.PostRepository;
import com.board.domain.post.service.PostService;
import com.board.exception.AttachFileException;
import com.board.file.boardFile;
import com.board.file.dto.FileDto;
import com.board.file.exception.FileException;
import com.board.file.exception.FileExceptionType;
import com.board.file.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileServiceImpl implements FileService {
	
	private final FileRepository fileRepository;
	private final PostRepository postRepository;
	
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	private final String uploadPath = Paths.get("D:","SpringBootProject","upload",today).toString();
	
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@Override
public List<boardFile> save(MultipartFile[] multipartFile,Long boardIdx) {
	// TODO Auto-generated method stub
List<boardFile> attachList = new ArrayList<>();
		
		File dir = new File(uploadPath);
		if(dir.exists()==false) {
			dir.mkdirs();
		}
		
		for(MultipartFile file:multipartFile){
			if(file.getSize()<1) {
				continue;
			}
			try {
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				final String saveName = getRandomString() + '.' + extension;
				
				File target = new File(uploadPath,saveName);
				file.transferTo(target);
				boardFile attach = boardFile.builder().originalName(file.getOriginalFilename()).saveName(saveName).imageSize(file.getSize()).build();
				System.out.println(attach.getOriginalName()+attach.getSaveName());
				attachList.add(attach);
				System.out.println("파일파일처리"+boardIdx);
				Post pppost = postRepository.findById(boardIdx).orElseThrow();
				System.out.println("타이틀타이틀"+pppost.getTitle());
				//				attach.addFilese(postRepository.findByIdx(boardIdx).orElseThrow(()-> new FileException(FileExceptionType.FILE_CAN_NOT_SAVE)));
				attach.setPost(pppost);
				attach.setDeleteYn("N");
				fileRepository.save(attach);
			} catch (IOException e) {
				throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);

			} 
		}
	return attachList;
}

	@Override
	public void delete(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileDto getFileDetails(Long idx) {
		// TODO Auto-generated method stub 
		return new FileDto(fileRepository.findById(idx).orElseThrow(()->new FileException(FileExceptionType.File_NOT_EXIST)));
	}

	@Override
	public List<FileDto> getFileList(Long idx) {
		// TODO Auto-generated method stub
		List<FileDto> list = fileRepository.findByPostIdx(idx).stream()
											.filter(file->"N".equals(file.getDeleteYn()))
											.map(file -> new FileDto(file))
											.collect(Collectors.toList())
											;
		
		return list;
	}

	@Override
	public void saveFile(FileDto fileDto) {
		// TODO Auto-generated method stub
		Post post = postRepository.getById(fileDto.getBoardIdx());
		boardFile bf= fileDto.toEntity();
		bf.setPost(post);
		fileRepository.save(bf);
		
	}

}
