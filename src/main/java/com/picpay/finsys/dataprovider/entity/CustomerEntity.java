package com.picpay.finsys.dataprovider.entity;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@Document(collection = "customer")
public class CustomerEntity implements UserDetails {
    @MongoId
    private String id;

    private String name;

    private String document;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    private CustomerStatus status;

    private String email;

    private String password;

    private LocalDateTime birthDate;

    private AddressEntity address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
