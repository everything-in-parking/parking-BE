package com.example.parking.application.searchcondition;

import com.example.parking.application.SearchConditionMapper;
import com.example.parking.application.searchcondition.dto.SearchConditionDto;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.searchcondition.FeeType;
import com.example.parking.domain.searchcondition.Hours;
import com.example.parking.domain.searchcondition.Priority;
import com.example.parking.domain.searchcondition.SearchCondition;
import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import com.example.parking.domain.searchcondition.SearchConditionRepository;
import com.example.parking.support.Association;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchConditionService {

    private final SearchConditionRepository searchConditionRepository;
    private final SearchConditionMapper searchConditionMapper;

    public SearchConditionDto findSearchCondition(Long memberId) {
        SearchCondition searchCondition = searchConditionRepository.getByMemberId(memberId);
        return toSearchConditionDto(searchCondition);
    }

    private SearchConditionDto toSearchConditionDto(SearchCondition searchCondition) {
        return new SearchConditionDto(
                toDescriptions(searchCondition.getOperationTypes()),
                toDescriptions(searchCondition.getParkingTypes()),
                toDescriptions(searchCondition.getFeeTypes()),
                toDescriptions(searchCondition.getPayTypes()),
                searchCondition.getPriority().getDescription(),
                searchCondition.getHours().getHours()
        );
    }

    private <E extends SearchConditionAvailable> List<String> toDescriptions(List<E> enums) {
        return enums.stream()
                .map(SearchConditionAvailable::getDescription)
                .toList();
    }

    @Transactional
    public void updateSearchCondition(Long memberId, SearchConditionDto searchConditionDto) {
        SearchCondition newSearchCondition = createSearchCondition(memberId, searchConditionDto);

        searchConditionRepository.findByMemberId(memberId).ifPresentOrElse(
                existingSearchCondition -> existingSearchCondition.update(newSearchCondition),
                () -> searchConditionRepository.save(newSearchCondition)
        );
    }

    private SearchCondition createSearchCondition(Long memberId, SearchConditionDto searchConditionDto) {
        return new SearchCondition(
                Association.from(memberId),
                searchConditionMapper.toEnums(OperationType.class, searchConditionDto.getOperationType()),
                searchConditionMapper.toEnums(ParkingType.class, searchConditionDto.getParkingType()),
                searchConditionMapper.toEnums(FeeType.class, searchConditionDto.getFeeType()),
                searchConditionMapper.toEnums(PayType.class, searchConditionDto.getPayType()),
                searchConditionMapper.toEnum(Priority.class, searchConditionDto.getPriority()),
                Hours.from(searchConditionDto.getHours())
        );
    }
}
