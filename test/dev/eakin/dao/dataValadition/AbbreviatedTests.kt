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
package dev.eakin.dao.dataValadition

import dev.eakin.dao.entities.DataSource
import dev.eakin.dao.entities.FoodDescription
import dev.eakin.dao.entities.NutrientData
import dev.eakin.dao.entities.NutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.hibernate.query.Query
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class AbbreviatedTests internal constructor(private val session: Session) {
    @Test
    fun Test1() {
        val hql = "FROM DataSource AS ds WHERE ds.id = :id"
        val query =
            session.createQuery(
                hql,
                DataSource::class.java
            )
        query.setParameter("id", "D642")
        val dataSource = query.singleResult
        assertEquals("D642", dataSource.dataSrc_ID)
    }

    @Test
    fun Test2() {
        val hql = "select nds from DataSource as ds join ds.nutrientDataSet nds where ds.id = :id"
        val query =
            session.createQuery(hql, NutrientData::class.java)
        query.setParameter("id", "D642")
        val list = query.resultList
        assertEquals(2, list.size)
    }

    @Test
    fun Test3() {
        val hql = """
            select new map( max(nutr_Val) as max, min(nutr_Val) as min, count(*) as n ) 
            from NutrientData nut 
            where nut.nutrientDataKey.nutrientDefinition.nutr_No = :id
            """.trimIndent()
        val query = session.createQuery(hql) as Query<Map<String, Any>>
        query.setParameter("id", "262")
        val map = query.singleResult
        assertEquals(0.0, map["min"] as Double)
        assertEquals(5714.0, map["max"] as Double)
        assertEquals(5396, map["n"] as Long)
    }

    @Test
    fun Test4() {
        val hql = """
            select nd.nutr_Val, fd.long_Desc 
            from NutrientData as nd 
            join nd.nutrientDataKey.foodDescription as fd 
            where nd.nutrientDataKey.nutrientDefinition.nutr_No = :id 
            and nd.nutr_Val >= :value 
            order by nd.nutr_Val desc
            """.trimIndent()
        val query = session.createQuery(hql)
        query.setParameter("id", "204")
        query.setParameter("value", 98.0)
        val list = query.resultList
        assertEquals(112, list.size)
    }

    @Test
    fun ColumnTest() {
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, "255")
        assertEquals("Water", nutrientDefinition.nutrDesc)
        assertEquals("WATER", nutrientDefinition.tagname)
        assertEquals("g", nutrientDefinition.units)
        val nutrientDataSet = nutrientDefinition.getNutrientDataSet()
        assertEquals(8788, nutrientDataSet.size)
    }

    @Test
    fun ColumnHqlTest() {
        val hql = """
            FROM NutrientData as nd 
            join nd.nutrientDataKey.foodDescription as fd 
            where nd.nutrientDataKey.nutrientDefinition.nutr_No = :id 
            order by nd.nutrientDataKey.foodDescription.NDB_No  
            """.trimIndent()
        val query = session.createQuery(hql)
        query.setParameter("id", "255")
        query.maxResults = 10
        val list = query.resultList
        for (listItem in list) {
            val o1 = listItem as Array<Any>
            val nd = o1[0] as NutrientData
            val fd = o1[1] as FoodDescription
            println(
                """${fd.nDB_No}: ${fd.shrt_Desc} has ${nd.nutr_Val} ${nd.nutrientDataKey!!.getNutrientDefinition()!!.units} of ${nd.nutrientDataKey!!.getNutrientDefinition()!!.nutrDesc}"""
            )
        }
    }

    @Test
    fun RowHqlTest() {
        //String hql = "FROM NutrientData as nd join nd.nutrientDataKey.foodDescription as fd "
        val hql = ("FROM FoodDescription as fd join fd.nutrientDataSet as nds "
                + "where fd.NDB_No = :id "
                + "order by nds.nutrientDataKey.nutrientDefinition.nutr_No ")
        val query = session.createQuery(hql)
        query.setParameter("id", "01001")
        query.maxResults = 10
        val list = query.resultList
        for (listItem in list) {
            val o1 = listItem as Array<Any>
            val nd = o1[1] as NutrientData
            val fd = o1[0] as FoodDescription
            println(
                """${fd.nDB_No}, ${fd.shrt_Desc}, ${nd.nutr_Val}, ${nd.nutrientDataKey!!.getNutrientDefinition()!!.tagname}"""
            )
        }
    }
}