package com.tophyuk.board.service;

import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }


}