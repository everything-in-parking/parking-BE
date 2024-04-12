package com.parkingcomestrue.external.parkingapi.pusan;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
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
public class PusanPublicParkingApiService implements ParkingApiService {

    private static final String URL = "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo";
    private static final String RESULT_TYPE = "json";
    private static final int SIZE = 1000;

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
    public List<Parking> read() throws Exception {
        PusanPublicParkingResponse response = call(1, SIZE);
        return adapter.convert(response);
    }

    private PusanPublicParkingResponse call(int startIndex, int size) {
        URI uri = makeUri(startIndex, size);
        ResponseEntity<PusanPublicParkingResponse> response = restTemplate.getForEntity(uri,
                PusanPublicParkingResponse.class);
        return response.getBody();
    }

    private URI makeUri(int startIndex, int size) {
        return UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam("ServiceKey", API_KEY)
                .queryParam("pageNo", startIndex)
                .queryParam("numOfRows", size)
                .queryParam("resultType", RESULT_TYPE)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }

    @Override
    public boolean offerCurrentParking() {
        return true;
    }
}
