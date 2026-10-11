package com.zosh.payload.request;

import com.zosh.embeddable.ContactInfo;
import com.zosh.enums.CabinClassType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {

    @NotNull(message = "flight ID is required")
    private Long flightId;

    @NotNull(message = "flight instance ID is required")
    private Long flightInstanceId;

    @NotNull(message = "cabin class is required")
    private CabinClassType cabinClass;

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    @NotNull(message = "At least one passenger is required")
    @Size(min = 1, message = "At least one passenger is required")
    @Size
    private List<PassengerRequest> passengers;

    private ContactInfo contactInfo;
    private List<Long> ancillaryIds;

    private List<Long> mealIds;

    @NotNull
    private List<String> seatNumbers;


}
