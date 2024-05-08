package com.parkingcomestrue.common.domain.favorite;

import com.parkingcomestrue.common.domain.AuditingEntity;
import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.infra.converter.AssociationConverter;
import com.parkingcomestrue.common.support.Association;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"memberId", "parkingId"}
                )
        }
)
@Entity
public class Favorite extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AssociationConverter.class)
    private Association<Member> memberId;

    @Convert(converter = AssociationConverter.class)
    private Association<Parking> parkingId;

    public Favorite(Association<Member> memberId, Association<Parking> parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
