package com.example.yellowaution.security;

import com.example.yellowaution.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * UserPrincipal 클래스는 Spring Security의 UserDetails를 구현한 인증 정보(Principal) 객체입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>Authentication 객체에 저장될 사용자 정보 제공 (username, password 등)</li>
 *   <li>사용자의 권한(GrantedAuthority) 정보를 제공하여 인가(Authorization) 처리에 활용</li>
 * </ul>
 * </p>
 *
 * <p>Application의 User 엔티티를 래핑(wrap)하여 Spring Security가 요구하는
 * UserDetails 인터페이스를 구현합니다.</p>
 */
public class UserPrincipal implements UserDetails {

    /** 실제 사용자 정보를 담고 있는 도메인 객체 */
    private final User user;

    /**
     * UserPrincipal 생성자.
     *
     * @param user 인증 후 인증 컨텍스트에 저장할 User 도메인 객체
     */
    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * 내부에 래핑된 User 도메인 객체를 반환합니다.
     * 컨트롤러나 핸들러에서 세션 또는 SecurityContext에서
     * User 엔티티에 직접 접근할 때 사용 가능합니다.
     *
     * @return User 도메인 엔티티
     */
    public User getUser() {
        return user;
    }

    /**
     * 사용자의 권한 목록을 반환합니다.
     *
     * <p>여기서는 간단히 userType(예: "EMPLOYER", "FREELANCER", "ADMIN")을
     * GrantedAuthority로 변환하여 리스트로 리턴합니다.
     * 필요에 따라 Role과 Permission을 분리하거나
     * "ROLE_" 접두사를 붙여 관리할 수 있습니다.</p>
     *
     * @return Collection<? extends GrantedAuthority> 권한 리스트
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 람다 표현식: GrantedAuthority.getAuthority() 호출 시 user.getUserType() 반환
        return List.of(() -> user.getUserType());
    }

    /**
     * 계정의 암호를 반환합니다.
     * Spring Security 내부에서 인증 시 비밀번호 비교에 사용됩니다.
     *
     * @return 암호화된 비밀번호 문자열
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 계정의 사용자명을 반환합니다.
     * Spring Security 내부에서 식별자(username)로 사용됩니다.
     *
     * @return 사용자명(username)
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 계정 만료 여부를 반환합니다.
     *
     * @return true: 계정이 만료되지 않음 (사용 가능)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부를 반환합니다.
     *
     * @return true: 계정이 잠기지 않음 (사용 가능)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명(비밀번호) 만료 여부를 반환합니다.
     *
     * @return true: 자격 증명이 만료되지 않음 (사용 가능)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 여부를 반환합니다.
     *
     * @return true: 계정이 활성화 상태 (사용 가능)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
