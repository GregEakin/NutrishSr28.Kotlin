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
@Table(name = "FD_GROUP")
class FoodGroup {
    //  Links to the Food Description file by FdGrp_Cd
    @get:Column(name = "FdGrp_Cd", length = 4, nullable = false)
    @get:Id
    var fdGrp_Cd: String? = null

    @get:Column(name = "FdGrp_Desc", length = 60, nullable = false)
    var fdGrp_Desc: String? = null
    private var foodDescriptionSet: MutableSet<FoodDescription> =
        HashSet(0)

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodGroup")
    fun getFoodDescriptionSet(): MutableSet<FoodDescription> {
        return foodDescriptionSet
    }

    fun setFoodDescriptionSet(foodDescriptionSet: MutableSet<FoodDescription>) {
        this.foodDescriptionSet = foodDescriptionSet
    }

    fun addFoodDescriptionSet(foodDescription: FoodDescription) {
        foodDescription.setFoodGroup(this)
        foodDescriptionSet.add(foodDescription)
    }
}