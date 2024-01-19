package com.oauth.sample.service

import com.oauth.sample.entity.Member
import com.oauth.sample.jwt.JwtDto
import com.oauth.sample.jwt.JwtProvider
import com.oauth.sample.repository.MemberRepository
import com.oauth.sample.role.AuthType
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun login(oAuth2User: OAuth2User) : JwtDto {
        //TODO: 1. 회원이 아니라면 회원 가입을 시켜준다.
        if(!memberRepository.existsByEmail(oAuth2User.attributes["email"] as String)) {
            val member = Member(
                email = oAuth2User.attributes["email"] as String,
                role = AuthType.ROLE_USER
            )
            memberRepository.save(member)
        }

        //TODO: 2. token 을 생성해준다.
        return jwtProvider.generateJwtDto(oAuth2User)
    }
}