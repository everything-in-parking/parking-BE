package com.example.parking.application.member;

import com.example.parking.application.member.dto.MemberLoginRequest;
import com.example.parking.application.member.dto.MemberNotFoundException;
import com.example.parking.application.member.dto.MemberSignupRequest;
import com.example.parking.application.member.dto.PasswordChangeRequest;
import com.example.parking.application.member.exception.MemberLoginException;
import com.example.parking.application.member.exception.MemberSignupException;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.member.MemberRepository;
import com.example.parking.domain.member.Password;
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
                dto.getName(),
                dto.getEmail(),
                dto.getNickname(),
                new Password(dto.getPassword()));

        validateDuplicatedEmail(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new MemberSignupException("중복된 이메일이라 회원가입이 불가능합니다.");
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
                .orElseThrow(() -> new MemberLoginException("회원가입되지 않은 유저입니다."));
    }

    private void validatePassword(Member member, String password) {
        if (!member.checkPassword(password)) {
            throw new MemberLoginException("비밀번호가 틀립니다.");
        }
    }

    @Transactional
    public void changePassword(Long memberId, PasswordChangeRequest dto) {
        Member member = findMemberById(memberId);
        String previousPassword = dto.getPreviousPassword();
        String newPassword = dto.getNewPassword();
        member.changePassword(previousPassword, newPassword);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원가입되지 않은 유저아이디입니다."));
    }
}
