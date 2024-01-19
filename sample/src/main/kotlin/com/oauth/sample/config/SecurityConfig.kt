package com.oauth.sample.config

import com.oauth.sample.service.CustomUserDetailService
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * 스프링 시큐리티 관련 설정입니다.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customUserDetailService: CustomUserDetailService
){

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        return http
            .csrf {
            it.disable()
        }
            .httpBasic {
                it.disable()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER) // #1
            }
            .oauth2Login {
                it.userInfoEndpoint { u -> u.userService(customUserDetailService)} // #2
                it.defaultSuccessUrl("/auth/login") // #3
                it.failureUrl("/fail")
            }.build()

    }

/*    *//**
     * OAuth2 설정입니다.
     *//*
    @Bean
    fun clientRegistrationRepository(oAuth2ClientProperties: OAuth2ClientProperties,
                                     customOAuth2ClientProperties: CustomOAuth2ClientProperties
    ): InMemoryClientRegistrationRepository {

        // 소셜 설정 등록
        val registrations = oAuth2ClientProperties.registration.keys
            .map { getRegistration(oAuth2ClientProperties, it) }
            .filter { it != null }
            .toMutableList()

        // 추가 설정 프로퍼티
        val customRegistrations = customOAuth2ClientProperties.registration

        // 추가 소셜 설정을 기본 소셜 설정에 추가
        for (customRegistration in customRegistrations) {

            when (customRegistration.key) {
                "kakao" -> registrations.add(
                    CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                    .clientId(customRegistration.value.clientId)
                    .clientSecret(customRegistration.value.clientSecret)
                    .jwkSetUri(customRegistration.value.jwkSetUri)
                    .build())
                "naver" -> registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                    .clientId(customRegistration.value.clientId)
                    .clientSecret(customRegistration.value.clientSecret)
                    .jwkSetUri(customRegistration.value.jwkSetUri)
                    .build())
            }

        }

        return InMemoryClientRegistrationRepository(registrations)
    }

    // 공통 소셜 설정을 호출합니다.
    private fun getRegistration(clientProperties: OAuth2ClientProperties, client: String): ClientRegistration? {
        val registration = clientProperties.registration[client]
        return when(client) {
            "google" -> CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(registration?.clientId)
                .clientSecret(registration?.clientSecret)
                .scope("email", "profile")
                .build()
            else -> null
        }
    }*/

}