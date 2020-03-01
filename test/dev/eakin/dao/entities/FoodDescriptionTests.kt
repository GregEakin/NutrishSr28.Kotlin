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
package dev.eakin.dao.entities

import dev.eakin.dao.entities.FootnoteTests.Companion.createFootnote
import dev.eakin.dao.entities.WeightTests.Companion.createWeight
import dev.eakin.dao.utilities.NutrishRepositoryExtension
import org.hibernate.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(NutrishRepositoryExtension::class)
class FoodDescriptionTests internal constructor(private val session: Session) {
    @Test
    fun addNullFoodGroupTest() {
        val foodDescription =
            createFoodDescription()
        val closureContainingCodeToTest =
            Executable { foodDescription.addFoodGroup(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null FoodGroup"
        )
    }

    @Test
    fun addFoodGroupTest() {
        val foodDescription =
            createFoodDescription()
        val foodGroup: FoodGroup = FoodGroupTests.createFoodGroup()
        foodDescription.addFoodGroup(foodGroup)
        Assertions.assertSame(foodGroup, foodDescription.getFoodGroup())
        Assertions.assertTrue(foodGroup.getFoodDescriptionSet().contains(foodDescription))
    }

    @Test
    fun addNullWeightTest() {
        val foodDescription =
            createFoodDescription()
        val closureContainingCodeToTest =
            Executable { foodDescription.addWeight(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null Weight"
        )
    }

    @Test
    fun addWeightTest() {
        val foodDescription =
            createFoodDescription()
        val weight: Weight = createWeight(foodDescription)
        foodDescription.addWeight(weight)
        Assertions.assertTrue(foodDescription.getWeightSet().contains(weight))
        Assertions.assertSame(foodDescription, weight.weightKey!!.foodDescription)
    }

    @Test
    fun addNullFootnoteTest() {
        val foodDescription =
            createFoodDescription()
        val closureContainingCodeToTest =
            Executable { foodDescription.addFootnote(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null Footnote"
        )
    }

    @Test
    fun addFootnoteTest() {
        val foodDescription =
            createFoodDescription()
        val footnote: Footnote = createFootnote()
        foodDescription.addFootnote(footnote)
        Assertions.assertTrue(foodDescription.getFootnoteSet().contains(footnote))
        Assertions.assertSame(foodDescription, footnote.foodDescription)
    }

    @Test
    fun addNullLanguageTest() {
        val foodDescription =
            createFoodDescription()
        val closureContainingCodeToTest =
            Executable { foodDescription.addLanguage(null) }
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            closureContainingCodeToTest,
            "null Language"
        )
    }

    @Test
    fun addLanguageTest() {
        val foodDescription =
            createFoodDescription()
        val language: Language = LanguageTests.createLanguage()
        foodDescription.addLanguage(language)
        Assertions.assertTrue(foodDescription.getLanguageSet().contains(language))
        Assertions.assertTrue(language.getFoodDescriptionSet().contains(foodDescription))
    }

    companion object {
        fun createFoodDescription(): FoodDescription {
            val foodDescription = FoodDescription()
            foodDescription.nDB_No = "000000"
            return foodDescription
        }
    }

}