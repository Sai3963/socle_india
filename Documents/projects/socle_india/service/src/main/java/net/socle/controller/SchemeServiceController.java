package net.socle.controller;

import net.socle.SchemeService;
import net.socle.dto.SchemeDto;
import net.socle.dto.request.GetDataRequestDto;
import net.socle.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 25-03-2022
 */

@RestController
@RequestMapping("/api/v1/scheme")
public class SchemeServiceController {

    private final Logger log;

    {
        log = LoggerFactory.getLogger(SchemeServiceController.class);
    }

    @Autowired
    SchemeService schemeService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveScheme(@RequestBody SchemeDto request) {
        log.info("Save Scheme Service Started");
        ApiResponse apiResponse = schemeService.saveScheme(request);
        log.info("Save Scheme Service Completed");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateScheme(@RequestBody SchemeDto request) {
        log.info("Update Scheme Service Started");
        ApiResponse apiResponse = schemeService.updateScheme(request);
        log.info("Update Scheme Service Completed");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse> getAllSchemes(@RequestBody GetDataRequestDto requestDto) {
        log.info("Get All Schemes Service Started");
        ApiResponse apiResponse = schemeService.getAllSchemes(requestDto);
        log.info("Get All Schemes Service Completed");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSchemeById(@PathVariable Long id) {
        log.info("Get Scheme By Id Service Started");
        ApiResponse apiResponse = schemeService.getSchemeById(id);
        log.info("Get Scheme By Id Service Completed");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSchemeById(@PathVariable Long id) {
        log.info("Delete Scheme By Id Service Started");
        ApiResponse apiResponse = schemeService.deleteSchemeById(id);
        log.info("Delete Scheme By Id Service Completed");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

}