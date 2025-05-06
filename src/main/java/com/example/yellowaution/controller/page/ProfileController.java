package com.example.yellowaution.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ProfileController 클래스는 사용자 프로필 관련 페이지 요청을 처리하는 컨트롤러입니다.
 *
 * <ul>
 *   <li>프로필 조회, 수정 등 프로필 섹션의 여러 하위 페이지를 렌더링</li>
 *   <li>URL 패턴에 따라 동적으로 뷰 템플릿 경로를 결정</li>
 * </ul>
 *
 * <p>매핑:
 * <ul>
 *   <li>GET /profile/{page}         → profile/{page}.html</li>
 *   <li>GET /profile/{page}/{id}    → profile/{page}.html (id는 선택적 식별자로 활용 가능)</li>
 * </ul>
 * </p>
 */
@Controller
@RequestMapping("profile")  // "/profile"로 시작하는 모든 요청을 처리
public class ProfileController {

    /**
     * 프로필 관련 일반 페이지를 보여주는 핸들러 메서드입니다.
     *
     * <p>예시:
     * - "/profile/view"   → "profile/view" 뷰 반환
     * - "/profile/edit"   → "profile/edit" 뷰 반환
     * </p>
     *
     * @param page 요청된 서브 페이지 이름 (예: "view", "edit", "settings" 등)
     * @return 뷰 템플릿 경로 ("profile/" + page)
     */
    @GetMapping("/{page}")
    public String page(@PathVariable String page) {
        // PathVariable로 전달된 page 값에 해당하는 뷰를 렌더링
        return "profile/" + page;
    }

    /**
     * 프로필 상세 또는 특정 ID가 필요한 페이지를 보여줄 때 사용하는 핸들러입니다.
     *
     * <p>예시:
     * - "/profile/view/42" → "profile/view" 뷰 반환 (id=42번 사용자 프로필 조회)
     * </p>
     *
     * <p>추가 설명:
     * - id 값을 활용해 서비스에서 사용자 정보를 조회하고 Model에 담으려면
     *   Model 파라미터를 추가하고, UserService 등을 호출하도록 구현을 확장하세요.</p>
     *
     * @param page 요청된 서브 페이지 이름 (예: "view")
     * @param id   조회할 대상 식별자 (예: 사용자 ID)
     * @return 뷰 템플릿 경로 ("profile/" + page)
     */
    @GetMapping("/{page}/{id}")
    public String page2(
            @PathVariable String page,
            @PathVariable String id
    ) {
        return "profile/" + page;
    }
}
