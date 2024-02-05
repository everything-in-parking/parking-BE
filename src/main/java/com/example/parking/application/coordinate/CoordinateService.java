package com.example.parking.application.coordinate;

import com.example.parking.application.coordinate.dto.CoordinateResponse;
import com.example.parking.application.coordinate.dto.CoordinateResponse.ExactLocation;
import com.example.parking.domain.parking.Location;
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

    private final RestTemplate restTemplate;

    @Autowired
    public CoordinateService(@Qualifier("coordinateRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Location extractLocationByAddress(String address, double longitude, double latitude) {
        UriComponents uriComponents = makeCompleteUri(address);
        ResponseEntity<CoordinateResponse> result = connect(uriComponents);

        if (isEmptyResultData(result)) {
            return new Location(longitude, latitude);
        }

        ExactLocation exactLocation = getExactLocation(result);
        return new Location(exactLocation.getLongitude(), exactLocation.getLatitude());
    }

    private ExactLocation getExactLocation(ResponseEntity<CoordinateResponse> result) {
        List<ExactLocation> exactLocations = result.getBody().getExactLocations();
        return exactLocations.get(0);
    }

    private ResponseEntity<CoordinateResponse> connect(UriComponents uriComponents) {
        return restTemplate.getForEntity(
                uriComponents.toString(),
                CoordinateResponse.class
        );
    }

    private UriComponents makeCompleteUri(String address) {
        return UriComponentsBuilder
                .fromHttpUrl(KAKAO_URL)
                .queryParam("query", address)
                .build();
    }

    private boolean isEmptyResultData(ResponseEntity<CoordinateResponse> result) {
        Integer matchingDataCount = result.getBody().getMeta().getTotalCount();
        return matchingDataCount == 0;
    }
}
