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

@ExtendWith(NutrishRepositoryExtension::class)
class WeightTests internal constructor(private val session: Session) {

    //    @Test
    //    public void addNullFoodDescription() {
    //        FoodDescription foodDescription = createFoodDescription();
    //        Weight weight = createWeight(foodDescription);
    //
    //        Executable closureContainingCodeToTest = () -> weight.addFoodDescription(null);
    //        assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "null NutrientData");
    //    }

    @Test
    fun addFoodDescription() {
        val foodDescription = FoodDescriptionTests.createFoodDescription()
        val weight = createWeight(foodDescription)

        // weight.addFoodDescription(foodDescription);
        Assertions.assertSame(foodDescription, weight.weightKey!!.foodDescription)
        Assertions.assertTrue(foodDescription.getWeightSet().contains(weight))
    }

    //    @Test
    //    public void addNullNutrientDataTest() {
    //        FoodDescription foodDescription = createFoodDescription();
    //        Weight weight = createWeight(foodDescription);
    //
    //        Executable closureContainingCodeToTest = () -> weight.addNutrientData(null);
    //        assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "null NutrientData");
    //    }

    @Test
    fun addNutrientData() {
        val foodDescription = FoodDescriptionTests.createFoodDescription()
        val weight = createWeight(foodDescription)
        val nutrientDefinition = NutrientDefinitionTests.createNutrientDefinition()
        val nutrientData = NutrientDataTests.createNutrientData(foodDescription, nutrientDefinition)
        Assertions.assertSame(weight.weightKey!!.foodDescription, nutrientData.nutrientDataKey!!.foodDescription)
        // assertTrue(nutrientData.getWeightSet().contains(weight));
    }

    companion object {
        fun createWeight(foodDescription: FoodDescription): Weight {
            val weightKey = WeightKey(foodDescription, "00")
            val weight = Weight()
            weight.weightKey = weightKey
            foodDescription.addWeight(weight)
            return weight
        }
    }
}