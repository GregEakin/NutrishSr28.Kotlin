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
package dev.eakin.dao.entities

import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(NutrishRepositoryExtension::class)
class FootnoteTests internal constructor(private val session: Session) {
    @Test
    fun addNutrientDefinitionTest() {
        val footnote = createFootnote()
        val nutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        footnote.addNutrientDefinition(nutrientDefinition)
        Assertions.assertSame(nutrientDefinition, footnote.getNutrientDefinition())
        Assertions.assertTrue(nutrientDefinition.getFootnoteSet().contains(footnote))
    }

    @Test
    fun addFoodDescriptionTest() {
        val footnote = createFootnote()
        val foodDescription = FoodDescriptionTests.createFoodDescription()
        foodDescription.addFootnote(footnote)
        Assertions.assertSame(foodDescription, footnote.foodDescription)
    }

    companion object {
        fun createFootnote(): Footnote {
            return Footnote()
        }
    }
}