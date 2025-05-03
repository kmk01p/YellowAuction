// ApiResponse.java
package com.example.yellowaution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ApiResponse {
    // 요청 성공 여부
    private boolean success;
    // 에러 메시지 또는 상태 메시지
    private String message;
    // 실제 응답 데이터
    private Object data;

    // 1) 데이터 전용 생성자: 성공(true) + message는 "OK"로 기본 설정
    public ApiResponse(Object data) {
        this.success = true;
        this.message = "OK";
        this.data = data;
    }

    // 2) 기존 생성자(필요시 에러 상황에 사용)
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }
}
