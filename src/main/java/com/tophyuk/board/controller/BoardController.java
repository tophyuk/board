package com.tophyuk.board.controller;

import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.dto.PaginationDto;
import com.tophyuk.board.dto.SearchDto;
import com.tophyuk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page, SearchDto searchDto) {

        Map<String, Object> boardDtoList = boardService.getList(page, searchDto);
        PaginationDto paginationDto = (PaginationDto) boardDtoList.get("paginationDto");

        model.addAttribute("startPage", paginationDto.getStartPage());
        model.addAttribute("endPage", paginationDto.getEndPage());
        model.addAttribute("prev", paginationDto.getPrev());
        model.addAttribute("next", paginationDto.getNext());

        model.addAttribute("page", page);
        model.addAttribute("searchDto", searchDto);

        model.addAttribute("boardDtoList", boardDtoList.get("boardDtoList"));

        return "board/list";
    }

    @GetMapping("/{no}")
    public String detail(@PathVariable(value="no") Long no,
                         @RequestParam(value="page", required=false) Integer page,
                         SearchDto searchDto,
                         Model model) {

        BoardDto boardDto = boardService.getBoard(no);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("page", page);
        return "board/detail";
    }

    @GetMapping("/post")
    public String write(@RequestParam(value="page", required=false) Integer page,
                        SearchDto searchDto,Model model) {

        model.addAttribute("searchDto", searchDto);
        model.addAttribute("page", page);

        return "board/write";
    }

    @PostMapping("/post")
    public String add(BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/board/list";
    }

    @DeleteMapping("/{no}")
    public String delete(@PathVariable Long no) {

        boardService.delete(no);

        return "redirect:/board/list";
    }

    @GetMapping("/edit/{no}")
    public String edit(@PathVariable Long no, @RequestParam(value="page") Integer page, SearchDto searchDto, Model model) {
        BoardDto boardDto = boardService.getBoard(no);

        model.addAttribute("page", page);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("boardDto", boardDto);

        return "/board/update";
    }

    @PutMapping("/edit/{no}")
    public String update(@PathVariable Long no, @RequestParam(value="page") Integer page,  BoardDto boardDto,
                         SearchDto searchDto,
                         RedirectAttributes redirectAttributes) {

        boardService.save(boardDto);

        redirectAttributes.addAttribute("searchDto", searchDto);
        redirectAttributes.addAttribute("page", page);


        return "redirect:/board/{no}";
    }


}
