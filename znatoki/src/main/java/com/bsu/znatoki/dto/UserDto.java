package com.bsu.znatoki.dto;

import com.bsu.znatoki.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto {
    private int id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    private boolean activated;

    private Date createdAt;

    private Set<Role> roles;
}


