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
package dev.eakin.dao.dataValadition

import dev.eakin.dao.entities.NutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class NutrientCountTests internal constructor(private val session: Session) {
    val Stuff = arrayOf(
        arrayOf("255", "Water", "WATER", "g", "8788"),
        arrayOf("208", "Energy", "ENERC_KCAL", "kcal", "8789"),
        arrayOf("211", "Glucose (dextrose)", "GLUS", "g", "1752"),
        arrayOf("204", "Total lipid (fat)", "FAT", "g", "8789")
    )

    @Test
    fun counterTest() {
        for (data in Stuff) {
            val nutrientDefinition =
                session.load(NutrientDefinition::class.java, data[0])
            Assertions.assertEquals(data[1], nutrientDefinition.nutrDesc)
            Assertions.assertEquals(data[2], nutrientDefinition.tagname)
            Assertions.assertEquals(data[3], nutrientDefinition.units)
            val nutrientDataSet = nutrientDefinition.getNutrientDataSet()
            Assertions.assertEquals(data[4].toInt(), nutrientDataSet.size)
        }
    }

}