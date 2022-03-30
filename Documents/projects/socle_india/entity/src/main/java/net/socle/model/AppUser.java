package net.socle.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.socle.enums.UserRole;



import javax.persistence.*;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "mobile",unique = true)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

/*

    @Column(name = "user_name",unique = true)
    private String userName;

    @Column(name = "alternate_mobile")
    private String alternateMobile;

    @Column(name = "password")
    private String password;
*/

}
