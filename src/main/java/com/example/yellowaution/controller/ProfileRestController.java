package com.example.yellowaution.controller;

import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.security.UserPrincipal;
import com.example.yellowaution.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * ProfileRestController 클래스는 REST API 방식으로 프로필(Profile) 관련
 * CRUD 기능을 제공합니다.
 *
 * <p>인증된 사용자(UserPrincipal)를 기반으로 본인 프로필만 조회・생성・수정・삭제하도록 설계되었습니다.</p>
 *
 * <p>매핑:
 * <ul>
 *   <li>GET    /api/profile       : 본인 프로필 리스트 조회 (일반적으로 하나)</li>
 *   <li>POST   /api/profile       : 본인 프로필 생성</li>
 *   <li>GET    /api/profile/{id}  : 특정 프로필 조회 (본인 소유 여부 검사)</li>
 *   <li>PUT    /api/profile/{id}  : 특정 프로필 수정 (본인 소유 여부 검사)</li>
 *   <li>DELETE /api/profile/{id}  : 특정 프로필 삭제 (본인 소유 여부 검사)</li>
 * </ul>
 * </p>
 *
 * <p>ApiResponse 객체로 일관된 JSON 응답 구조를 반환합니다.</p>
 */
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileRestController {

    /**
     * ProfileService는 프로필 관련 비즈니스 로직을 처리합니다.
     * - getList, create, get, update, delete 메서드 제공
     */
    private final ProfileService service;

    /**
     * 1) 본인 프로필 리스트를 조회합니다.
     *
     * @param principal 현재 인증된 사용자 정보(UserPrincipal)
     * @return ApiResponse: 본인 프로필 리스트(List<ProfileDto>)를 담은 응답 객체
     */
    @GetMapping
    public ApiResponse list(@AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(service.getList(principal));
    }

    /**
     * 2) 본인 프로필을 새로 생성합니다.
     *
     * @param dto       요청 바디로 전달된 프로필 정보(ProfileDto)
     * @param principal 현재 인증된 사용자 정보(UserPrincipal)
     * @return ApiResponse: 생성된 프로필(ProfileDto)을 담은 응답 객체
     */
    @PostMapping
    public ApiResponse create(
            @RequestBody ProfileDto dto,
            @AuthenticationPrincipal UserPrincipal principal) {

        return new ApiResponse(service.create(dto, principal));
    }

    /**
     * 3) 본인이 소유한 특정 프로필을 조회합니다.
     *
     * @param id        조회할 프로필의 고유 식별자
     * @param principal 현재 인증된 사용자 정보(UserPrincipal)
     * @return ApiResponse: 조회된 프로필(ProfileDto)을 담은 응답 객체
     */
    @GetMapping("/{id}")
    public ApiResponse get(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        return new ApiResponse(service.get(id, principal));
    }

    /**
     * 4) 본인이 소유한 특정 프로필을 수정합니다.
     *
     * @param id        수정할 프로필의 고유 식별자
     * @param dto       요청 바디로 전달된 업데이트할 프로필 정보(ProfileDto)
     * @param principal 현재 인증된 사용자 정보(UserPrincipal)
     * @return ApiResponse: 수정된 프로필(ProfileDto)을 담은 응답 객체
     */
    @PutMapping("/{id}")
    public ApiResponse update(
            @PathVariable Long id,
            @RequestBody ProfileDto dto,
            @AuthenticationPrincipal UserPrincipal principal) {

        return new ApiResponse(service.update(id, dto, principal));
    }

    /**
     * 5) 본인이 소유한 특정 프로필을 삭제합니다.
     *
     * @param id        삭제할 프로필의 고유 식별자
     * @param principal 현재 인증된 사용자 정보(UserPrincipal)
     * @return ApiResponse: 삭제 후 null 데이터를 담은 응답 객체
     */
    @DeleteMapping("/{id}")
    public ApiResponse delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        service.delete(id, principal);
        return new ApiResponse(null);
    }
}
