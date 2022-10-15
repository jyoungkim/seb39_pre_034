package com.codestates.stackoverflowclone.member.service;

import com.codestates.stackoverflowclone.member.dto.MemberSignupDto;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Member signupMember(MemberSignupDto memberSignupDto) {
        Member member = new Member();
        member.setUsername(memberSignupDto.getUsername());
        member.setEmail(memberSignupDto.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(memberSignupDto.getPassword()));
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member getMember(String username) {
        Member getMember = memberRepository.findByUsername(username);
        if (!getMember.equals(null)) {
            return getMember;
        } else {
            throw new RuntimeException();
        }
    }
}
