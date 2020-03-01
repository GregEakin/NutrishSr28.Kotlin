/*
 * Copyright (c) 2020. Greg Eakin
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
package dev.eakin.dao.dataValadition

import dev.eakin.dao.entities.NutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class FattyAcidTests internal constructor(private val session: Session) {
    @Test
    fun butyricTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "607")
        Assertions.assertEquals("4:0", nutrientDefinition.nutrDesc)
        Assertions.assertEquals("F4D0", nutrientDefinition.tagname)
        Assertions.assertEquals("g", nutrientDefinition.units)
    }

    @Test
    fun caproicTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "608")
        Assertions.assertEquals("6:0", nutrientDefinition.nutrDesc)
        Assertions.assertEquals("F6D0", nutrientDefinition.tagname)
        Assertions.assertEquals("g", nutrientDefinition.units)
    }

    @Test
    fun myristoleicTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "625")
        Assertions.assertEquals("14:1", nutrientDefinition.nutrDesc)
        Assertions.assertEquals("F14D1", nutrientDefinition.tagname)
        Assertions.assertEquals("g", nutrientDefinition.units)
    }

}