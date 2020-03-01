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

import java.util.function.Predicate

class LazyList<T>(private val head: T, private val tail: () -> MyList<T>) : MyList<T> {
    override fun head(): T {
        return head
    }

    override fun tail(): MyList<T> {
        return tail()
    }

    override val isEmpty: Boolean
        get() = false

    override fun filter(p: Predicate<T>): MyList<T> {
        return when {
            isEmpty -> this
            p.test(head()) -> LazyList(head) { tail().filter(p) }
            else -> tail().filter(p)
        }
    }
}