package net.socle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class LoginDto {

    private String mobile;

    private String password;

    private String email;

  //  private String otp;

    ///private String role;

  //  private String registeredToken;
}
