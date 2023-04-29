package com.ldg.prime.v1.common.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/web/v1/chat")
class ChatPageController {
    @GetMapping
    fun chat(): String? {
        return "/views/chat/chat"
    }

}