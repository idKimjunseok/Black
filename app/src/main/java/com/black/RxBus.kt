package com.black

import io.reactivex.subjects.PublishSubject
import java.util.*

object RxBus {

    private val subjectTable = Hashtable<String, PublishSubject<Any>>()

    fun sendEvent(any: Any, key: String = "Default") { subjectTable[key]?.onNext(any) }

    fun receiveEvent(key : String = "Default") : PublishSubject<Any>{
        synchronized(this) {
            if (subjectTable.containsKey(key).not()) {
                subjectTable[key] = PublishSubject.create()
            }
            return subjectTable[key]!!
        }
    }


}