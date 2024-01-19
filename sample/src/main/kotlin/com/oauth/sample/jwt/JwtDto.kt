package com.oauth.sample.jwt

data class JwtDto(
   val grantType: String,
   val accessToken: String,
   val refreshToken: String,
   val accessTokenExpiresIn: Long
) {
}