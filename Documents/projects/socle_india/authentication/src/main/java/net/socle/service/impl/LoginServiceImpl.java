package net.socle.service.impl;

import net.socle.dto.AppUserDto;
import net.socle.dto.ChangePasswordDto;
import net.socle.dto.LoginDto;
import net.socle.dto.response.LoginResponse;
import net.socle.dto.response.SendMessageDto;
import net.socle.enums.UserRole;
import net.socle.model.AppUser;
import net.socle.repo.AppUserRepo;
import net.socle.security.JwtUtil;
import net.socle.security.LoggedInUser;
import net.socle.service.LoginService;
import net.socle.utils.ApiResponse;
import net.socle.utils.CommonUtil;
import net.socle.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 22-03-2022
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    Environment environment;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtils;

    @Override
    public ApiResponse signUpUser(AppUserDto appUserDto) {
        AppUser appUser=dtoToModel(appUserDto);
        appUserRepo.save(appUser);
        return new ApiResponse(HttpStatus.OK, environment.getProperty("USER_SIGNUP_SUCCESS"));
    }

    private AppUser dtoToModel(AppUserDto request) {
        AppUser appUser=new AppUser();
        if(request.getId()!=null && request.getId()>0){
            appUser=appUserRepo.findById(request.getId()).orElse(new AppUser());
        }
        appUser.setFirstName(request.getFirstName());
        appUser.setLastName(request.getLastName());
        appUser.setMobile(request.getMobile());
        appUser.setEmail(request.getEmail());
       // appUser.setRole(UserRole.stringToEnum("ADMIN"));
        appUser.setRole(UserRole.stringToEnum("USER"));
        appUser.setSearchKey(getAppUserSearchKey(appUser));

        // appUser.setUserName(appUser.getFirstName()+" "+request.getLastName());
        //  appUser.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
         //  appUser.setAlternateContactNumber(request.getAlternateContactNumber());
        return appUser;
    }

    private String getAppUserSearchKey(AppUser appUser) {
        String searchKey = "";
        if (Validator.isValid(appUser.getId())) {
            searchKey = searchKey + appUser.getId();
        }
        if (Validator.isValid(appUser.getFirstName())) {
            searchKey = searchKey + appUser.getFirstName();
        }
        if (Validator.isValid(appUser.getLastName())) {
            searchKey = searchKey + appUser.getLastName();
        }
        if (Validator.isValid(appUser.getEmail())) {
            searchKey = searchKey + appUser.getEmail();
        }
        if (Validator.isValid(appUser.getMobile())) {
            searchKey = searchKey + appUser.getMobile();
        }
//           if (appUser.getUserRole() != null) {
//            searchKey = searchKey + appUser.getUserRole().name();
//        }
        return searchKey;
    }

    @Override
    public ApiResponse getOtp(LoginDto request) {

        if (request != null && request.getMobile() != null) {
            //Optional<AppUser> optionalAppUser = null;
                Optional<AppUser> optionalAppUser = appUserRepo.findByMobileAndIsActiveAndIsDeleted(request.getMobile(), Boolean.TRUE, Boolean.FALSE);
                if(optionalAppUser.isPresent()){
                SendMessageDto dto = new SendMessageDto();
                dto.setOtp(CommonUtil.generateOTP(6));
           //      dto.setUserName(optionalAppUser.get().getUserName());
                return new ApiResponse(HttpStatus.OK, environment.getProperty("OTP_SUCCESS"), dto.getOtp());

            } else {
                return new ApiResponse(HttpStatus.BAD_REQUEST, environment.getProperty("INVALID_MOBILE_NUMBER"));
            }
//            if (request.getEmail() != null) {
//                optionalAppUser = appUserRepo.findByEmailAndIsActiveAndIsDeleted(request.getEmail(), Boolean.TRUE, Boolean.FALSE);
//
//                SendMessageDto dto = new SendMessageDto();
//                dto.setOtp(CommonUtil.generateOTP(6));
//                 dto.setUserName(optionalAppUser.get().getUserName());
//                return new ApiResponse(HttpStatus.OK, "OTP Generated Successfully", dto.getOtp());
//            } else {
//                return new ApiResponse(HttpStatus.BAD_REQUEST, "Please Signup ");
//            }
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST, environment.getProperty("PLEASE_SIGNUP"));
    }

    @Override
    public ApiResponse validateOtp(LoginDto request) {
        return null;
    }


    @Override
    public ApiResponse loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getMobile(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(loggedInUser.getId());
        loginResponse.setFirstName(loggedInUser.getFirstName());
        loginResponse.setLastName(loggedInUser.getLastName());
        loginResponse.setContactNumber(loggedInUser.getContactNumber());
        loginResponse.setEmail(loggedInUser.getEmail());
        loginResponse.setUserRole(loggedInUser.getRole());
        loginResponse.setAccessToken(jwt);
        return new ApiResponse(HttpStatus.OK, environment.getProperty("LOGIN_SUCCESS"), loginResponse);
    }

    @Override
    public ApiResponse changePassword(ChangePasswordDto changePasswordDto) {
        ApiResponse apiResponse=validateChangePasswordRequest(changePasswordDto);
        if(Validator.isObjectValid(apiResponse)){
            return apiResponse;
        }
        LoggedInUser loggedInUser =getCurrentUserLogin();
        if (loggedInUser!=null) {
            Optional<AppUser> optionalAppUser = appUserRepo.findById(loggedInUser.getId());
            if (optionalAppUser.isPresent()) {
                AppUser appUser = optionalAppUser.get();
          //      appUser.setPassword(new BCryptPasswordEncoder().encode(changePasswordDto.getNewPassword()));
                appUserRepo.save(appUser);
                return new ApiResponse(HttpStatus.OK, "Password Changed Successfully");
            }else{
                return new ApiResponse(HttpStatus.BAD_REQUEST,"Logged in User Details Missing,please do a login again");
            }
        }else{
            return new ApiResponse(HttpStatus.BAD_REQUEST,"Please login to do this action");
        }
    }

    private ApiResponse validateChangePasswordRequest(ChangePasswordDto changePasswordDto) {
        if(!Validator.isObjectValid(changePasswordDto)){
            return new ApiResponse(HttpStatus.BAD_REQUEST,"Please Provide Valid Request");
        }
        if(!Validator.isValid(changePasswordDto.getNewPassword())){
            return new ApiResponse(HttpStatus.BAD_REQUEST,"Please Provide New Password");
        }
        if(!Validator.isValid(changePasswordDto.getConfirmPassword())){
            return new ApiResponse(HttpStatus.BAD_REQUEST,"Please Confirm Password");
        }
        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())){
            return new ApiResponse(HttpStatus.BAD_REQUEST,"Password and Confirm Password Should be same");
        }
        return null;
    }

    public static LoggedInUser getCurrentUserLogin() {
        LoggedInUser loggedInUser = null;
        Object loggedInUserResponse = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUserResponse != null && !loggedInUserResponse.equals("anonymousUser")) {
            loggedInUser = (LoggedInUser) loggedInUserResponse;
        }
        return loggedInUser;
    }

    @Override
    public ApiResponse logoutUser() {
        return new ApiResponse(HttpStatus.OK, "User Logged out Successfully");
    }

    @Override
    public ApiResponse getLoggedInUser() {
        Object loggedInUserResponse = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUserResponse != null && !loggedInUserResponse.equals("anonymousUser")) {
            System.out.println(loggedInUserResponse);
            LoginResponse loginResponse = new LoginResponse();
            LoggedInUser loggedInUser = (LoggedInUser) loggedInUserResponse;
            loginResponse.setId(loggedInUser.getId());
            loginResponse.setFirstName(loggedInUser.getFirstName());
            loginResponse.setLastName(loggedInUser.getLastName());

            loginResponse.setContactNumber(loggedInUser.getUsername());
            loginResponse.setEmail(loggedInUser.getEmail());
            //loginResponse.setUserRole(loggedInUser.getUserRole().name());
            return new ApiResponse(HttpStatus.OK, "Logged in User Details Found Successfully", loginResponse);
        } else {
            return new ApiResponse(HttpStatus.BAD_REQUEST, "Please login !!", null);
        }
    }

    @Override
    public ApiResponse updateUser(AppUserDto appUserDto) {
        return null;
    }


}
