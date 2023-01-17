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

import java.util.*;

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
        Page<BoardDto> boardDtoList = new BoardDto().toPageBoardDto(boardList); // Page<Entity> -> Page<Dto> 변환.

        return boardDtoList;

    }

    /** 게시글 목록 페이징 처리 **/
    public Map<String, Object> getPaging(Integer pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate"));
        Page<Board> boardList = boardRepository.findAll(pageRequest);
        Page<BoardDto> boardDtoList = new BoardDto().toPageBoardDto(boardList); // Page<Entity> -> Page<Dto> 변환.

        Map<String, Object> map = new HashMap<String, Object>();

        int endPage = (int) (Math.ceil(pageNum / 5.0 )) * PAGE_POST_COUNT;
        int startPage = endPage - (PAGE_POST_COUNT-1);
        int totalPages = boardDtoList.getTotalPages();
        long totalCnt = boardDtoList.getTotalElements();

        if( (int) (Math.ceil(totalCnt) * 1.0) / PAGE_POST_COUNT < endPage) {
            endPage = ((int) (Math.ceil(totalCnt) * 1.0) / PAGE_POST_COUNT) + 1;
        }

        int prevNext = pageNum / 5;

        int prev = (prevNext * PAGE_POST_COUNT) - (PAGE_POST_COUNT-1) ;
        int next = ((prevNext + 1) * PAGE_POST_COUNT) + 1;

        prev = prev < 1 ? 1 : prev;
        next = next > totalPages ? totalPages : next;


        map.put("prev", prev);
        map.put("next", next);
        map.put("startPage", startPage);
        map.put("endPage", endPage);

        return map;
    }

    /** 게시판 조회 **/
    public BoardDto getBoard(Long id){
        // Optional : NullPointException 방지
        Optional<Board> boardOptional = boardRepository.findById(id);
        Board board = boardOptional.get();
        BoardDto boardDto = new BoardDto().toBaordDto(board);

        return boardDto;
    }


    /** 게시글 글쓰기 **/
    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }


}