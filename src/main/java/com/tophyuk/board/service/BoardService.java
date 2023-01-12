package com.tophyuk.board.service;

import com.tophyuk.board.domain.Board;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /* 게시글 목록 불러오기 */
    public List<BoardDto> getList() {
        BoardDto boardDto = new BoardDto();
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : boardList) {
            boardDtoList.add(boardDto.toDto(board));
        }

        return boardDtoList;

    }

    /* 게시글 글쓰기 */
    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }


}