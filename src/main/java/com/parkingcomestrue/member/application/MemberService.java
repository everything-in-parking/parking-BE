package com.parkingcomestrue.member.application;

import com.parkingcomestrue.member.application.dto.MemberInfoResponse;
import com.parkingcomestrue.member.application.dto.MemberLoginRequest;
import com.parkingcomestrue.member.application.dto.MemberSignupRequest;
import com.parkingcomestrue.member.application.dto.PasswordChangeRequest;
import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.member.domain.MemberRepository;
import com.parkingcomestrue.member.domain.Password;
import com.parkingcomestrue.support.exception.ClientException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
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
            throw new ClientException(ExceptionInformation.DUPLICATE_MAIL);
        }
    }

    @Transactional(readOnly = true)
    public Long login(MemberLoginRequest dto) {
        Member member = findMemberByEmail(dto.getEmail());
        validatePassword(member, dto.getPassword());
        return member.getId();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException(ExceptionInformation.INVALID_EMAIL));
    }

    private void validatePassword(Member member, String password) {
        if (!member.checkPassword(password)) {
            throw new ClientException(ExceptionInformation.INVALID_PASSWORD);
        }
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
