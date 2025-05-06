package com.example.yellowaution.controller.page;

import com.example.yellowaution.dto.UserRegisterDto;
import com.example.yellowaution.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController 클래스는 웹 애플리케이션의 인증 관련
 * 페이지 요청을 처리하는 컨트롤러입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>회원가입 폼 보여주기</li>
 *   <li>회원가입 요청 처리</li>
 *   <li>로그인 폼 보여주기</li>
 *   <li>로그아웃 처리</li>
 * </ul>
 * </p>
 *
 * <p>View 이름과 URL 매핑을 분리하여 깔끔한 구조를 제공합니다.</p>
 */
@Controller
public class AuthController {

    /**
     * 사용자 관련 비즈니스 로직을 수행하는 서비스 클래스.
     * 회원가입, 중복검사, 비밀번호 암호화 등 처리를 역할 분리하여 담당합니다.
     */
    private final UserService userService;

    /**
     * 생성자를 통한 의존성 주입(Constructor Injection).
     * 스프링 컨테이너가 UserService 빈을 주입하여 사용할 수 있도록 합니다.
     *
     * @param userService 사용자 관리 로직을 제공하는 서비스 객체
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 폼을 요청(GET)할 때 호출되는 핸들러 메서드.
     *
     * <p>요청 URL: /register<br>
     * 반환 View: register.html (회원가입 폼 템플릿)</p>
     *
     * @return 회원가입 폼 뷰의 이름
     */
    @GetMapping("/register")
    public String registerForm() {
        // 단순히 회원가입 폼 페이지로 이동
        return "register";
    }

    /**
     * 회원가입 폼에서 전달된 데이터로 실제 회원 가입 처리를 수행하는 핸들러.
     *
     * <p>요청 URL: /register<br>
     * HTTP Method: POST</p>
     *
     * @param dto 회원가입 폼에서 받은 데이터를 담은 DTO 객체
     *            - username, password, email 등 필드 포함
     *            @ModelAttribute를 통해 자동으로 바인딩됨
     * @return 회원가입 성공 후 로그인 페이지로 리다이렉트
     */
    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDto dto) {
        // 서비스 레이어에 회원가입 로직 위임
        // - DTO 유효성 검사
        // - 비밀번호 암호화
        // - DB에 사용자 정보 저장
        userService.register(dto);
        // 회원가입 완료 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }

    /**
     * 로그인 폼을 요청(GET)할 때 호출되는 핸들러 메서드.
     *
     * <p>요청 URL: /login<br>
     * 로그인 실패 시 파라미터로 error가 전달되면
     * 모델에 오류 메시지를 추가하여 뷰에 표시합니다.</p>
     *
     * @param error   로그인 실패 시 스프링 시큐리티가 자동으로 전달하는 파라미터
     * @param model   뷰에 데이터를 전달하기 위한 Model 객체
     * @return 로그인 폼 뷰의 이름
     */
    @GetMapping("/login")
    public String loginForm(
            @RequestParam(name = "error", required = false) String error,
            Model model) {

        // 로그인 실패 시 error 파라미터가 존재하면
        // "loginError"라는 이름으로 뷰에 오류 메시지 전달
        if (error != null) {
            model.addAttribute("loginError", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        }
        // 커스텀 로그인 페이지로 이동
        return "login";
    }

    /**
     * 로그아웃 요청(GET)을 처리하는 핸들러 메서드.
     *
     * <p>요청 URL: /logout<br>
     * 스프링 시큐리티가 처리하기 전 세션 무효화를 직접 수행합니다.</p>
     *
     * @param session 현재 사용자의 HttpSession 객체
     * @return 로그아웃 후 로그인 페이지로 리다이렉트
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션에 저장된 모든 정보 제거 및 세션 무효화
        session.invalidate();
        // 로그아웃 완료 후 로그인 페이지로 이동
        return "redirect:/login";
    }
}
