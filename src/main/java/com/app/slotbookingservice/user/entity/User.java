package com.app.slotbookingservice.user.entity;

import com.app.slotbookingservice.common.AbstractEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document(collection = "User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    private String name;
    private String email;
    private Long phoneNumber;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Not considering role based approach for now.
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
