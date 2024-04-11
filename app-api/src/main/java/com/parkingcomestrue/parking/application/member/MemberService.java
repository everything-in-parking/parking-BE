package com.parkingcomestrue.parking.application.member;

import com.parkingcomestrue.parking.application.member.dto.MemberInfoResponse;
import com.parkingcomestrue.parking.application.member.dto.MemberLoginRequest;
import com.parkingcomestrue.parking.application.member.dto.MemberSignupRequest;
import com.parkingcomestrue.parking.application.member.dto.PasswordChangeRequest;
import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.member.repository.MemberRepository;
import com.parkingcomestrue.parking.domain.member.Password;
import com.parkingcomestrue.parking.application.exception.ClientException;
import com.parkingcomestrue.parking.application.exception.ClientExceptionInformation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long signup(MemberSignupRequest dto) {
        Member member = new Member(
                dto.getEmail(),
                dto.getNickname(),
                new Password(dto.getPassword()));

        validateDuplicatedEmail(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new ClientException(ClientExceptionInformation.DUPLICATE_MAIL);
        }
    }

    @Transactional(readOnly = true)
    public Long login(MemberLoginRequest dto) {
        Member member = findMemberByEmail(dto.getEmail());
        member.validatePassword(dto.getPassword());
        return member.getId();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException(ClientExceptionInformation.INVALID_EMAIL));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.getById(memberId);
        member.delete();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse findMemberInfo(Long memberId) {
        Member member = memberRepository.getById(memberId);

        return new MemberInfoResponse(member.getNickname(), member.getEmail());
    }

    @Transactional
    public void changePassword(Long memberId, PasswordChangeRequest dto) {
        Member member = memberRepository.getById(memberId);
        String previousPassword = dto.getPreviousPassword();
        String newPassword = dto.getNewPassword();
        member.changePassword(previousPassword, newPassword);
    }
}
