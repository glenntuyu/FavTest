package com.fav.favtest.utils

/**
 * Created by glenntuyu on 15/05/2024.
 */
internal infix fun Any?.shouldBe(expectedValue: Any?) {
    if (this != expectedValue) {
        throw AssertionError("$this should be $expectedValue")
    }
}

internal inline fun <reified T> Any?.shouldBeInstanceOf() {
    if (this !is T) {
        val actualClassName = if (this == null) "null" else this::class.simpleName
        val expectedClassName = T::class.simpleName

        throw AssertionError("$actualClassName should be instance of $expectedClassName")
    }
}

internal fun <A, E> List<A>.listShouldBe(expectedList: List<E>, compare: (A, E) -> Unit) {
    assert(size == expectedList.size) {
        "Size should be equal. Actual size = $size, Expected size: ${expectedList.size}."
    }

    expectedList.forEachIndexed { index, t -> compare(this[index], t) }
}