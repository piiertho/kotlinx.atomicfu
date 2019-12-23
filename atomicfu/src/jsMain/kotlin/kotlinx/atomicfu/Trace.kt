package kotlinx.atomicfu

@JsName("atomicfu\$Trace\$")
public actual fun Trace(size: Int, format: (Int, String) -> String): TraceBase = TraceBase.None