package com.tophyuk.board.service;

import com.tophyuk.board.domain.Board;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    /**게시글 목록 불러오기 **/
    public Page<BoardDto> getList(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate"));
        Page<Board> boardList = boardRepository.findAll(pageRequest);
        Page<BoardDto> boardDtoList = new BoardDto().toDto(boardList); // Page<Entity> -> Page<Dto> 변환.



        return boardDtoList;

    }

    public long getTotalCnt() {
        long count = boardRepository.count();
        return count;
    }

    /** 게시글 글쓰기 **/
    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }


}