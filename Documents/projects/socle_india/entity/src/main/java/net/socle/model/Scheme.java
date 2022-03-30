package net.socle.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.socle.enums.SchemeType;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 24-03-2022
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "schemes")
public class Scheme extends  BaseEntity {

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = " vision",columnDefinition = "text")
    private String vision;

    @Column(name = " eligibility",columnDefinition = "text")
    private String eligibility;

    @Column(name = " purpose",columnDefinition = "text")
    private String purpose;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData fileUpload;
}
