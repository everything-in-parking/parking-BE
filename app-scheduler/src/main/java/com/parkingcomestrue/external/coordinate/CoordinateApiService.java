package com.parkingcomestrue.external.coordinate;

import com.parkingcomestrue.common.domain.parking.Location;
import com.parkingcomestrue.external.coordinate.dto.CoordinateResponse;
import com.parkingcomestrue.external.coordinate.dto.CoordinateResponse.ExactLocation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CoordinateApiService {

    private static final String KAKAO_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private final RestTemplate restTemplate;

    @Autowired
    public CoordinateApiService(@Qualifier("coordinateRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Location extractLocationByAddress(String address, Location location) {
        UriComponents uriComponents = makeCompleteUri(address);
        ResponseEntity<CoordinateResponse> result = connect(uriComponents);

        if (isEmptyResultData(result)) {
            return location;
        }

        ExactLocation exactLocation = getExactLocation(result);
        return Location.of(exactLocation.getLongitude(), exactLocation.getLatitude());
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
