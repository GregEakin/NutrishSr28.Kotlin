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

import javax.persistence.*

@Entity
@Table(name = "FOOTNOTE")
class Footnote {
    //  Links to the Food Description file by NDB_No
    //  Links to the Nutrient Data file by NDB_No and when applicable, Nutr_No
    //  Links to the Nutrient Definition file by Nutr_No, when applicable
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Id
    var id :Int? = 0

    @get:JoinColumn(name = "NDB_No", nullable = false)
    @get:ManyToOne(fetch = FetchType.LAZY)
    var foodDescription // The food item
            : FoodDescription? = null

    @get:Column(name = "Footnt_No", length = 4, nullable = false)
    var footnt_No // Sequence number.
            : String? = null

    @get:Column(name = "Footnt_Typ", length = 1, nullable = false)
    var footnt_Typ // Type of footnote:
            : String? = null

    private var nutrientDefinition // The nutrient to which footnote applies.
            : NutrientDefinition? = null

    @get:Column(name = "Footnt_Txt", length = 200, nullable = false)
    var footnt_Txt // Footnote text.
            : String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Nutr_No")
    fun getNutrientDefinition(): NutrientDefinition? {
        return nutrientDefinition
    }

    fun setNutrientDefinition(nutrientDefinition: NutrientDefinition?) {
        this.nutrientDefinition = nutrientDefinition
    }

    fun addNutrientDefinition(nutrientDefinition: NutrientDefinition) {
        nutrientDefinition.getFootnoteSet().add(this)
        this.nutrientDefinition = nutrientDefinition
    }
}