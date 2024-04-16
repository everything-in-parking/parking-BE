package com.parkingcomestrue.external.coordinate;

import com.parkingcomestrue.external.support.exception.SchedulerException;
import com.parkingcomestrue.external.support.exception.SchedulerExceptionInformation;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
public class CoordinateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.warn("fail while extracting coordinate by address code: {}", response.getStatusCode());
        throw new SchedulerException(SchedulerExceptionInformation.COORDINATE_EXCEPTION);
    }
}
