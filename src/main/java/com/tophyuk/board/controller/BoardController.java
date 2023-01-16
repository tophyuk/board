package com.tophyuk.board.controller;

import com.tophyuk.board.domain.Board;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.repository.BoardRepository;
import com.tophyuk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        Page<BoardDto> boardDtoList = boardService.getList(pageNum);

        int endPage = (int) (Math.ceil(pageNum / 5.0 )) * PAGE_POST_COUNT;
        int startPage = endPage - (PAGE_POST_COUNT-1);
        int totalPages = boardDtoList.getTotalPages();
        long totalCnt = boardService.getTotalCnt();

        if( (int) (Math.ceil(totalCnt) * 1.0) / PAGE_POST_COUNT < endPage) {
            endPage = ((int) (Math.ceil(totalCnt) * 1.0) / PAGE_POST_COUNT) + 1;
        }

        int prevNext = pageNum / 5;

        int prev = (prevNext * PAGE_POST_COUNT) - (PAGE_POST_COUNT-1) ;
        int next = ((prevNext + 1) * PAGE_POST_COUNT) + 1;

        prev = prev < 1 ? 1 : prev;
        next = next > totalPages ? totalPages : next;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("boardDtoList", boardDtoList);

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
