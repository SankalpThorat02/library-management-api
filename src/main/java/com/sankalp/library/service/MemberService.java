package com.sankalp.library.service;

import com.sankalp.library.dto.MemberRequest;
import com.sankalp.library.entity.Member;
import com.sankalp.library.exception.MemberNotFoundException;
import com.sankalp.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member addMember(MemberRequest request) {
        Member member = new Member(
                request.getName(), request.getEmail()
        );

        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(int id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException("Member with ID: " + id + " not found"));
    }

    public void deleteMemberById(int id) {
        memberRepository.deleteById(id);
    }
}
