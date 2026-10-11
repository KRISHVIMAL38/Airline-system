package com.zosh.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class SeatBenefits {
    private Boolean extraSeatSpace = false;
    private Boolean preferredSeatChoice = false;
    private Boolean advanceSeatSelection = false;
    private Boolean guaranteedSeatTogether = false;
}
