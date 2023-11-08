package com.example.beproject.repository.member;

import com.example.beproject.domain.member.Member;
import com.example.beproject.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJPARepository memberJPARepository;

    @Override
    public Member save(Member member) {
        return memberJPARepository.save(MemberEntity.from(member)).toDTO();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Member findByEmail(String email) {
        MemberEntity member = memberJPARepository.findByEmail(email);
        return member.toDTO();
    }
}
