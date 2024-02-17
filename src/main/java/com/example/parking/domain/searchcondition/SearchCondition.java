package com.example.parking.domain.searchcondition;

import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCondition {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn
        private Member member;

        private List<OperationType> operationTypes;
        private List<ParkingType> parkingTypes;
        private List<FeeType> feeTypes;
        private List<PayType> payTypes;

        @Enumerated(EnumType.STRING)
        private Priority priority;

        @Embedded
        private Hours hours;

        public SearchCondition(Member member, List<OperationType> operationTypes, List<ParkingType> parkingTypes,
                               List<FeeType> feeTypes, List<PayType> payTypes, Priority priority, Hours hours) {
                this.member = member;
                this.operationTypes = operationTypes;
                this.parkingTypes = parkingTypes;
                this.feeTypes = feeTypes;
                this.payTypes = payTypes;
                this.priority = priority;
                this.hours = hours;
        }

        public void update(SearchCondition updated) {
                this.operationTypes = updated.operationTypes;
                this.parkingTypes = updated.parkingTypes;
                this.feeTypes = updated.feeTypes;
                this.payTypes = updated.payTypes;
                this.priority = updated.priority;
                this.hours = updated.hours;
        }
}
