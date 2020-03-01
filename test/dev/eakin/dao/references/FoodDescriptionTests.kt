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
import dev.eakin.dao.entities.Language
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class FoodDescriptionTests internal constructor(private val session: Session) {

    //  Links to the Food Group Description file by the FdGrp_Cd field
    @Test
    fun foodGroupTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val foodGroup = foodDescription.getFoodGroup()
        Assertions.assertEquals("0100", foodGroup!!.fdGrp_Cd)
        Assertions.assertEquals("Dairy and Egg Products", foodGroup.fdGrp_Desc)
    }

    //  Links to the Nutrient Data file by the NDB_No field
    @Test
    fun NutrientDataTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDataSet = foodDescription.getNutrientDataSet()
        Assertions.assertEquals(91, nutrientDataSet.size)
    }

    //  Links to the Weight file by the NDB_No field
    @Test
    fun WeightTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val weightSet = foodDescription.getWeightSet()
        Assertions.assertEquals(3, weightSet.size)
    }

    //  Links to the Footnote file by the NDB_No field
    @Test
    fun FootnoteTest() {
        val foodDescription = session.load(FoodDescription::class.java, "05315")
        val footnoteSet = foodDescription.getFootnoteSet()
        Assertions.assertEquals(3, footnoteSet.size)
    }

    //  Links to the LanguaL Factor file by the NDB_No field
    @Test
    fun LanguageTest() {
        val foodDescription = session.load(FoodDescription::class.java, "02002")
        val languageSet: Set<Language> = foodDescription.getLanguageSet()
        Assertions.assertEquals(13, languageSet.size)
    }

}