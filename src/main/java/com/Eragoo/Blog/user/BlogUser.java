package com.Eragoo.Blog.user;

import com.Eragoo.Blog.role.Role;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "usr")
public class BlogUser {
    @Id
    private long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String username;
    @ManyToOne
    @NonNull
    private Role role;
}
