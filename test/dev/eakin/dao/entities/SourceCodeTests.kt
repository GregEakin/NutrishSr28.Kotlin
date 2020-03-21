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
class SourceCodeTests internal constructor(private val session: Session) {
    @Test
    fun addNutrientDataTest() {
        val sourceCode =
            createSourceCode()
        val foodDescription = FoodDescriptionTests.createFoodDescription()
        val nutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = NutrientDataTests.createNutrientData(foodDescription, nutrientDefinition)
        sourceCode.addNutrientData(nutrientData)
        Assertions.assertSame(sourceCode, nutrientData.getSourceCode())
        Assertions.assertTrue(sourceCode.getNutrientDataSet().contains(nutrientData))
    }

    companion object {
        fun createSourceCode(): SourceCode {
            val sourceCode = SourceCode()
            sourceCode.src_Cd = "00"
            return sourceCode
        }
    }

}