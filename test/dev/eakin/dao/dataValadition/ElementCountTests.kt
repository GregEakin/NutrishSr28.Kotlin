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

import dev.eakin.dao.entities.NutrientData
import dev.eakin.dao.entities.NutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class ElementCountTests internal constructor(private val session: Session) {
    @Test
    fun waterTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "255")
        Assertions.assertEquals("255", nutrientDefinition.nutr_No)
        Assertions.assertEquals("Water", nutrientDefinition.nutrDesc)
        Assertions.assertEquals("g", nutrientDefinition.units)
        val hql =
            "select count(*) from  NutrientData where nutrientDataKey.nutrientDefinition.nutr_No = :nutr_no"
        val query = session.createQuery(hql, Long::class.javaObjectType)
        query.setParameter("nutr_no", "255")
        val count = query.singleResult
        Assertions.assertEquals(8788L, count.toLong())
    }

    @Test
    fun waterLimitTest() {
        val hql = ("FROM NutrientData "
                + "WHERE nutrientDataKey.nutrientDefinition.nutr_No = :nutr_no "
                + "ORDER BY nutrientDataKey.foodDescription.NDB_No DESC ")
        val query =
            session.createQuery(hql, NutrientData::class.java)
        query.setParameter("nutr_no", "255")
        query.maxResults = 10
        val list = query.resultList
        Assertions.assertEquals(10, list.size)
        for (nutrientData in list) {
            Assertions
                .assertEquals("255", nutrientData.nutrientDataKey!!.getNutrientDefinition()!!.nutr_No)
            println(nutrientData.nutrientDataKey!!.foodDescription!!.nDB_No)
        }
    }

}