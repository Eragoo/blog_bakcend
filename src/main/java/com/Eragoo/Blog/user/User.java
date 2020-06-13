package com.Eragoo.Blog.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    private long id;
    private String login;
    private String password;
    private String username;
    private Role role;
}
