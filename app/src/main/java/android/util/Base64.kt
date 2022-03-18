package android.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64

//Mock for base64 algorithm test
object Base64 {

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun decode(str: String?, flags: Int): ByteArray {
        return Base64.getDecoder().decode(str)
    }

    const val DEFAULT = 0

}