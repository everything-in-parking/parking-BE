package com.example.parking.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.parking.external.coordinate.CoordinateService;
import com.example.parking.external.coordinate.dto.CoordinateResponse;
import com.example.parking.external.coordinate.dto.CoordinateResponse.Meta;
import com.example.parking.domain.parking.Location;
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
class CoordinateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CoordinateService coordinateService;

    @Test
    void 주소_변환_요청_결과개수가_0이면_기존의_위도_경도를_반환한다() {
        CoordinateResponse emptyResponse = new CoordinateResponse(Collections.emptyList(), new Meta(0));

        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(new ResponseEntity<>(emptyResponse, HttpStatus.OK));

        double expectedLongitude = 67;
        double expectedLatitude = 10;

        Location result = coordinateService.extractLocationByAddress("address", Location.of(67.0, 10.0));
        assertAll(
                () -> assertThat(result.getLatitude()).isEqualTo(expectedLatitude),
                () -> assertThat(result.getLongitude()).isEqualTo(expectedLongitude)
        );
    }
}
