package com.example.security1.config.auth;

import com.example.security1.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 메소드.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 이 계정은 만료 안 되었는지 묻는 메소드. 즉 아직 유효하냐
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 이 계정은 잠기지 않았는가를 묻는 메소드. 즉 유효하냐
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 이 계정의 비밀번호가 오래되지 않았는지를 묻는 메소드. true면 아직 많이 안 지났다는 의미.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 이 계정이 활성화되었는지를 묻는 메소드.
    @Override
    public boolean isEnabled() {

        return true;
    }
}
