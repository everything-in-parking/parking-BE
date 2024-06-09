package com.parkingcomestrue.external.api.parkingapi.pusan;

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
public class PusanPublicParkingApiService implements ParkingApiService {

    private static final String URL = "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo";
    private static final String RESULT_TYPE = "json";
    private static final int SIZE = 1000;
    private static String NORMAL_CODE = "00";

    @Value("${pusan-public-parking-key}")
    private String API_KEY;

    private final PusanPublicParkingAdapter adapter;
    private final RestTemplate restTemplate;

    public PusanPublicParkingApiService(PusanPublicParkingAdapter adapter,
                                        @Qualifier("parkingApiRestTemplate") RestTemplate restTemplate) {
        this.adapter = adapter;
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker
    public List<Parking> read(int pageNumber, int size) {
        PusanPublicParkingResponse response = call(pageNumber, size).getBody();
        return adapter.convert(response);
    }

    private ResponseEntity<PusanPublicParkingResponse> call(int pageNumber, int size) {
        URI uri = makeUri(pageNumber, size);
        return restTemplate.getForEntity(uri, PusanPublicParkingResponse.class);
    }

    private URI makeUri(int startIndex, int size) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return factory.builder()
                .queryParam("ServiceKey", API_KEY)
                .queryParam("pageNo", startIndex)
                .queryParam("numOfRows", size)
                .queryParam("resultType", RESULT_TYPE)
                .build();
    }

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public HealthCheckResponse check() {
        ResponseEntity<PusanPublicParkingResponse> response = call(1, 1);
        return new HealthCheckResponse(isHealthy(response),
                response.getBody().getGetParkingInfoDetails().getBody().getTotalCount());
    }

    private boolean isHealthy(ResponseEntity<PusanPublicParkingResponse> response) {
        return response.getStatusCode().is2xxSuccessful() && response.getBody().getGetParkingInfoDetails().getHeader()
                .getResultCode().equals(NORMAL_CODE);
    }

    @Override
    public int getReadSize() {
        return SIZE;
    }
}
