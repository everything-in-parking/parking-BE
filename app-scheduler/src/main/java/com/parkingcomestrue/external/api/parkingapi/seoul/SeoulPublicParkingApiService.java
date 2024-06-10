package com.parkingcomestrue.external.api.parkingapi.seoul;

import com.parkingcomestrue.external.api.CircuitBreaker;
import com.parkingcomestrue.external.api.HealthCheckResponse;
import com.parkingcomestrue.external.api.parkingapi.ParkingApiService;
import com.parkingcomestrue.common.domain.parking.Parking;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SeoulPublicParkingApiService implements ParkingApiService {

    private static final String URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_NAME = "GetParkingInfo";
    private static final String RESULT_TYPE = "json";
    private static final int SIZE = 1000;
    private static final String NORMAL_CODE = "INFO-000";

    @Value("${seoul-public-parking-key}")
    private String API_KEY;

    private final SeoulPublicParkingAdapter adapter;
    private final RestTemplate restTemplate;

    public SeoulPublicParkingApiService(SeoulPublicParkingAdapter adapter,
                                        @Qualifier("parkingApiRestTemplate") RestTemplate restTemplate) {
        this.adapter = adapter;
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker
    public List<Parking> read(int pageNumber, int size) {
        int startIndex = (pageNumber - 1) * size + 1;
        ResponseEntity<SeoulPublicParkingResponse> response = call(startIndex, startIndex + size - 1);
        return adapter.convert(response.getBody());
    }

    private ResponseEntity<SeoulPublicParkingResponse> call(int startIndex, int lastIndex) {
        URI uri = makeUri(startIndex, lastIndex);
        return restTemplate.getForEntity(uri, SeoulPublicParkingResponse.class);
    }

    private URI makeUri(int startIndex, int endIndex) {
        return UriComponentsBuilder
                .fromHttpUrl(URL)
                .pathSegment(API_KEY, RESULT_TYPE, API_NAME, String.valueOf(startIndex), String.valueOf(endIndex))
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public HealthCheckResponse check() {
        ResponseEntity<SeoulPublicParkingResponse> response = call(1, 1);
        return new HealthCheckResponse(isHealthy(response), SIZE * 2);
    }

    private boolean isHealthy(ResponseEntity<SeoulPublicParkingResponse> response) {
        return response.getStatusCode()
                .is2xxSuccessful() && response.getBody().getParkingInfo().getResult().getCode().equals(NORMAL_CODE);
    }

    @Override
    public int getReadSize() {
        return SIZE;
    }
}
