package net.socle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 25-03-2022
 */
@Getter
@Setter
@NoArgsConstructor
public class SchemeDto {

    private Long id;

    private String schemeName;

    private String vision;

    private String eligibility;

    private String purpose;

    //private String schemeType;

    private FileDataDto fileDataDto ;

    private String searchKey;

    private Boolean isActive;
}
