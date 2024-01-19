package com.oauth.sample.repository

import com.oauth.sample.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String) : Member?
    fun existsByEmail(email: String) : Boolean
}