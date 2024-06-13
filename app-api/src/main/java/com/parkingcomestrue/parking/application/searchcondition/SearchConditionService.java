package com.parkingcomestrue.parking.application.searchcondition;

import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.parking.application.SearchConditionMapper;
import com.parkingcomestrue.parking.application.member.dto.MemberId;
import com.parkingcomestrue.parking.application.searchcondition.dto.SearchConditionDto;
import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.searchcondition.FeeType;
import com.parkingcomestrue.common.domain.searchcondition.Hours;
import com.parkingcomestrue.common.domain.searchcondition.Priority;
import com.parkingcomestrue.common.domain.searchcondition.SearchCondition;
import com.parkingcomestrue.common.domain.searchcondition.SearchConditionAvailable;
import com.parkingcomestrue.common.domain.searchcondition.repository.SearchConditionRepository;
import com.parkingcomestrue.common.support.Association;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchConditionService {

    private final SearchConditionRepository searchConditionRepository;
    private final SearchConditionMapper searchConditionMapper;

    public SearchConditionDto findSearchCondition(MemberId memberId) {
        SearchCondition searchCondition = searchConditionRepository.getByMemberId(Association.from(memberId.getId()));
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

    private <E extends SearchConditionAvailable> List<String> toDescriptions(Set<E> enums) {
        return enums.stream()
                .map(SearchConditionAvailable::getDescription)
                .sorted()
                .toList();
    }

    @Transactional
    public void updateSearchCondition(MemberId memberId, SearchConditionDto searchConditionDto) {
        SearchCondition newSearchCondition = createSearchCondition(Association.from(memberId.getId()), searchConditionDto);

        searchConditionRepository.findByMemberId(Association.from(memberId.getId())).ifPresentOrElse(
                existingSearchCondition -> existingSearchCondition.update(newSearchCondition),
                () -> searchConditionRepository.save(newSearchCondition)
        );
    }

    private SearchCondition createSearchCondition(Association<Member> memberId, SearchConditionDto searchConditionDto) {
        return new SearchCondition(
                memberId,
                searchConditionMapper.toEnums(OperationType.class, searchConditionDto.getOperationType()),
                searchConditionMapper.toEnums(ParkingType.class, searchConditionDto.getParkingType()),
                searchConditionMapper.toEnums(FeeType.class, searchConditionDto.getFeeType()),
                searchConditionMapper.toEnums(PayType.class, searchConditionDto.getPayType()),
                searchConditionMapper.toEnum(Priority.class, searchConditionDto.getPriority()),
                Hours.from(searchConditionDto.getHours())
        );
    }
}
