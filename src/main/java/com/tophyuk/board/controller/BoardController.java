package com.tophyuk.board.controller;

import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;
    @GetMapping("/list")
    public String list() {
        return "board/list";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write";
    }

    @PostMapping("/post")
    public String add(BoardDto boardDto) {
        log.info("boardDto class={}", boardDto.getWriter());

        //todo - JPA -> DB 저장하는 service 만들기
        boardService.save(boardDto);

        return "redirect:/board/list";
    }


}
