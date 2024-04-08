package com.parkingcomestrue.parking.domain.favorite;

import com.parkingcomestrue.parking.domain.AuditingEntity;
import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.support.Association;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "member_id"))
    private Association<Member> memberId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "parking_id"))
    private Association<Parking> parkingId;

    public Favorite(Association<Member> memberId, Association<Parking> parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
