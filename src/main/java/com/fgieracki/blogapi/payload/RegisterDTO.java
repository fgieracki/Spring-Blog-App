package com.fgieracki.blogapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    @Length(min=6, max = 24)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 8, max = 64)
    private String password;
}
