package kotlinx.atomicfu.test

import kotlinx.atomicfu.Trace
import kotlinx.atomicfu.atomic
import org.junit.Test
import kotlin.test.assertEquals

class TraceToStringTest {
    private val aTrace = Trace { i, text -> "[$i: $text]" }
    private val a = atomic(0, aTrace)

    @Test
    fun testTraceToStringAtomicInt() {
        a.value = 5
        a.compareAndSet(5, -2)
        a.lazySet(3)
        a.getAndIncrement()
        a.addAndGet(5)
        a.plusAssign(7)
        assertEquals(a.trace.toString(), "[1: value.set(5)], [2: value.CAS(5, -2)], [3: value.lazySet(3)], [4: value.getAndInc():3, value = 4], [5: value.addAndGet(5):9], [6: value.getAndAdd(7):9, value = 16]")
    }
}