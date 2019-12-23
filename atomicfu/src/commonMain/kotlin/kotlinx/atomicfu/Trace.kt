@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package kotlinx.atomicfu

import kotlin.js.JsName
import kotlin.internal.InlineOnly

/**
 * Creates `Trace` object for tracing atomic operations.
 *
 * ### Usage
 *
 * Create a separate field for `Trace`:
 *
 * ```
 * val trace = Trace(size)
 * ```
 *
 * Using it to add trace messages:
 *
 * ```
 * trace { "Doing something" }
 * ```
 *
 * Pass it to `atomic` constructor to automatically trace all modifications of the corresponding field:
 *
 * ```
 * val a = atomic(initialValue, trace)
 * ```
 */
@Suppress("FunctionName")
@JsName("atomicfu\$Trace\$")
public expect fun Trace(size: Int = 32, format: (Int, String) -> String = { index, text -> "$index: $text" } ): TraceBase

public open class TraceBase internal constructor() {
    @JsName("atomicfu\$Trace\$append\$")
    @PublishedApi
    internal open fun append(text: String) {}

    @InlineOnly
    public inline operator fun invoke(text: () -> String) {
        append(text())
    }

    /**
     * NOP tracing.
     */
    public object None : TraceBase()
}