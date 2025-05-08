package core.lang
@Suppress("functionName","unused")
object Logger {
    fun on(tag: String, msg: String) {
        _log(tag,msg)
    }
    fun off(tag: String, msg: String) {
        //_log(tag,msg)
    }


    private fun _log(tag: String, msg: String) {
        println("$tag::$msg")
    }

}