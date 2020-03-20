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

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class NutrientDataKey : Serializable {
    @get:JoinColumn(name = "NDB_No", nullable = false)
    @get:ManyToOne(fetch = FetchType.LAZY)
    var foodDescription // The food item
            : FoodDescription? = null
    private var nutrientDefinition // The nutrient to which footnote applies.
            : NutrientDefinition? = null

    constructor() {}
    constructor(foodDescription: FoodDescription, nutrientDefinition: NutrientDefinition) {
        this.foodDescription = foodDescription
        this.nutrientDefinition = nutrientDefinition
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUTR_NO", nullable = false)
    fun getNutrientDefinition(): NutrientDefinition? {
        return nutrientDefinition
    }

    fun setNutrientDefinition(nutrientDefinition: NutrientDefinition?) {
        this.nutrientDefinition = nutrientDefinition
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as NutrientDataKey
        return ((if (foodDescription != null) foodDescription == that.foodDescription else that.foodDescription == null)
                && if (nutrientDefinition != null) nutrientDefinition!!.equals(that.nutrientDefinition) else that.nutrientDefinition == null)
    }

    override fun hashCode(): Int {
        var result: Int
        result = if (foodDescription != null) foodDescription.hashCode() else 0
        result = 31 * result + if (nutrientDefinition != null) nutrientDefinition.hashCode() else 0
        return result
    }
}