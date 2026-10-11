package com.zosh.service;

import com.zosh.payload.request.AncillaryRequest;
import com.zosh.payload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {
    AncillaryResponse createAncillary(Long userId,
                                      AncillaryRequest ancillaryRequest);

    AncillaryResponse getAncillaryById(Long id);
    List<AncillaryResponse> getAncillaryByAirlineId(Long userId);
    AncillaryResponse updateAncillary(Long id, AncillaryRequest request);
    void deleteAncillary(Long id);

}
