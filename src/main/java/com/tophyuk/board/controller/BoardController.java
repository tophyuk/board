package com.tophyuk.board.controller;

import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;
    @GetMapping("/list")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getList();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write";
    }

    @PostMapping("/post")
    public String add(BoardDto boardDto) {
        log.info("boardDto class={}", boardDto.getWriter());
        boardService.save(boardDto);
        return "redirect:/board/list";
    }


}
