package com.tophyuk.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDto {

    private String searchType;
    private String keyword;
    private String writer;


}
