package com.tophyuk.board;

import com.tophyuk.board.domain.Board;
import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.BoardRepository;
import com.tophyuk.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	String title = "오늘도 강추위가 예상됩니다.";
	String writer = "홍길동";
	String content = "내용을 적으려니까 적을게 참 많네요" +
			"그래도 계속해서 적어 나갑니다! 특스문자 ^&*(도 써보고, English도 써보고";


	@Test
	@DisplayName("게시글 등록 테스트")
	void save() {

		//given
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

	@Test
	@DisplayName("JPA Auditing 테스트")
	void auditing() {
		//given
		LocalDateTime now = LocalDateTime.of(2022, 01, 10, 0, 0, 0);

		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(title);
		boardDto.setWriter(writer);
		boardDto.setContent(content);
		boardDto.toEntity();

		//when
		Long id = boardRepository.save(boardDto.toEntity()).getId();
		Board findBoard = boardRepository.findById(id).get();

		//then
		Assertions.assertThat(findBoard.getCreatedDate()).isAfter(now);
		Assertions.assertThat(findBoard.getModifiedDate()).isAfter(now);

	}


	@Test
	@DisplayName("게시글 조회 테스트")
	void getBoard() {

		//given
		long id = 10;
		Optional<Board> boardOptional = boardRepository.findById(id);
		Board board = boardOptional.get();

		//when
		BoardDto boardDto = new BoardDto().toBoardDto(board);

		//then
		Assertions.assertThat(boardDto.getId()).isEqualTo(id);

	}

	@Test
	@DisplayName("게시글 삭제 테스트")
	void delete() {

		//given
		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(title);
		boardDto.setWriter(writer);
		boardDto.setContent(content);
		boardDto.toEntity();

		Long id = boardRepository.save(boardDto.toEntity()).getId();

		//when
		boardRepository.deleteById(id);


		//then
		Board board = boardRepository.findById(id).get();
		Assertions.assertThat(board).isNotNull();

	}


	@Test
	@DisplayName("게시글 수정 테스트")
	void update() {

		//given
		long id = 10;
		String title = "제목 바꿔봅니다.";
		Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
		BoardDto boardDto = new BoardDto().toBoardDto(board);
		boardDto.setTitle(title);

		//when
		Board saveBoard = boardRepository.save(boardDto.toEntity());

		//then
		Assertions.assertThat(board.getTitle()).isNotEqualTo(saveBoard.getTitle());
	}


	@Test
	@DisplayName("회원가입 테스트")
	void signup() {

		String username="홍길동";
		String email="gildong@naver.com";
		String password="ghdrlfehd1!";
		String region="DAEGU";

		//given
		UserDto userDto = new UserDto();
		userDto.setUsername(username);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setRegion(region);

		//when
		long id = userRepository.save(userDto.toEntity()).getId();
		User findUser = userRepository.findById(id).get();

		//then
		Assertions.assertThat(findUser.getEmail()).isEqualTo(email);
	}
}
