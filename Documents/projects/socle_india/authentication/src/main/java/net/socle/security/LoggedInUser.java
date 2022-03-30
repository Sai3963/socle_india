package net.socle.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.socle.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Getter
@Setter
@NoArgsConstructor
public class LoggedInUser implements UserDetails {
    private Long id;

    private String contactNumber;

    private String firstName;

    private String lastName;

    private String role;

    private Boolean isActive;

    private AppUser appUser;

    private String email;

    @JsonIgnore
    private String password;

    //    public static LoggedInUser build(Map<Object,Object> loginData) {
//        LoggedInUser loggedInUser=new LoggedInUser();
//        loggedInUser.setId((Long) loginData.get("Id"));
//        loggedInUser.setContactNumber(loginData.get("ContactNumber").toString());
//        loggedInUser.setFirstName(loginData.get("FirstName").toString());
//        loggedInUser.setLastName(loginData.get("LastName").toString());
//        loggedInUser.setEmail(loginData.get("Email").toString());
//        loggedInUser.setPassword(loginData.get("Password").toString());
//        loggedInUser.setRole(loginData.get("Role").toString());
//        return loggedInUser;
//    }
    public static LoggedInUser builds(Optional<AppUser> loginData) {
        LoggedInUser loggedInUser = new LoggedInUser();
        if (loginData.isPresent()) {
            loggedInUser.setId((loginData.get().getId()));

            loggedInUser.setContactNumber(loginData.get().getMobile());
            loggedInUser.setFirstName(loginData.get().getFirstName());
            loggedInUser.setLastName(loginData.get().getLastName());
            loggedInUser.setEmail(loginData.get().getEmail());
            loggedInUser.setRole(String.valueOf(loginData.get().getRole()));
//            loggedInUser.setPassword(loginData.get().getPassword());
//            loggedInUser.setRole(loginData.get().getRoltoString());
        }
        return loggedInUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return contactNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
