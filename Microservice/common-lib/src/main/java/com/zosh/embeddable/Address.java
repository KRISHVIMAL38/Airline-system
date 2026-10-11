package com.zosh.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String street;
    private String postalCode;

}
