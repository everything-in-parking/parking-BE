package com.parkingcomestrue.external.parkingapi.korea;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.external.parkingapi.ParkingApiService;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KoreaParkingApiService implements ParkingApiService {

    private static final String URL = "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo";
    private static final String RESULT_TYPE = "json";
    private static final int SIZE = 1000;
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
    public List<Parking> read() throws Exception {
        Set<KoreaParkingResponse> result = new HashSet<>();
        for (int pageNumber = 1; ; pageNumber++) {
            KoreaParkingResponse response = call(pageNumber, SIZE);
            String resultCode = response.getResponse().getHeader().getResultCode();
            if (NORMAL_RESULT_CODE.equals(resultCode)) {
                result.add(response);
                continue;
            }
            break;
        }
        return result.stream()
                .flatMap(response -> adapter.convert(response).stream())
                .toList();
    }

    private KoreaParkingResponse call(int startIndex, int size) {
        URI uri = makeUri(startIndex, size);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<KoreaParkingResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
                KoreaParkingResponse.class);
        return response.getBody();
    }

    private URI makeUri(int startIndex, int size) {
        return UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam("serviceKey", API_KEY)
                .queryParam("pageNo", startIndex)
                .queryParam("numOfRows", size)
                .queryParam("type", RESULT_TYPE)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
