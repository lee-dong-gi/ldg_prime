package com.ldg.prime.v1.common.web.socket

data class ChatMessage (
    var type: MessageType,
    var content: String?,
    var sender: String
)