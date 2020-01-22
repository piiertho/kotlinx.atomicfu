package kotlinx.atomicfu.test

import kotlinx.atomicfu.Trace
import kotlinx.atomicfu.atomic
import org.junit.Test
import kotlin.test.assertEquals

class TraceToStringTest {
    private val aTrace = Trace { i, text -> "[$i: $text]" }
    private val a = atomic(0, aTrace)

    private val shortTrace = Trace(4)
    private val s = atomic(0, shortTrace)

    @Test
    fun testTraceFormat() {
        repeat(3) { i ->
            aTrace { "Iteration $i started" }
            a.lazySet(i)
            aTrace { "Iteration $i ended" }
        }
        val trace = buildString {
            repeat(3) { i -> append("[${i * 3 + 1}: Iteration $i started]\n[${i * 3 + 2}: value.lazySet($i)]\n[${i * 3 + 3}: Iteration $i ended]\n") }
        }.dropLast(1)
        assertEquals(a.trace.toString(), trace)
    }

    @Test
    fun testTraceSequence() {
        s.value = 5
        s.compareAndSet(5, -2)
        s.lazySet(3)
        s.getAndIncrement()
        s.getAndAdd(7)
        assertEquals(s.trace.toString(), "5: value.getAndAdd(7):4, value = 11")
        s.getAndAdd(8)
        s.getAndAdd(9)
        assertEquals(s.trace.toString(), "5: value.getAndAdd(7):4, value = 11\n6: value.getAndAdd(8):11, value = 19\n7: value.getAndAdd(9):19, value = 28")
        s.lazySet(3)
        assertEquals(s.trace.toString(), "5: value.getAndAdd(7):4, value = 11\n6: value.getAndAdd(8):11, value = 19\n7: value.getAndAdd(9):19, value = 28\n8: value.lazySet(3)")
        s.getAndIncrement()
        s.getAndAdd(7)
        assertEquals(s.trace.toString(), "9: value.getAndInc():3, value = 4\n10: value.getAndAdd(7):4, value = 11")
    }
}