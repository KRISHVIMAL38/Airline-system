package com.zosh.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareRulesRequest {
    @NotBlank(message = "rule name is required")
    private String ruleName;

    @NotNull(message = "fare id is required")
    private Long fareId;

    private Long airlineId;
    private Boolean isRefundable;

    @PositiveOrZero(message = "change fee must be positive or zero")
    private Double changeFee;

    @PositiveOrZero(message = "cancellation fee must be positive or zero")
    private Double cancellationFee;

    @PositiveOrZero(message = "refund deadline days must be positive or zero")
    private Integer refundDeadlineDays;

    @PositiveOrZero(message = "dead line hours must be positive or zero")
    private Integer changeDeadlineHours;

    private Boolean isChangeable;
}
