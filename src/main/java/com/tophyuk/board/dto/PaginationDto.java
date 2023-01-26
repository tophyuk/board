package com.tophyuk.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PaginationDto {

    private static final int pagePostCount = 5; // 한 페이지에 존재하는 게시글 수
    private int endPage;
    private int startPage;
    private int prev;
    private int next;

    @Builder
    public PaginationDto(Integer pageNum, int totalPages, long totalCnt) {
        this.endPage = (int) (Math.ceil(pageNum / 5.0 )) * pagePostCount;
        this.startPage = endPage - (pagePostCount-1);

        if( (int) (Math.ceil(totalCnt) * 1.0) / pagePostCount < endPage) {
            endPage = ((int) (Math.ceil(totalCnt) * 1.0) / pagePostCount) + 1;
        }

        int prevNext = pageNum / 5;

        this.prev = (prevNext * pagePostCount) - (pagePostCount-1) ;
        this.next = ((prevNext + 1) * pagePostCount) + 1;

        this.prev = prev < 1 ? 1 : prev;
        this.next = next > totalPages ? totalPages : next;

    }


}
