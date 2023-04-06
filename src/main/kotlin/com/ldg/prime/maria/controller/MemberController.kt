package com.ldg.prime.maria.controller

import com.ldg.prime.maria.dto.request.member.MemberDto
import com.ldg.prime.maria.master.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/members")
@RestController
class MemberController(private val memberService: MemberService) {

    @PostMapping("/signin")
    fun signin(@RequestBody memberDto: MemberDto) = ResponseEntity.ok().body(memberService.signin(memberDto))

}