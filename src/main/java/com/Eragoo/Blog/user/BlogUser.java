package com.Eragoo.Blog.user;

import com.Eragoo.Blog.role.Role;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "usr")
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    private String username;
    @ManyToOne
    private Role role;
}
