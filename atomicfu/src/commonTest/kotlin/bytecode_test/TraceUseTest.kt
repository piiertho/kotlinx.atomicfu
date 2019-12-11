package bytecode_test

import kotlinx.atomicfu.*
import kotlin.test.*

class TraceUseTest {
    val trace = Trace(size = 64) { index, text -> "$index:  $text" }
    val current = atomic(0, trace)

    @Test
    fun testTraceUse() {
        assertEquals(0, update(42))
        assertEquals(42, current.value)
    }

    fun update(x: Int): Int {
        // custom trace message
        trace { "calling update($x)" }
        // automatic tracing of modification operations
        return current.getAndAdd(x)
    }
}