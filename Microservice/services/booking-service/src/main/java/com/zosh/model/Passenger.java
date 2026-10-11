package com.zosh.model;

import com.zosh.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;
    private String phone;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nationality;
    private Long primaryUserId;

    @ManyToOne()
    private Booking booking;

    private Boolean isActive = true;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getAge() {
        return LocalDate.now().getYear()-dateOfBirth.getYear();
    }

    public Boolean isAdult(){
        return getAge()>=18;
    }
}
