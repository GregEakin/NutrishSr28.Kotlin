/*
 * Copyright (c) 2019. Greg Eakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.eakin.dao.primes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.function.Predicate
import java.util.function.Supplier

class PrimeTests {
    @Test
    fun lazyListTest() {
        val numbers: MyList<Int> = from(2)
        assertEquals(2, numbers.head())
        assertEquals(3, numbers.tail().head())
        assertEquals(4, numbers.tail().tail().head())
    }

    @Test
    fun primeTest() {
        val numbers: MyList<Int> = from(2)
        assertEquals(2, primes(numbers).head())
        assertEquals(3, primes(numbers).tail().head())
        assertEquals(5, primes(numbers).tail().tail().head())
    }

    @Test
    fun printPrimes() {
        var numbers: MyList<Int> = primes(from(2))
        for (i in 0..19) {
            println(numbers.head())
            numbers = numbers.tail()
        }
    }

    companion object {
        fun from(n: Int): MyList<Int> {
            return LazyList(n, Supplier { from(n + 1) })
        }

        fun primes(numbers: MyList<Int>): MyList<Int> {
            return LazyList(
                numbers.head(),
                Supplier { primes(numbers.tail().filter(Predicate { n -> n % numbers.head() != 0 })) })
        }
    }
}