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

import dev.eakin.dao.entities.Footnote
import dev.eakin.dao.entities.NutrientData
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class FootnoteTests internal constructor(private val session: Session) {

    //  Links to the Food Description file by NDB_No
    @Test
    fun foodDescriptionTest() {
        val hql =
            "FROM Footnote WHERE foodDescription.NDB_No = :ndb_no and nutrientDefinition.nutr_No is null"
        val query =
            session.createQuery(
                hql,
                Footnote::class.java
            )
        query.setParameter("ndb_no", "05316")
        val results: List<*> = query.list()
        Assertions.assertEquals(1, results.size)
        val footnote = query.singleResult
        val foodDescription = footnote.foodDescription
        Assertions.assertEquals("05316", foodDescription!!.nDB_No)
    }

    //  Links to the Nutrient Data file by NDB_No and when applicable, Nutr_No
    @Test
    fun nutrientDataTest1() {
        val hql =
            "FROM Footnote WHERE foodDescription.NDB_No = :ndb_no and nutrientDefinition.nutr_No is null"
        val query =
            session.createQuery(
                hql,
                Footnote::class.java
            )
        query.setParameter("ndb_no", "05316")
        val footnote = query.singleResult
        val foodDescription = footnote.foodDescription
        val nutrientDataSet = foodDescription!!.getNutrientDataSet()
        Assertions.assertEquals(44, nutrientDataSet.size)
        for (nutrientData in nutrientDataSet) Assertions
            .assertEquals("05316", nutrientData.nutrientDataKey!!.foodDescription!!.nDB_No)
    }

    //  Links to the Nutrient Data file by NDB_No and when applicable, Nutr_No
    @Test
    fun nutrientDataTest2() {
//        String hql = "FROM Footnote WHERE foodDescription.NDB_No = :ndb_no and nutrientDefinition.nutr_No = :nutr_no";
//        Query<Footnote> query = session.createQuery(hql, Footnote.class);
//        query.setParameter("ndb_no", "05316");
//        query.setParameter("nutr_no", "204");
//        Footnote footnote = query.getSingleResult();
        val hql =
            "FROM NutrientData WHERE nutrientDataKey.foodDescription.NDB_No = :ndb_no and nutrientDataKey.nutrientDefinition.nutr_No = :nutr_no"
        val query =
            session.createQuery(hql, NutrientData::class.java)
        query.setParameter("ndb_no", "05316") // set from footnote.getFoodDescription().getNDB_No()
        query.setParameter("nutr_no", "204") // set from footnote.getNutrientDefinition().getNutr_No()
        val nutrientData = query.singleResult
        Assertions.assertEquals("05316", nutrientData.nutrientDataKey!!.foodDescription!!.nDB_No)
        Assertions
            .assertEquals("204", nutrientData.nutrientDataKey!!.getNutrientDefinition()!!.nutr_No)
    }

    //  Links to the Nutrient Definition file by Nutr_No, when applicable
    @Test
    fun nutrientDefinitionTest() {
        val hql =
            "FROM Footnote WHERE foodDescription.NDB_No = :ndb_no and nutrientDefinition.nutr_No = :nutr_no"
        val query =
            session.createQuery(
                hql,
                Footnote::class.java
            )
        query.setParameter("ndb_no", "05316")
        query.setParameter("nutr_no", "204")
        val footnote = query.singleResult
        val nutrientDefinition = footnote.getNutrientDefinition()
        Assertions.assertEquals("204", nutrientDefinition!!.nutr_No)
    }
}
