package com.example.yellowaution.controller.page;

import com.example.yellowaution.service.BoardService;
import com.example.yellowaution.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DashboardController 클래스는 사용자 유형별 대시보드 페이지 요청을 처리합니다.
 *
 * <ul>
 *   <li>대시보드 메인 및 서브페이지 렌더링</li>
 *   <li>관리자 상세 페이지 요청 시 모델에 필요한 데이터(board, bids) 추가</li>
 * </ul>
 *
 * URL 매핑:
 * - GET /dashboard/{page}
 * - GET /dashboard/{page}/{id}
 */
@Controller
@RequestMapping("/dashboard")    // "/dashboard"로 시작하는 모든 요청을 이 컨트롤러에서 처리
@RequiredArgsConstructor         // final 필드를 자동으로 생성자 주입 (Lombok)
public class DashboardController {

    /**
     * BoardService는 게시물(프로젝트) 정보를 조회하는 서비스입니다.
     * - getById(id): PK(id)로 단일 게시물 조회
     */
    private final BoardService boardService;

    /**
     * BidService는 입찰 정보를 조회하는 서비스입니다.
     * - findAllForAdminByBoardId(id): 관리자용으로 특정 게시물(id)에 대한 전체 입찰 목록 조회
     */
    private final BidService bidService;

    /**
     * 대시보드 페이지 일반 요청 처리 메서드.
     *
     * <p>예시:
     * - /dashboard/admin_dashboard → "dashboard/admin_dashboard" 뷰 반환
     * - /dashboard/com_dashboard   → "dashboard/com_dashboard" 뷰 반환
     * - /dashboard/free_dashboard  → "dashboard/free_dashboard" 뷰 반환
     * </p>
     *
     * @param page PathVariable로 받은 페이지 이름 (예: "admin_dashboard", "com_dashboard", "free_dashboard" 등)
     * @return 뷰 템플릿 경로 ("dashboard/" + page)
     */
    @GetMapping("/{page}")
    public String page(@PathVariable String page) {
        // 경로 변수 page 값에 따라 해당 뷰로 이동
        return "dashboard/" + page;
    }

    /**
     * 대시보드 상세 페이지 요청 처리 메서드.
     *
     * <p>예시:
     * - /dashboard/admin_dashboard_detail/123 → "dashboard/admin_dashboard_detail" 뷰 반환
     *   (id=123번 게시물의 상세 정보와 입찰 내역을 모델에 담아 렌더링)</p>
     *
     * <p>동작 설명:
     * 1. page가 "admin_dashboard_detail"인 경우에만 boardService와 bidService를 호출하여
     *    모델에 데이터를 추가(com.example.yellowaution.domain.Board, List&lt;Bid&gt;).
     * 2. 그 외 페이지명은 데이터 없이 뷰 이름만 반환.</p>
     *
     * @param page PathVariable로 받은 페이지 이름 (예: "admin_dashboard_detail")
     * @param id   PathVariable로 받은 게시물 식별자 (Long 타입)
     * @param model 뷰에 전달할 데이터를 담는 Model 객체
     * @return 뷰 템플릿 경로 ("dashboard/" + page)
     */
    @GetMapping("/{page}/{id}")
    public String page2(
            @PathVariable String page,
            @PathVariable Long id,
            Model model) {

        // 관리자 상세 페이지 요청인 경우에만 데이터 조회
        if ("admin_dashboard_detail".equals(page)) {
            // 게시물(id) 정보 조회하여 모델에 추가
            model.addAttribute("board", boardService.getById(id));
            // 해당 게시물에 대한 모든 입찰 내역 조회하여 모델에 추가
            model.addAttribute("bids", bidService.findAllForAdminByBoardId(id));
        }

        // 공통 반환: "dashboard/{page}" 뷰
        return "dashboard/" + page;
    }
}
