package core.lang
@Suppress("functionName","unused")
 fun Any.tag()=this::class.simpleName
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