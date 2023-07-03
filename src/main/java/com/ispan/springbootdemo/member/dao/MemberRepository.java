package com.ispan.springbootdemo.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.ispan.springbootdemo.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>, JpaSpecificationExecutor<Member> {
	public Member findByAccount(String username);
}
