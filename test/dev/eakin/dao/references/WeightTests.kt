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
package dev.eakin.dao.references

import dev.eakin.dao.entities.FoodDescription
import dev.eakin.dao.entities.Weight
import dev.eakin.dao.entities.WeightKey
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class WeightTests internal constructor(private val session: Session) {

    //  Links to Food Description file by NDB_No
    @Test
    fun foodDescriptionTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val weightKey = WeightKey(foodDescription, "3 ")
        val weight =
            session.load(Weight::class.java, weightKey)
        Assertions.assertSame(foodDescription, weight.weightKey!!.foodDescription)
    }

    //  Links to Nutrient Data file by NDB_No
    @Test
    fun nutrientDataTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val weightKey = WeightKey(foodDescription, "3 ")
        val weight =
            session.load(Weight::class.java, weightKey)
        val nutrientDataSet =
            weight.weightKey!!.foodDescription!!.getNutrientDataSet()
        Assertions.assertEquals(91, nutrientDataSet.size)
    }

}