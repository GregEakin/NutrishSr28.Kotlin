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
import dev.eakin.dao.entities.Language
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(NutrishRepositoryExtension::class)
class LanguageTests internal constructor(private val session: Session) {

    //  Links to the Food Description file by the NDB_No field
    @Test
    fun foodDescriptionTest() {
        val language = session.load(Language::class.java, "A0143")
        val foodDescriptionSet: Set<FoodDescription> = language.getFoodDescriptionSet()
        Assertions.assertEquals(232, foodDescriptionSet.size)
    }

    //  Links to LanguaL Factors Description file by the Factor_Code field
    @Test
    fun languageSetTest() {
        val foodDescription = session.load(FoodDescription::class.java, "02014")
        val languageSet: Set<Language> = foodDescription.getLanguageSet()
        Assertions.assertEquals(13, languageSet.size)
    }

}