package com.parkingcomestrue.external.api.parkingapi.korea;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.external.api.CircuitBreaker;
import com.parkingcomestrue.external.api.parkingapi.HealthCheckResponse;
import com.parkingcomestrue.external.api.parkingapi.ParkingApiService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class KoreaParkingApiService implements ParkingApiService {

    private static final String URL = "http://api.data.go.kr/openapi/tn_pubr_prkplce_info_api";
    private static final String RESULT_TYPE = "json";
    private static final int SIZE = 250;
    private static final String NORMAL_RESULT_CODE = "00";

    @Value("${korea-parking-key}")
    private String API_KEY;

    private final KoreaParkingAdapter adapter;
    private final RestTemplate restTemplate;

    public KoreaParkingApiService(KoreaParkingAdapter adapter,
                                  @Qualifier("parkingApiRestTemplate") RestTemplate restTemplate) {
        this.adapter = adapter;
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker
    public List<Parking> read(int pageNumber, int size) {
        ResponseEntity<KoreaParkingResponse> response = call(pageNumber, size);
        return adapter.convert(response.getBody());
    }

    @Override
    public int getReadSize() {
        return SIZE;
    }

    private ResponseEntity<KoreaParkingResponse> call(int pageNumber, int size) {
        URI uri = makeUri(pageNumber, size);
        return restTemplate.getForEntity(uri, KoreaParkingResponse.class);
    }

    private URI makeUri(int startIndex, int size) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return factory.builder()
                .queryParam("serviceKey", API_KEY)
                .queryParam("pageNo", startIndex)
                .queryParam("numOfRows", size)
                .queryParam("type", RESULT_TYPE)
                .build();
    }

    @Override
    public HealthCheckResponse check() {
        ResponseEntity<KoreaParkingResponse> response = call(1, 1);
        return new HealthCheckResponse(isHealthy(response), response.getBody().getResponse().getBody().getTotalCount());
    }

    private boolean isHealthy(ResponseEntity<KoreaParkingResponse> response) {
        return response.getStatusCode().is2xxSuccessful() && response.getBody().getResponse().getHeader()
                .getResultCode().equals(NORMAL_RESULT_CODE);
    }
}
