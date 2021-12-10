package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Data
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String uuid;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonManagedReference @JsonIgnore
    private List<GroceryList> groceryLists;

//    Tutorial said I'd need this.
//    @JsonCreator
//    User(@JsonProperty("userId") final String userId,
//         @JsonProperty("username") final String username,
//         @JsonProperty("password") final String password) {
//        super();
//        this.userId = Integer.parseInt(userId);
//        this.username = requireNonNull(username);
//        this.password = requireNonNull(password);
//    }

    @Override @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
