package com.zosh.controller;

import com.zosh.payload.request.FareRulesRequest;
import com.zosh.payload.response.ApiResponse;
import com.zosh.payload.response.FareRulesResponse;
import com.zosh.service.FareRulesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fare-rules")
public class FareRulesController {
    @Autowired
    private FareRulesService fareRulesService;

    @PostMapping
    public ResponseEntity<FareRulesResponse> createFareRule(
            @Valid @RequestBody FareRulesRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareRulesService.createFareRules(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareRulesService.getFareRulesById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(@PathVariable Long fareId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareRulesService.getFareRulesByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(@PathVariable Long airlineId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(@PathVariable Long id,
                                                             @RequestBody FareRulesRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fareRulesService.updateFareRules(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFareRules(@PathVariable Long id) throws Exception {
        fareRulesService.deleteFareRules(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Fare Rules deleted successfully"));
    }

}
