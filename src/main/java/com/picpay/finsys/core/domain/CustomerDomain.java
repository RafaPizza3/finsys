package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
public class CustomerDomain implements UserDetails {
    private String id;
    private String name;
    private String document;
    private LocalDateTime createdAt;
    private CustomerStatus status;
    private String email;
    private String password;
    private LocalDateTime birthDate;
    private AddressDomain address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
