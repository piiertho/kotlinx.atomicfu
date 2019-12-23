package kotlinx.atomicfu

public actual fun Trace(size: Int, format: (Int, String) -> String): TraceBase = TraceBase.None