package net.socle.service;

import net.socle.dto.AppUserDto;
import net.socle.dto.ChangePasswordDto;
import net.socle.dto.LoginDto;
import net.socle.utils.ApiResponse;

public interface LoginService {
    ApiResponse signUpUser(AppUserDto appUserDto);

    ApiResponse loginUser(LoginDto loginDto);

    ApiResponse changePassword(ChangePasswordDto changePasswordDto);

    ApiResponse logoutUser();

    ApiResponse getLoggedInUser();

    ApiResponse updateUser(AppUserDto appUserDto);

    ApiResponse getOtp(LoginDto request);

    ApiResponse validateOtp(LoginDto request);
}
