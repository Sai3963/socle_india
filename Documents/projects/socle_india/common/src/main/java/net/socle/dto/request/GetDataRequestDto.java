package net.socle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 25-03-2022
 */

@Getter
@Setter
@NoArgsConstructor
public class GetDataRequestDto {
        private Integer pageSize;
        private Integer pageNumber;
        private String searchKey;
        private Long filterKey;
        private String filterType;
        private String consultationType;
        private Boolean status = null;
        private String roleType;
        private String order;
        private String field;
}
