package com.ldg.prime.maria.utils

import com.ldg.prime.maria.common.GlobalConstant.ENC_PADDING
import com.ldg.prime.maria.common.GlobalConstant.JWT_SECRET_KEY
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.GeneralSecurityException
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


@Component
class AES256Util : InitializingBean {
    /**
     * 16자리의 키값을 입력하여 객체를 생성
     */
    override fun afterPropertiesSet() {
        iv = JWT_SECRET_KEY.substring(0, 16)
        val keyBytes = ByteArray(16)
        val b: ByteArray = JWT_SECRET_KEY.toByteArray(StandardCharsets.UTF_8)
        var len = b.size
        if (len > keyBytes.size) {
            len = keyBytes.size
        }
        System.arraycopy(b, 0, keyBytes, 0, len)
        keySpec = SecretKeySpec(keyBytes, "AES")
    }

    companion object {
        private var iv: String? = null
        private var keySpec: Key? = null

        /**
         * AES256 으로 암호화
         *
         * @param str 암호화할 문자열
         * @return
         * @throws NoSuchAlgorithmException
         * @throws GeneralSecurityException
         * @throws UnsupportedEncodingException
         */
        @Throws(GeneralSecurityException::class)
        fun encrypt(str: String): String {
            val c = Cipher.getInstance(ENC_PADDING)
            c.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(iv!!.toByteArray()))
            val encrypted = c.doFinal(str.toByteArray(StandardCharsets.UTF_8))
            return String(Base64.encodeBase64(encrypted))
        }

        /**
         * AES256으로 암호화된 txt를 복호화
         *
         * @param str 복호화할 문자열
         * @return
         * @throws NoSuchAlgorithmException
         * @throws GeneralSecurityException
         * @throws UnsupportedEncodingException
         */
        @Throws(GeneralSecurityException::class)
        fun decrypt(str: String): String {
            val c = Cipher.getInstance(ENC_PADDING)
            c.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(iv!!.toByteArray()))
            val byteStr: ByteArray = Base64.decodeBase64(str.toByteArray())
            return String(c.doFinal(byteStr), StandardCharsets.UTF_8)
        }
    }
}
