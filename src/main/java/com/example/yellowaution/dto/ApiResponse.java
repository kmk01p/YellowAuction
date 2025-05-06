package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ApiResponse 클래스는 REST API 응답을 일관된 형식으로 감싸는 DTO(Data Transfer Object)입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>성공 여부(success), 메시지(message), 실제 데이터(data)를 하나의 객체로 묶어 반환</li>
 *   <li>프론트엔드에서 응답 처리 로직을 단순화하고, 에러 핸들링을 일원화</li>
 * </ul>
 * </p>
 *
 * <p>사용 예시:
 * <code>return new ApiResponse(true, "사용자 생성 성공");</code>
 * 또는
 * <code>return new ApiResponse(userList);</code>
 * 와 같이 생성자를 통해 바로 응답 객체 생성 가능</p>
 */
@Getter
@Setter
@NoArgsConstructor  // 파라미터 없는 기본 생성자 자동 생성 (직렬화·역직렬화 시 사용)
public class ApiResponse {

    /**
     * 요청 성공 여부를 나타냅니다.
     * - true: 요청이 정상 처리된 경우
     * - false: 요청 처리 중 예외 또는 검증 실패가 발생한 경우
     */
    private boolean success;

    /**
     * 상태 메시지 또는 에러 메시지를 담습니다.
     * - success=true일 때는 주로 "OK" 또는 간단한 정보 메시지
     * - success=false일 때는 실패 원인(유효성 검사 실패, 권한 오류 등) 메시지
     */
    private String message;

    /**
     * 실제 응답 데이터를 담는 필드입니다.
     * - 성공 시 필요한 객체나 리스트를 이 필드에 설정
     * - 실패 시 null이거나, 추가적인 에러 상세 정보를 담아도 무방
     */
    private Object data;

    /**
     * 데이터 전용 생성자입니다.
     *
     * <p>동작:
     * <ul>
     *   <li>success를 true로 설정</li>
     *   <li>message를 기본값 "OK"로 설정</li>
     *   <li>data에 전달된 객체를 저장</li>
     * </ul>
     * </p>
     *
     * @param data 실제 응답 데이터 객체
     */
    public ApiResponse(Object data) {
        this.success = true;
        this.message = "OK";
        this.data = data;
    }

    /**
     * success와 message를 직접 지정할 수 있는 생성자입니다.
     *
     * <p>동작:
     * <ul>
     *   <li>success와 message를 그대로 설정</li>
     *   <li>data는 null로 초기화 (에러 상황 등에서 사용)</li>
     * </ul>
     * </p>
     *
     * @param success 처리 결과 (true=성공, false=실패)
     * @param message 성공 또는 실패에 대한 설명 메시지
     */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }
}
