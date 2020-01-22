@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package kotlinx.atomicfu

import java.util.concurrent.atomic.AtomicInteger
import kotlin.internal.InlineOnly

@Suppress("FunctionName")
@InlineOnly
public actual fun Trace(size: Int, format: (Int, String) -> String): TraceBase =
    TraceImpl(size, format)

private class TraceImpl(size: Int, val format: (Int, String) -> String) : TraceBase() {
    init { require(size >= 1) }
    private val size = ((size shl 1) - 1).takeHighestOneBit() // next power of 2
    private val mask = this.size - 1
    private val trace = arrayOfNulls<String>(this.size)
    private val index = AtomicInteger(0)

    override fun append(text: String) {
        val i = index.getAndIncrement()
        trace[i and mask] = format(index.get(), text)
    }

    override fun toString(): String = trace.slice(0 .. ((index.get() - 1) and mask)).joinToString("\n")
}