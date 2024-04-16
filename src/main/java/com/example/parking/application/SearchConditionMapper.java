package com.example.parking.application;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import com.example.parking.support.exception.ClientException;
import com.example.parking.support.exception.ExceptionInformation;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SearchConditionMapper {

    public <E extends Enum<E> & SearchConditionAvailable> List<E> toEnums(Class<E> searchConditionAvailableClass,
                                                                          List<String> descriptions) {
        return descriptions.stream()
                .map(description -> toEnum(searchConditionAvailableClass, description))
                .toList();
    }

    public <E extends Enum<E> & SearchConditionAvailable> E toEnum(Class<E> searchConditionAvailableClass,
                                                                   String description) {
        E[] conditions = searchConditionAvailableClass.getEnumConstants();

        return Arrays.stream(conditions)
                .filter(condition -> description.startsWith(condition.getDescription()))
                .findAny()
                .orElseThrow(() -> new ClientException(ExceptionInformation.INVALID_DESCRIPTION));
    }

    public <E extends Enum<E> & SearchConditionAvailable> List<String> getValues(
            Class<E> searchConditionAvailableClass) {
        E[] conditions = searchConditionAvailableClass.getEnumConstants();

        return Arrays.stream(conditions)
                .filter(condition -> condition != condition.getDefault())
                .map(SearchConditionAvailable::getDescription)
                .toList();
    }
}
