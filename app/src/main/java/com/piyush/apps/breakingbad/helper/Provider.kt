package com.piyush.apps.breakingbad.helper

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
data class Provider<out P>(val status : Status, val data : P?, val message : String?) {

    companion object {
        fun <P> success(data : P?) : Provider<P> {
            return Provider(Status.SUCCESS, data, null)
        }

        fun <P> error(data : P?, message : String) : Provider<P> {
            return Provider(Status.ERROR, data, message)
        }

        fun <P> loading(data : P?) : Provider<P> {
            return Provider(Status.LOADING, data, null)
        }
    }

}