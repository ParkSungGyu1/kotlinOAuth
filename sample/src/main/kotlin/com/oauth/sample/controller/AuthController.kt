package com.oauth.sample.controller

import com.oauth.sample.jwt.JwtDto
import com.oauth.sample.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    /**
     * token 생성해서 보내주기
     */
    @GetMapping("/login")
    fun login(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<JwtDto> {
        //이거 추가됨!!!
        return ResponseEntity.ok(authService.login(oAuth2User))
    }
}