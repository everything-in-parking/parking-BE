package com.parkingcomestrue.external.parkingapi.seoul;

import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import com.parkingcomestrue.parking.domain.parking.Parking;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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
    public List<Parking> read() throws Exception {
        List<SeoulPublicParkingResponse> response = call();
        return response.stream()
                .map(adapter::convert)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<SeoulPublicParkingResponse> call() {
        List<SeoulPublicParkingResponse> result = new LinkedList<>();
        for (int i = 0; i < 2 * SIZE; i += SIZE) {
            URI uri = makeUri(String.valueOf(i), String.valueOf(i + SIZE - 1));

            ResponseEntity<SeoulPublicParkingResponse> response = restTemplate.getForEntity(uri,
                    SeoulPublicParkingResponse.class);
            result.add(response.getBody());
        }
        return Collections.unmodifiableList(result);
    }

    private URI makeUri(String startIndex, String endIndex) {
        return UriComponentsBuilder
                .fromHttpUrl(URL)
                .pathSegment(API_KEY, RESULT_TYPE, API_NAME, startIndex, endIndex)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }

    @Override
    public boolean offerCurrentParking() {
        return true;
    }
}
