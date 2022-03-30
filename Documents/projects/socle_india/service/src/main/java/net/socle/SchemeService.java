package net.socle;

import net.socle.dto.SchemeDto;
import net.socle.dto.request.GetDataRequestDto;
import net.socle.utils.ApiResponse;

public interface SchemeService {
    ApiResponse saveScheme(SchemeDto request);

    ApiResponse updateScheme(SchemeDto request);

    ApiResponse getAllSchemes(GetDataRequestDto requestDto);

    ApiResponse getSchemeById(Long id);

    ApiResponse deleteSchemeById(Long id);
}
