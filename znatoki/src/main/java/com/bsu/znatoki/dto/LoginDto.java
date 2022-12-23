package com.bsu.znatoki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
