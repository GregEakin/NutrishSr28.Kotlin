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

import dev.eakin.dao.entities.DataSourceTests.Companion.createDataSource
import dev.eakin.dao.entities.FoodDescriptionTests.Companion.createFoodDescription
import dev.eakin.dao.entities.SourceCodeTests.Companion.createSourceCode
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(NutrishRepositoryExtension::class)
class NutrientDataTests internal constructor(private val session: Session) {
    @Test
    fun addNullDataDerivationTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val closureContainingCodeToTest = Executable { nutrientData.addDataDerivation(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null DataDerivation"
        )
    }

    @Test
    fun addDataDerivationTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val dataDerivation = DataDerivationTests.createDataDerivation()
        nutrientData.addDataDerivation(dataDerivation)
        Assertions.assertSame(dataDerivation, nutrientData.dataDerivation)
        Assertions.assertTrue(dataDerivation.getNutrientDataSet().contains(nutrientData))
    }

    @Test
    fun addNullDataSourceTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val closureContainingCodeToTest = Executable { nutrientData.addDataSource(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null DataSource"
        )
    }

    @Test
    fun addDataSourceTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val dataSource: DataSource = createDataSource()
        nutrientData.addDataSource(dataSource)
        Assertions.assertTrue(nutrientData.dataSourceSet.contains(dataSource))
        Assertions.assertTrue(dataSource.getNutrientDataSet().contains(nutrientData))
    }

    //    @Test
    //    public void setNullRefFoodDescriptionTest() {
    //        FoodDescription foodDescription = createFoodDescription();
    //        NutrientDefinition nutrientDefinition = createNutrientDefinition();
    //        NutrientData nutrientData = crateNutrientData(foodDescription, nutrientDefinition);
    //
    //        Executable closureContainingCodeToTest = () -> language.addFoodDescription(null);
    //        Assertions.assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "null FoodDescription");
    //    }

    @Test
    fun setRefFoodDescriptionTest() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        nutrientData.refFoodDescription = foodDescription
        Assertions.assertSame(foodDescription, nutrientData.refFoodDescription)
    }

    @Test
    fun addNullSourceCode() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val closureContainingCodeToTest = Executable { nutrientData.addSourceCode(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null SourceCode"
        )
    }

    @Test
    fun addSourceCode() {
        val foodDescription: FoodDescription = createFoodDescription()
        val nutrientDefinition: NutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = createNutrientData(foodDescription, nutrientDefinition)
        val sourceCode: SourceCode = createSourceCode()
        nutrientData.addSourceCode(sourceCode)
        Assertions.assertSame(sourceCode, nutrientData.getSourceCode())
        Assertions.assertTrue(sourceCode.getNutrientDataSet().contains(nutrientData))
    }

    companion object {
        fun createNutrientData(
            foodDescription: FoodDescription,
            nutrientDefinition: NutrientDefinition
        ): NutrientData {
            val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
            val nutrientData = NutrientData()
            nutrientData.nutrientDataKey = nutrientDataKey
            return nutrientData
        }
    }
}