package com.black.module

import com.black.util.MyLog
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 비어있는(length=0)인 Response를 받았을 경우 처리
 */
class NullOnEmptyConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<kotlin.Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L) {
                ""
            } else  {
                MyLog.e("responseBodyConverter " + it.contentLength())
                if (it is Array<*>) {
                    delegate.convert(it)
                } else {
                    delegate.convert(it)
                }
            }
        }

    }

}

