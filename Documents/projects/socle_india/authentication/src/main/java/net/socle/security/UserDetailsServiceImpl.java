package net.socle.security;

import net.socle.model.AppUser;
import net.socle.utils.Validator;
import net.socle.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    AppUserRepo appUserRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String contactNumber) throws UsernameNotFoundException {
        //Map<Object,Object> loginData=appUserRepo.getLoginData(contactNumber);
        Optional<AppUser> optionalAppUser=appUserRepo.findByMobile(contactNumber);
       // Optional<AppUser> optionalAppUser=appUserRepo.getLoginData(contactNumber,contactNumber);
//  || Validator.isValid(optionalAppUser.get().getEmail())

        if(Validator.isValid(optionalAppUser.get().getMobile()) ){
            return LoggedInUser.builds(optionalAppUser);
        }else{
            throw new UsernameNotFoundException("User Not Found with Contact Number: " + contactNumber);
        }
    }
}


