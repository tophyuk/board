package com.tophyuk.board;

import com.tophyuk.board.domain.Board;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private BoardRepository boardRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void save(){

		//given
		String title = "오늘도 강추위가 예상됩니다.";
		String writer = "홍길동";
		String content = "내용을 적으려니까 적을게 참 많네요" +
				"그래도 계속해서 적어 나갑니다! 특스문자 ^&*(도 써보고, English도 써보고";

		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(title);
		boardDto.setWriter(writer);
		boardDto.setContent(content);

		//when
		Long id = boardRepository.save(boardDto.toEntity()).getId();
		Board findBoard = boardRepository.findById(id).get();

		//then
		Assertions.assertThat(findBoard.getWriter()).isEqualTo(writer);

	}

}
