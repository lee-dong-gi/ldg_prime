//package com.ldg.prime.maria.api
//
//import com.ldg.prime.maria.dto.request.member.SigninDto
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.http.ResponseEntity.status
//import org.springframework.transaction.annotation.Transactional
//
//@Transactional
//@AutoConfigureMockMvc
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll
//class MemberApiControllerTest {
//
//    @Test
//    @DisplayName("로그인 테스트")
//    fun `로그인 테스트 jwt 토큰발급 테스트` () {
//
//        val signinDto: SigninDto = SigninDto("test", "test")
//        val signinDtoJson = objectMapper.writeValueAsString(signinDto)
//
//        mockMvc.post("/api/members/signin")
//        {
//            contentType = MediaType.APPLICATION_JSON
//            content = signinDtoJson
//        }
//                .andExpect {
//                    status { isOk() }
//                }
//                .andDo {
//                    print()
//                }
//    }
//}