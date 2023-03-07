package com.tophyuk.board;

import com.tophyuk.board.auth.userinfo.GoogleUserInfo;
import com.tophyuk.board.auth.userinfo.KakaoUserInfo;
import com.tophyuk.board.auth.userinfo.NaverUserInfo;
import com.tophyuk.board.auth.userinfo.OAuth2UserInfo;
import com.tophyuk.board.domain.Board;
import com.tophyuk.board.domain.Role;
import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.BoardDto;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.BoardRepository;
import com.tophyuk.board.repository.UserRepository;
import com.tophyuk.board.service.CustomOAuth2UserService;
import com.tophyuk.board.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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

		String nickname="홍길동";
		String email="gildong@naver.com";
		String password="ghdrlfehd1!";
		String region="DAEGU";

		//given
		UserDto userDto = new UserDto();
		userDto.setNickname(nickname);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setRegion(region);

		//when
		long id = userRepository.save(userDto.toEntity()).getId();
		User findUser = userRepository.findById(id).get();

		//then
		Assertions.assertThat(findUser.getEmail()).isEqualTo(email);
	}

	@Test
	@DisplayName("이름 중복 체크")
	void nameCheck() {

		//given
		String username = "홍길동2";
		UserDto userDto = new UserDto();
		userDto.setNickname(username);


		//when
		boolean existsByUsername = userRepository.existsByNickname(userDto.toEntity().getNickname());

		//then
		Assertions.assertThat(existsByUsername).isEqualTo(true);

	}


	@Test
	@DisplayName("이메일 중복 체크")
	void emailCheck() {

		//given
		String email = "sanghyuk1992@naver.com";
		UserDto userDto = new UserDto();
		userDto.setEmail(email);
		userDto.setLoginType("basic");

		//when
		boolean existsByEmailAndLoginType = userRepository.existsByEmailAndLoginType(userDto.toEntity().getEmail(), userDto.getLoginType());

		//then
		Assertions.assertThat(existsByEmailAndLoginType).isEqualTo(true);

	}

	@Test
	@DisplayName("닉네임 중복 체크")
	void nicknameCheck() {

		//given
		String nickname ="김반장";
		UserDto userDto = new UserDto();
		userDto.setNickname(nickname);

		//then
		boolean existsByNickname = userRepository.existsByNickname(userDto.toEntity().getNickname());

		//when
		Assertions.assertThat(existsByNickname).isEqualTo(true);
	}

	@Test
	@DisplayName("이메일로 사용자 찾기")
	void userByEmail() {

		//given
		String email = "kim@naver.com";

		//then
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "는 존재하지 않는 이메일입니다."));

		UserDto userDto = new UserDto().toUserDto(user); // User<Entity> -> UserDto 변환.

		//when
		Assertions.assertThat(userDto.getEmail()).isEqualTo(email);

	}

	@Test
	@DisplayName("스프링시큐리티 로그인")
	void securityLogin(){

		//given
		String nickname = "김반장2";
		String email = "kim2@naver.com";
		String password = "wjdtkd1!";
		String encodePassword = userService.bCryptPasswordEncoder.encode(password);
		String region = "SEOUL";
		Role role = Role.USER;

		UserDto userDto = new UserDto(nickname, email, encodePassword, region, role, "", null, "basic");
		userService.signup(userDto);

		//then
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "는 존재하지 않는 이메일입니다."));
		UserDetails userDetails = userService.loadUserByUsername(email);

		//when
		Assertions.assertThat(userDto.getPassword()).isEqualTo(userDetails.getPassword());

	}


	@Test
	@DisplayName("스프링시큐리티 로그아웃")
	void securityLogout() {


		//given
		String email = "kim2@naver.com";

		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "는 존재하지 않는 이메일입니다."));
		UserDetails userDetails = userService.loadUserByUsername(email);
		//then


		//when

	}

	@Test
	@DisplayName("SNS 회원가입 및 로그인")
	void snsLogin() {

		//given
		String uuid = UUID.randomUUID().toString().substring(0, 6);
		String nickname = "닉네임";	// 사용자가 입력한 적은 없지만 만들어준다

		String email = "sanghyuk13992@naver.com";


		String password = bCryptPasswordEncoder.encode("basic1!"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다
		String region = "SEOUL";
		Role role = Role.USER;
		String picture = "";
		String loginType = "naver";

		OAuth2UserInfo oAuth2UserInfo = null;

		UserDto userDto = new UserDto(nickname, email, password, region, role, picture, oAuth2UserInfo, loginType);
		User user = userDto.toEntity();

		//then
		User findUser = userRepository.findByEmailAndLoginType(email, loginType)
								.orElseGet(() -> userRepository.save(user));

		//when
		Assertions.assertThat(findUser.getEmail()).isEqualTo(email);
	}


}
