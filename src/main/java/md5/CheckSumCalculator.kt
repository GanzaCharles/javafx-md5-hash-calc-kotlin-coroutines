package md5

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.experimental.and

class CheckSumCalculator {
    fun checksum(file: File): String {
        try {
            val fin = FileInputStream(file)
            val md5er = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(1024)
            var read: Int
            do {
                read = fin.read(buffer)
                if (read > 0)
                    md5er.update(buffer, 0, read)
            } while (read != -1)
            fin.close()
            val digest = md5er.digest() ?: return ""
            var strDigest = "0x"
            for (i in digest.indices) {
                strDigest += ((digest[i] and 0xff.toByte()) + 0x100).toString(16).substring(1).toUpperCase()
            }
            return BigInteger(1, digest).toString(16)
        } catch (e: Exception) {
            return ""
        }
    }
}