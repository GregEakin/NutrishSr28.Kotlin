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
package dev.eakin.dao.entities

import dev.eakin.dao.entities.FoodDescriptionTests.Companion.createFoodDescription
import dev.eakin.dao.entities.FootnoteTests.Companion.createFootnote
import dev.eakin.dao.entities.NutrientDataTests.Companion.createNutrientData
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(NutrishRepositoryExtension::class)
class NutrientDefinitionTests internal constructor(private val session: Session) {
    @Test
    fun addNullNutrientDataTest() {
        val nutrientDefinition =
            createNutrientDefinition()
        val closureContainingCodeToTest =
            Executable { nutrientDefinition.addNutrientData(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null NutrientData"
        )
    }

    @Test
    fun addNutrientDataTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition =
            createNutrientDefinition()
        val nutrientData: NutrientData = createNutrientData(foodDescription, nutrientDefinition)
        nutrientDefinition.addNutrientData(nutrientData)
        Assertions.assertTrue(nutrientDefinition.getNutrientDataSet().contains(nutrientData))
        Assertions
            .assertSame(nutrientDefinition, nutrientData.nutrientDataKey!!.getNutrientDefinition())
    }

    @Test
    fun addNullFootnoteTest() {
        val nutrientDefinition =
            createNutrientDefinition()
        val closureContainingCodeToTest =
            Executable { nutrientDefinition.addFootnote(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null Footnote"
        )
    }

    @Test
    fun addFootnoteTest() {
        val nutrientDefinition =
            createNutrientDefinition()
        val footnote: Footnote = createFootnote()
        nutrientDefinition.addFootnote(footnote)
        Assertions.assertSame(nutrientDefinition, footnote.getNutrientDefinition())
        Assertions.assertTrue(nutrientDefinition.getFootnoteSet().contains(footnote))
    }

    companion object {
        fun createNutrientDefinition(): NutrientDefinition {
            val nutrientDefinition = NutrientDefinition()
            nutrientDefinition.nutr_No = "000"
            return nutrientDefinition
        }
    }

}