package net.socle.impl;

import net.socle.SchemeService;
import net.socle.dto.FileDataDto;
import net.socle.dto.SchemeDto;
import net.socle.dto.request.GetDataRequestDto;
import net.socle.enums.FileType;
import net.socle.enums.SchemeType;
import net.socle.exception.CustomException;
import net.socle.model.AppUser;
import net.socle.model.FileData;
import net.socle.model.Scheme;
import net.socle.repo.SchemeRepo;
import net.socle.utils.ApiResponse;
import net.socle.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Sai Regati
 * @version 1.0
 * @since 25-03-2022
 */

@Service
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    SchemeRepo schemeRepo;

    @Autowired
    Environment environment;

    @Override
    public ApiResponse saveScheme(SchemeDto request) {
//        LoggedInUser loggedInUser =getCurrentUserLogin();
//        if(loggedInUser.getRole().equals("ADMIN")){
//
//        }
        try {
//            ApiResponse apiResponse = validateSchemeRequest(request);
//            if (Validator.isObjectValid(request)) {
//                return apiResponse;
//            }
            Scheme model = dtoToEntity(request);
            schemeRepo.save(model);
            return new ApiResponse(HttpStatus.OK, environment.getProperty("SCHEME_SAVE_SUCCESS"));

        } catch (CustomException e) {
            return new ApiResponse(e.getHttpStatus(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("UN_HANDLED_ERROR_MESSAGE"));
        }

    }


    @Override
    public ApiResponse updateScheme(SchemeDto request) {
        if (request != null) {
            saveScheme(request);
            return new ApiResponse(HttpStatus.OK, environment.getProperty("USER_UPDATED_SUCCESS"));
        } else {
            return new ApiResponse(HttpStatus.BAD_REQUEST, environment.getProperty("USER_DOES_NOT_EXISTS"));
        }
    }



/*    private ApiResponse validateSchemeRequest(SchemeDto request) {
        if (!Validator.isObjectValid(request)) {
            return new ApiResponse(HttpStatus.BAD_REQUEST, environment.getProperty("INVALID_REQUEST"));
        }
        if (!Validator.isValid(request.getSchemeName())) {
            return new ApiResponse(HttpStatus.BAD_REQUEST, environment.getProperty("INVALID_SCHEME_NAME"));
        }
        return null;
    }*/

    @Override
    public ApiResponse getAllSchemes(GetDataRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPageNumber(), requestDto.getPageSize(), Sort.by("id").descending());
        Page<Scheme> schemePage = null;

        List<SchemeDto> schemeDto = new ArrayList<>();
        if(Validator.isValid(requestDto.getSearchKey())){
            schemePage = schemeRepo.findAllByIsActiveAndIsDeletedAndSearchKeyContainingIgnoreCase(Boolean.TRUE,Boolean.FALSE,pageable,requestDto.getSearchKey());
        }else {
            schemePage = schemeRepo.findAllByIsActiveAndIsDeleted(Boolean.TRUE,Boolean.FALSE,pageable);
        }
        if (Validator.isValid(schemePage.getContent())) {
            schemeDto = schemePage.getContent().stream().map(this::entityToDto).collect(Collectors.toList());
        }
        return new ApiResponse(HttpStatus.OK,  schemeDto,schemePage.getTotalPages());
    }


    @Override
    public ApiResponse getSchemeById(Long id) {
        Optional<Scheme> optionalScheme = schemeRepo.findById(id);
        SchemeDto schemeDto = new SchemeDto();
        if (optionalScheme.isPresent()) {
            Scheme scheme = optionalScheme.get();
            schemeDto = entityToDto(scheme);
        }
        return new ApiResponse(HttpStatus.OK, "Scheme Found Successfully By ID", schemeDto);
    }

    @Override
    public ApiResponse deleteSchemeById(Long id) {
        Optional<Scheme> optionalScheme = schemeRepo.findById(id);

        if (optionalScheme.isPresent()) {
            Scheme scheme = optionalScheme.get();
            scheme.setIsDeleted(Boolean.TRUE);
            schemeRepo.save(scheme);
        }
        return new ApiResponse(HttpStatus.OK, "Scheme Deleted Successfully");
    }

    private Scheme dtoToEntity(SchemeDto request) {
        Scheme scheme = new Scheme();
        if (Validator.isValid(request.getId())) {
            scheme = schemeRepo.findById(request.getId()).orElse(new Scheme());
        }
        scheme.setSchemeName(request.getSchemeName());
        scheme.setVision(request.getVision());
        scheme.setPurpose(request.getPurpose());
        scheme.setEligibility(request.getEligibility());
        // scheme.setSchemeType(SchemeType.stringToEnum(request.getSchemeType()));
        scheme.setSearchKey(getSchemeSearchKey(scheme));

        if (request.getFileDataDto() != null) {
            scheme.setFileUpload(dtoToEntity(request.getFileDataDto()));
        }
        return scheme;
    }

    private SchemeDto entityToDto(Scheme request) {
        SchemeDto schemeDto = new SchemeDto();
        if (request != null) {
            schemeDto.setId(request.getId());
            schemeDto.setSchemeName(request.getSchemeName());
            schemeDto.setPurpose(request.getPurpose());
            schemeDto.setVision(request.getVision());
            schemeDto.setEligibility(request.getEligibility());
            if (request.getFileUpload() != null) {
                schemeDto.setFileDataDto(entityToDto(request.getFileUpload()));
            }
        }
        return schemeDto;
    }

    private FileData dtoToEntity(FileDataDto request) {
        FileData data = new FileData();
        if (Validator.isObjectValid(request) && Validator.isValid(request.getFileId())) {

        }
        data.setFileId(request.getFileId());
        data.setFileName(request.getFileName());
        data.setFileContentType(request.getFileContentType());
        data.setFileSizeInBytes(request.getFileSizeInBytes());
        data.setFileType(FileType.FILE);
        return data;
    }

    private FileDataDto entityToDto(FileData request) {
        FileDataDto data = new FileDataDto();
        if (Validator.isObjectValid(request) && Validator.isValid(request.getFileId())) {

        }
        data.setFileId(request.getFileId());
        data.setFileName(request.getFileName());
        data.setFileContentType(request.getFileContentType());
        data.setFileSizeInBytes(request.getFileSizeInBytes());
        data.setFileType(String.valueOf(FileType.FILE));

        return data;
    }

    private String getSchemeSearchKey(Scheme scheme) {
        String searchKey = "";
        if (Validator.isValid(scheme.getId())) {
            searchKey = searchKey + scheme.getId();
        }
        if (Validator.isValid(scheme.getSchemeName())) {
            searchKey = searchKey + scheme.getSchemeName();
        }
        return searchKey;
    }
}