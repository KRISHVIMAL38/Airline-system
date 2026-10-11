package com.zosh.controller;

import com.zosh.payload.request.AncillaryRequest;
import com.zosh.payload.response.AncillaryResponse;
import com.zosh.payload.response.ApiResponse;
import com.zosh.service.AncillaryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ancillaries")
public class AncillaryServiceController {
    @Autowired
    private AncillaryService ancillaryService;

    @PostMapping
    public ResponseEntity<AncillaryResponse> createAncillary(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody AncillaryRequest ancillaryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ancillaryService
                .createAncillary(userId,ancillaryRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AncillaryResponse> getAncillary(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ancillaryService.getAncillaryById(id));
    }

    @GetMapping
    public ResponseEntity<List<AncillaryResponse>> getAncillaryByAirlineId(
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(ancillaryService.getAncillaryByAirlineId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AncillaryResponse> updateAncillary(@PathVariable Long id,
                       @RequestBody AncillaryRequest ancillaryRequest) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ancillaryService.updateAncillary(id,ancillaryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAncillary(@PathVariable Long id) throws EntityNotFoundException {
        ancillaryService.deleteAncillary(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ancillary has been deleted successfully"));
    }

}
