package com.oauth.sample.entity

import com.oauth.sample.role.AuthType
import jakarta.persistence.*

@Entity
class Member(
    @Id @GeneratedValue
    var id: Long = 0,

    var email: String,

    @Enumerated(EnumType.STRING)
    var role: AuthType
) {
}