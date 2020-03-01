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

import dev.eakin.dao.entities.FoodDescriptionTests.Companion.createFoodDescription
import dev.eakin.dao.entities.NutrientDataTests.Companion.createNutrientData
import dev.eakin.dao.entities.NutrientDefinitionTests.Companion.createNutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(NutrishRepositoryExtension::class)
class DataDerivationTests internal constructor(private val session: Session) {
    @Test
    fun addNullNutrientDataTest() {
        val dataDerivation = createDataDerivation()
        val closureContainingCodeToTest =
            Executable { dataDerivation.addNutrientData(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null DataDerivation"
        )
    }

    @Test
    fun addNutrientDataTest() {
        val dataDerivation = createDataDerivation()
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = createNutrientDefinition()
        val nutrientData: NutrientData = createNutrientData(foodDescription, nutrientDefinition)
        dataDerivation.addNutrientData(nutrientData)
        Assertions.assertSame(dataDerivation, nutrientData.dataDerivation)
        Assertions.assertTrue(dataDerivation.getNutrientDataSet().contains(nutrientData))
    }

    companion object {
        fun createDataDerivation(): DataDerivation {
            val dataDerivation = DataDerivation()
            dataDerivation.deriv_Cd = "0000"
            return dataDerivation
        }
    }
}