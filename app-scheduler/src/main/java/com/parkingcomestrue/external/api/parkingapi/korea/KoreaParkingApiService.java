package com.parkingcomestrue.external.api.parkingapi.korea;

import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.external.api.AsyncApiExecutor;
import com.parkingcomestrue.external.api.parkingapi.ParkingApiService;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    public List<Parking> read() throws Exception {
        int pageNumber = 1;
        KoreaParkingResponse response = call(pageNumber++, SIZE).getBody();
        int lastPageNumber = response.getResponse().getBody().getTotalCount() / SIZE + 1;

        Set<KoreaParkingResponse> result = new HashSet<>(lastPageNumber);
        result.add(response);

        List<CompletableFuture<KoreaParkingResponse>> apis = Stream.iterate(pageNumber, i -> i <= lastPageNumber,
                        i -> i + 1)
                .map(i -> AsyncApiExecutor.executeAsync(() -> call(i, SIZE).getBody()))
                .toList();

        Set<KoreaParkingResponse> responses = apis.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());

        result.addAll(responses);

        return result.stream()
                .flatMap(koreaParkingResponse -> adapter.convert(koreaParkingResponse).stream())
                .toList();
    }

    private ResponseEntity<KoreaParkingResponse> call(int startIndex, int size) {
        URI uri = makeUri(startIndex, size);
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
}
