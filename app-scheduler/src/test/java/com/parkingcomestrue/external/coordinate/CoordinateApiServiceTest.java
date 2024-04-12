package com.parkingcomestrue.external.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.parkingcomestrue.external.coordinate.dto.CoordinateResponse;
import com.parkingcomestrue.common.domain.parking.Location;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class CoordinateApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CoordinateApiService coordinateApiService;

    @Test
    void 주소_변환_요청_결과개수가_0이면_기존의_위도_경도를_반환한다() {
        CoordinateResponse emptyResponse = new CoordinateResponse(Collections.emptyList(), new CoordinateResponse.Meta(0));

        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(new ResponseEntity<>(emptyResponse, HttpStatus.OK));

        double expectedLongitude = 67;
        double expectedLatitude = 10;

        Location result = coordinateApiService.extractLocationByAddress("address", Location.of(67.0, 10.0));
        assertAll(
                () -> assertThat(result.getLatitude()).isEqualTo(expectedLatitude),
                () -> assertThat(result.getLongitude()).isEqualTo(expectedLongitude)
        );
    }
}
