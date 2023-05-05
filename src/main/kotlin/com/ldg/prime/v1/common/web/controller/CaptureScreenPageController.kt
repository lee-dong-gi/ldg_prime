package com.ldg.prime.v1.common.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/web/v1/capture-screen")
class CaptureScreenPageController {
    @GetMapping
    fun captureScreen(): String? {
        return "/views/captureScreen/captureScreen"
    }

    @GetMapping("/external")
    fun externalCaptureScreen(): String? {
        return "/views/captureScreen/externalCaptureScreen"
    }

}