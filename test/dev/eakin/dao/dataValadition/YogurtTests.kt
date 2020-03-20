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

import dev.eakin.dao.entities.*
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class YogurtTests internal constructor(private val session: Session) {
    @Test
    fun foodDescriptionTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        Assertions.assertEquals("01119", foodDescription.nDB_No)
        Assertions
            .assertEquals("Yogurt, vanilla, low fat, 11 grams protein per 8 ounce", foodDescription.long_Desc)
        Assertions
            .assertEquals("YOGURT,VANILLA,LOFAT,11 GRAMS PROT PER 8 OZ", foodDescription.shrt_Desc)
        Assertions.assertEquals(3.87, foodDescription.cHO_Factor!!.toDouble())
        Assertions.assertEquals(4.27, foodDescription.pro_Factor!!.toDouble())
        Assertions.assertEquals(6.38, foodDescription.n_Factor!!.toDouble())
    }

    @Test
    fun foodGroupTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val foodGroup = foodDescription.getFoodGroup()
        Assertions.assertEquals("0100", foodGroup!!.fdGrp_Cd)
        Assertions.assertEquals("Dairy and Egg Products", foodGroup.fdGrp_Desc)
    }

    @Test
    fun foodDescriptionLoopbackTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDataSet = foodDescription.getNutrientDataSet()
        Assertions.assertEquals(91, nutrientDataSet.size)
        for (nutrientData in nutrientDataSet) {
            val nutrientDataKey = nutrientData.nutrientDataKey
            Assertions.assertSame(foodDescription, nutrientDataKey!!.foodDescription)
        }
    }

    @Test
    fun nutrientDefinitionLoopbackTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "204")
        val nutrientDataSet = nutrientDefinition.getNutrientDataSet()
        Assertions.assertEquals(8789, nutrientDataSet.size)
        for (nutrientData in nutrientDataSet) {
            val nutrientDataKey = nutrientData.nutrientDataKey
            Assertions.assertSame(nutrientDefinition, nutrientDataKey!!.getNutrientDefinition())
        }
    }

    @Test
    fun nutrientDefinitionTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "204")
        Assertions.assertEquals("204", nutrientDefinition.nutr_No)
        Assertions.assertEquals("Total lipid (fat)", nutrientDefinition.nutrDesc)
        Assertions.assertEquals("FAT", nutrientDefinition.tagname)
        Assertions.assertEquals("g", nutrientDefinition.units)
    }

    @Test
    fun nutrientDataTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "204")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        Assertions.assertSame(nutrientDataKey, nutrientData.nutrientDataKey)
        Assertions.assertEquals(1.25, nutrientData.nutr_Val)
        Assertions.assertNull(nutrientData.max)
        Assertions.assertNull(nutrientData.min)
        Assertions.assertNull(nutrientData.low_EB)
        Assertions.assertNull(nutrientData.up_EB)
    }

    @Test
    fun weightTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val weightSet = foodDescription.getWeightSet()
        Assertions.assertEquals(3, weightSet.size)
        for (weight in weightSet) {
            Assertions.assertEquals(1.0, weight.amount!!.toDouble())
            Assertions.assertNull(weight.std_Dev)
            when (weight.weightKey!!.seq) {
                "1 " -> {
                    Assertions.assertEquals(170.0, weight.gm_Wgt!!.toDouble())
                    Assertions.assertEquals("container (6 oz)", weight.msre_Desc)
                }
                "2 " -> {
                    Assertions.assertEquals(227.0, weight.gm_Wgt!!.toDouble())
                    Assertions.assertEquals("container (8 oz)", weight.msre_Desc)
                }
                "3 " -> {
                    Assertions.assertEquals(245.0, weight.gm_Wgt!!.toDouble())
                    Assertions.assertEquals("cup (8 fl oz)", weight.msre_Desc)
                }
            }
        }
    }

    @Test
    fun FoodDescriptionFootnoteTest() {
        val foodDescription = session.load(FoodDescription::class.java, "05315")
        val footnoteSet = foodDescription.getFootnoteSet()
        Assertions.assertEquals(3, footnoteSet.size)
        for (footnote in footnoteSet) println("    Footnote: " + footnote.footnt_Txt)
    }

    @Test
    fun nutrientDefinitionFootnoteTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "204")
        val footnoteSet: Set<Footnote> = nutrientDefinition.getFootnoteSet()
        Assertions.assertEquals(13, footnoteSet.size)
        val foodDescription = session.load(FoodDescription::class.java, "04673")
        Assertions.assertTrue(footnoteSet.stream().anyMatch { o: Footnote -> o.foodDescription == foodDescription })
        Assertions.assertTrue(
            footnoteSet.stream().map(Footnote::foodDescription)
                .anyMatch { obj: FoodDescription? -> foodDescription == obj })

//        Stream<FoodDescription> foodDescriptionStream = footnoteSet.stream().map(Footnote::getFoodDescription).filter(o -> o.getNDB_No() == "04673");
//        FoodDescription f2 = foodDescriptionStream.findFirst().get();
//        Assertions.assertSame(foodDescription, f2);
        for (footnote in footnoteSet) {
            println("    Footnote: " + footnote.footnt_Txt + " " + footnote.foodDescription!!.nDB_No)
            if (footnote.foodDescription!!.nDB_No == "04673") Assertions.assertEquals(
                "contains 2.841 g omega-3 fatty acids",
                footnote.footnt_Txt
            )
        }
    }

    @Test
    fun sourceCodeTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "204")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val sourceCode = nutrientData.getSourceCode()
        Assertions.assertEquals("1", sourceCode!!.src_Cd)
        Assertions.assertEquals("Analytical or derived from analytical", sourceCode.srcCd_Desc)
    }

    @Test
    fun dataDerivationTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "313")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val dataDerivation = nutrientData.dataDerivation
        Assertions.assertEquals("A", dataDerivation!!.deriv_Cd)
        Assertions.assertEquals("Analytical data", dataDerivation.deriv_Desc)
    }
}