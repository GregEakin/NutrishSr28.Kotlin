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
package dev.eakin.dao.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "LANGDESC")
class Language {
    //  Links to the LanguaL Factor file by the Factor_Code field
    @get:Column(name = "Factor_Code", length = 5, nullable = false)
    @get:Id
    var factor_Code: String? = null

    @get:Column(name = "Description", length = 140, nullable = false)
    var description: String? = null
    private var foodDescriptionSet: MutableSet<FoodDescription> =
        HashSet(0)

    @ManyToMany(mappedBy = "languageSet")
    fun getFoodDescriptionSet(): MutableSet<FoodDescription> {
        return foodDescriptionSet
    }

    fun setFoodDescriptionSet(foodDescriptionSet: MutableSet<FoodDescription>) {
        this.foodDescriptionSet = foodDescriptionSet
    }

    fun addFoodDescription(foodDescription: FoodDescription?) {
        requireNotNull(foodDescription) { "null FoodDescription" }
        foodDescriptionSet.add(foodDescription)
        foodDescription.getLanguageSet().add(this)
    }
}