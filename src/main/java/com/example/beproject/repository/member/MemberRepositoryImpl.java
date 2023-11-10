package com.example.beproject.repository.member;

import com.example.beproject.domain.member.Member;
import com.example.beproject.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJPARepository memberJPARepository;

    @Override
    public Member save(Member member) {
        return memberJPARepository.save(MemberEntity.from(member)).toDTO();
    }

    // 모든 회원을 조회하여 Member 객체의 리스트로 반환
    @Override
    public List<Member> findAll() {
        List<MemberEntity> memberEntities = memberJPARepository.findAll();

        return memberEntities.stream()
                .map(MemberEntity::toDTO)
                .collect(Collectors.toList());
    }

    // 주어진 ID에 해당하는 회원을 조회하여 Optional<Member>로 반환
    @Override
    public Optional<Member> findById(long id) {
        Optional<MemberEntity> memberEntityOptional = memberJPARepository.findById(id);

        return memberEntityOptional
                .map(MemberEntity::toDTO);
    }

    @Override
    public Member findByEmail(String email) {
        MemberEntity member = memberJPARepository.findByEmail(email);
        if (member == null) {
            return null;
        } else {
            return member.toDTO();
        }
    }
}
