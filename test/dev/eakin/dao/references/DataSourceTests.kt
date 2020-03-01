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

import dev.eakin.dao.entities.DataSource
import dev.eakin.dao.entities.NutrientDefinition
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class DataSourceTests internal constructor(private val session: Session) {

    //  Links to Nutrient Data file by NDB No. through the Sources of Data Link file
    @Test
    fun nutrientDataTest() {
        val dataSource =
            session.load(DataSource::class.java, "D642")
        val nutrientDataSet = dataSource.getNutrientDataSet()
        Assertions.assertEquals(2, nutrientDataSet.size)
    }

    //  Links to the Nutrient Definition file by Nutr_No
    @Test
    fun nutrientDefinitionTest() {
        //DataSource dataSource = session.load(DataSource.class, "D642");
        val hql =
            "select nd from DataSource ds join ds.nutrientDataSet nds join nds.nutrientDataKey.nutrientDefinition nd where ds.id = :id"
        val query = session.createQuery(
            hql,
            NutrientDefinition::class.java
        )
        query.setParameter("id", "D642")
        val list = query.resultList
        Assertions.assertEquals(2, list.size)
        Assertions.assertArrayEquals(
            arrayOf("306", "307"),
            list.stream().map(NutrientDefinition::nutr_No).sorted().toArray()
        )
    }

}