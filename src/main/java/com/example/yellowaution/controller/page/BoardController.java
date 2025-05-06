package com.example.yellowaution.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 게시판 관련 요청을 처리하는 컨트롤러 클래스입니다.
 *
 * <p>기본 URL 패턴은 /board/** 이며, URL 뒤에 붙는 {page} 또는 {page}/{id} 경로
 * 변수에 따라 서로 다른 뷰 템플릿을 반환합니다.</p>
 */
@Controller
@RequestMapping("board")  // "/board" 로 시작하는 모든 요청을 이 컨트롤러가 처리
public class BoardController {

    /**
     * 게시판 내 일반 페이지를 보여줄 때 사용되는 핸들러 메서드입니다.
     *
     * <p>예시:
     * - 클라이언트가 "/board/list" 요청 → page 변수에 "list" 가 바인딩 →
     *   뷰 이름 "board/list" 반환 → resources/templates/board/list.html 렌더링</p>
     *
     * @param page 요청된 페이지 이름 (예: "list", "write", "edit" 등)
     * @return 실제로 렌더링할 뷰 템플릿 경로 ("board/" + page)
     */
    @GetMapping("/{page}")
    public String page(@PathVariable String page) {
        // PathVariable("page")로 전달된 값에 해당하는 뷰를 동적으로 결정
        return "board/" + page;
    }

    /**
     * 게시판 글 상세보기 등, ID가 필요한 페이지를 보여줄 때 사용되는 핸들러 메서드입니다.
     *
     * <p>예시:
     * - 클라이언트가 "/board/detail/123" 요청 → page="detail", id="123"
     *   → 뷰 이름 "board/detail" 반환 → resources/templates/board/detail.html 렌더링</p>
     *
     * <p>추가 설명:
     * - id 값을 이용해 DB 조회 후 모델에 데이터를 담는 로직을 여기에 추가할 수 있습니다.
     * - 현재 구현은 뷰 이름만 반환하므로, 컨트롤러에서 모델을 채우려면
     *   메서드 시그니처에 Model 파라미터를 추가하고 userService 등을 호출해야 합니다.</p>
     *
     * @param page 요청된 페이지 이름 (예: "detail")
     * @param id   조회할 대상 글의 고유 식별자 (예: "123")
     * @return 실제로 렌더링할 뷰 템플릿 경로 ("board/" + page)
     */
    @GetMapping("/{page}/{id}")
    public String pageWithId(@PathVariable String page, @PathVariable String id) {
        return "board/" + page;
    }

}
