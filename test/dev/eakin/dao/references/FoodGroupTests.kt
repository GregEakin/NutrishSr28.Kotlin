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

import dev.eakin.dao.entities.FoodDescription
import dev.eakin.dao.entities.FoodGroup
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class FoodGroupTests internal constructor(private val session: Session) {

    //  Links to the Food Description file by FdGrp_Cd
    @Test
    fun foodDescriptionTest() {
        val foodGroup = session.load(FoodGroup::class.java, "0400")
        val foodDescriptionSet: Set<FoodDescription> = foodGroup.getFoodDescriptionSet()
        Assertions.assertEquals(220, foodDescriptionSet.size)
    }
}