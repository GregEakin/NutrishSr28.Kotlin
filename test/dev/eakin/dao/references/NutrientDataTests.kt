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
package dev.eakin.dao.references

import dev.eakin.dao.entities.*
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class NutrientDataTests internal constructor(private val session: Session) {

    //  Links to the Food Description file by Ref_NDB_No
    @Test
    fun foodDescriptionTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "204")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        Assertions.assertSame(nutrientDataKey, nutrientData.nutrientDataKey)
        Assertions.assertSame(foodDescription, nutrientData.nutrientDataKey!!.foodDescription)
    }

    //  Links to the Weight file by NDB_No
    //    @Test
    //    public void weightTest() {
    //        FoodDescription foodDescription = session.load(FoodDescription.class, "01119");
    //        NutrientDefinition nutrientDefinition = session.load(NutrientDefinition.class, "204");
    //        NutrientDataKey nutrientDataKey = new NutrientDataKey(foodDescription, nutrientDefinition);
    //        NutrientData nutrientData = session.load(NutrientData.class, nutrientDataKey);
    //
    //        Set<Weight> weightSet = nutrientData.getNutrientDataKey().getFoodDescription().getWeightSet();
    //        assertEquals(3, weightSet.size());
    //    }
    //  Links to the Footnote file by NDB_No

    @Test
    fun footnoteTest1() {
        val foodDescription = session.load(FoodDescription::class.java, "12040")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "204")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val footnoteSet = nutrientDataKey.foodDescription!!.getFootnoteSet()
        Assertions.assertEquals(2, footnoteSet.size)
        for (footnote in footnoteSet) {
            Assertions.assertEquals("12040", footnote.foodDescription!!.nDB_No)
            Assertions.assertNull(footnote.getNutrientDefinition())
        }
    }

    //  Links to the Footnote file by NDB_No and when applicable, Nutr_No
    @Test
    fun footnoteTest2() {
        val foodDescription = session.load(FoodDescription::class.java, "03073")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "320")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val hql = "FROM Footnote WHERE foodDescription.NDB_No = :ndb_no and nutrientDefinition.nutr_No = :nutr_no"
        val query = session.createQuery(hql, Footnote::class.java)
        query.setParameter("ndb_no", nutrientDataKey.foodDescription!!.nDB_No)
        query.setParameter("nutr_no", nutrientDataKey.getNutrientDefinition()!!.nutr_No)
        val footnote = query.singleResult
        Assertions.assertEquals("03073", footnote.foodDescription!!.nDB_No)
        Assertions.assertEquals("320", footnote.getNutrientDefinition()!!.nutr_No)
    }

    //  Links to the Sources of Data Link file by NDB_No and Nutr_No
    @Test
    fun DataSourceTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "313")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val dataSourceSet: Set<DataSource> = nutrientData.dataSourceSet
        Assertions.assertEquals(1, dataSourceSet.size)
    }

    //  Links to the Nutrient Definition file by Nutr_No
    @Test
    fun NutrientDefinitionTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "204")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        Assertions.assertSame(nutrientDataKey, nutrientData.nutrientDataKey)
        Assertions.assertSame(nutrientDefinition, nutrientData.nutrientDataKey!!.getNutrientDefinition())
    }

    //  Links to the Source Code file by Src_Cd
    @Test
    fun sourceCodeTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "317")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val sourceCode = nutrientData.getSourceCode()
        Assertions.assertEquals("4", sourceCode!!.src_Cd)
        Assertions.assertEquals("Calculated or imputed", sourceCode.srcCd_Desc)
    }

    //  Links to the Data Derivation Code Description file by Deriv_Cd
    @Test
    fun DataDerivationTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01119")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, "317")
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val dataDerivation = nutrientData.dataDerivation
        Assertions.assertEquals("BFNN", dataDerivation!!.deriv_Cd)
        Assertions.assertEquals(
            "Based on another form of the food or similar food; Concentration adjustment; Non-fat solids; Retention factors not used",
            dataDerivation.deriv_Desc
        )
    }
}