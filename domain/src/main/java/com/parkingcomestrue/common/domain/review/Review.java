package com.parkingcomestrue.common.domain.review;

import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.infra.converter.AssociationConverter;
import com.parkingcomestrue.common.infra.converter.ContentConverter;
import com.parkingcomestrue.common.support.Association;
import com.parkingcomestrue.common.support.exception.DomainException;
import com.parkingcomestrue.common.support.exception.DomainExceptionInformation;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = @Index(name = "review_ux_parking_id_reviewer_id", columnList = "parking_id, reviewer_id", unique = true))
public class Review {

    private static final int MAX_CONTENTS_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AssociationConverter.class)
    private Association<Parking> parkingId;

    @Convert(converter = AssociationConverter.class)
    private Association<Member> reviewerId;

    @Convert(converter = ContentConverter.class)
    private Set<Content> contents;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Review(Association<Parking> parkingId, Association<Member> reviewerId, Set<Content> contents) {
        validate(contents);
        this.parkingId = parkingId;
        this.reviewerId = reviewerId;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    private static void validate(Set<Content> contents) {
        if (contents == null || contents.isEmpty() || contents.size() > MAX_CONTENTS_SIZE) {
            throw new DomainException(DomainExceptionInformation.INVALID_CONTENTS_SIZE);
        }
    }
}
