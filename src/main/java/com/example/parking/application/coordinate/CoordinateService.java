package com.example.parking.application.coordinate;

import com.example.parking.application.coordinate.dto.CoordinateResponse;
import com.example.parking.application.coordinate.dto.CoordinateResponse.Document;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CoordinateService {

    private static final String KAKAO_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final Coordinate INVALID_COORDINATE = new Coordinate(0, 0);

    private final RestTemplate restTemplate;

    @Autowired
    public CoordinateService(@Qualifier("coordinateRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Coordinate extractCoordinateByAddress(String address) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(KAKAO_URL)
                .queryParam("query", address)
                .build();

        ResponseEntity<CoordinateResponse> result = restTemplate.getForEntity(
                uriComponents.toString(),
                CoordinateResponse.class
        );

        Integer matchingDataCount = result.getBody().getMeta().getTotalCount();
        if (matchingDataCount == 0) {
            return INVALID_COORDINATE;
        }

        List<Document> documents = result.getBody().getDocuments();
        Document document = documents.get(0);
        return new Coordinate(document.getX(), document.getY());
    }
}
