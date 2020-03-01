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

import dev.eakin.dao.entities.NutrientData
import dev.eakin.dao.entities.SourceCode
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class SourceCodeTests internal constructor(private val session: Session) {

    //  Links to the Nutrient Data file by Src_Cd
    @Test
    fun nutrientDataTest() {
        val sourceCode =
            session.load(SourceCode::class.java, "11")
        val nutrientDataSet: Set<NutrientData> = sourceCode.getNutrientDataSet()
        Assertions.assertEquals(822, nutrientDataSet.size)
    }

}