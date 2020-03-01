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

import dev.eakin.dao.entities.FoodDescription
import dev.eakin.dao.entities.NutrientData
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class FoodSearchTests internal constructor(private val session: Session) {
    @Test
    fun SortedQueryTest() {
        val foodDescription = session.load(FoodDescription::class.java, "01001")
        println(
            "Basic Report: " + foodDescription.nDB_No
                    + ", " + foodDescription.long_Desc
        )
        println("   Weight: Value per 100 g")
        val weightSet = foodDescription.getWeightSet()
        for (weight in weightSet) {
            println("   Weight: " + weight.msre_Desc + ", " + weight.amount + " x " + weight.gm_Wgt + " g")
        }
        val hql = ("select nds "
                + "from FoodDescription fd join fd.nutrientDataSet nds "
                + "where fd.NDB_No = :id "
                + "order by nds.nutrientDataKey.nutrientDefinition.SR_Order")
        val query =
            session.createQuery(hql, NutrientData::class.java)
        query.setParameter("id", "01001")
        val list = query.resultList

//        Set<NutrientData> nutrientDataSet = foodDescription.getNutrientDataSet();
//        Comparator<NutrientData> nutrientDataComparator = Comparator.comparingInt(o -> o.getNutrientDataKey().getNutrientDefinition().getSR_Order());
//        List<NutrientData> list = nutrientDataSet.stream().sorted(nutrientDataComparator).collect(Collectors.toList());
        Assertions.assertEquals(115, list.size)
        for (nutrientData in list) {
            val nutrientDataKey = nutrientData.nutrientDataKey
            val nutrientDefinition = nutrientDataKey!!.getNutrientDefinition()
            println(
                "   NutData: " + nutrientDefinition!!.sR_Order
                        + ", " + nutrientDefinition.nutrDesc
                        + " = " + nutrientData.nutr_Val
                        + " " + nutrientDefinition.units
            )
        }
    }

}