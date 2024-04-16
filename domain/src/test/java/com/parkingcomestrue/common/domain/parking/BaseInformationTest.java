package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.parking.PayTypes;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseInformationTest {

    @Test
    void 결제_방식_포함_여부를_확인한다() {
        PayTypes defaultPayTypes = PayTypes.from(List.of(PayType.NO_INFO));

        Assertions.assertThat(defaultPayTypes.contains(List.of(PayType.CARD))).isTrue();
    }
}
