package com.example.parking.application.searchcondition;

import com.example.parking.application.searchcondition.dto.SearchConditionDto;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.member.MemberRepository;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import com.example.parking.domain.searchcondition.FeeType;
import com.example.parking.domain.searchcondition.Hours;
import com.example.parking.domain.searchcondition.Priority;
import com.example.parking.domain.searchcondition.SearchCondition;
import com.example.parking.domain.searchcondition.SearchConditionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchConditionService {

    private final SearchConditionRepository searchConditionRepository;
    private final MemberRepository memberRepository;

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
        Member member = memberRepository.getById(memberId);
        return new SearchCondition(
                member,
                toEnums(searchConditionDto.getOperationType(), OperationType.values()),
                toEnums(searchConditionDto.getParkingType(), ParkingType.values()),
                toEnums(searchConditionDto.getFeeType(), FeeType.values()),
                toEnums(searchConditionDto.getPayType(), PayType.values()),
                SearchConditionAvailable.find(searchConditionDto.getPriority(), Priority.values()),
                Hours.from(searchConditionDto.getHours())
        );
    }

    public <E extends SearchConditionAvailable> List<E> toEnums(List<String> descriptions, E... values) {
        return descriptions.stream()
                .map(description -> SearchConditionAvailable.find(description, values))
                .toList();
    }
}
