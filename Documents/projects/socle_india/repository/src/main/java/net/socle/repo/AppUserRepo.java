package net.socle.repo;

import net.socle.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo  extends JpaRepository<AppUser,Long> {

     Optional<AppUser> findByMobileAndIsActiveAndIsDeleted(String mobile, Boolean aTrue, Boolean aFalse);



//   @Query("select a.id as Id,a.password as Password,a.firstName as FirstName,a.lastName as LastName,a.contactNumber as ContactNumber," +
//            "a.role.name as Role,a.email as Email from AppUser a where contactNumber=:contactNumber")
//   Optional<AppUser> getLoginData(@Param("contactNumber") String contactNumber);

    //For Mobile and Email
//   @Query("select a.id as Id,a.password as Password,a.firstName as FirstName,a.lastName as LastName,a.mobile as mobile ,a.email as Email  from AppUser a where a.email=:email or a.mobile=:mobile")
//            Optional<AppUser> getLoginData(@Param("mobile") String mobile,@Param("email") String email);


    Optional<AppUser> findByMobile(String contactNumber);

    Optional<AppUser> findByEmailAndIsActiveAndIsDeleted(String email, Boolean aTrue, Boolean aFalse);
}
