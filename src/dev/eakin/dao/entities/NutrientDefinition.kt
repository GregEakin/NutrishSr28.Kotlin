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
@Table(name = "NUTR_DEF")
class NutrientDefinition {
    //  Links to the Nutrient Data file by Nutr_No
    @get:Column(name = "Nutr_No", length = 3, nullable = false)
    @get:Id
    var nutr_No: String? = null

    @get:Column(name = "Units", length = 7, nullable = false)
    var units: String? = null

    @get:Column(name = "Tagname", length = 20)
    var tagname: String? = null

    @get:Column(name = "NutrDesc", length = 60, nullable = false)
    var nutrDesc: String? = null

    @get:Column(name = "Num_Dec", length = 1, nullable = false)
    var num_Dec: String? = null

    @get:Column(name = "SR_Order", nullable = false)
    var sR_Order: Int? = null
    private var nutrientDataSet: MutableSet<NutrientData> = HashSet(0)
    private var footnoteSet: MutableSet<Footnote> = HashSet(0)

    //  Links to the Nutrient Data file by Nutr_No
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nutrientDataKey.nutrientDefinition")
    fun getNutrientDataSet(): Set<NutrientData> {
        return nutrientDataSet
    }

    fun setNutrientDataSet(nutrientDataSet: MutableSet<NutrientData>) {
        this.nutrientDataSet = nutrientDataSet
    }

    fun addNutrientData(nutrientData: NutrientData?) {
        requireNotNull(nutrientData) { "Null NutrientData" }

        // if (nutrientData.getNutrientDataKey().getNutrientDefinition().)
        nutrientDataSet.add(nutrientData)
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nutrientDefinition")
    fun getFootnoteSet(): MutableSet<Footnote> {
        return footnoteSet
    }

    fun setFootnoteSet(footnoteSet: MutableSet<Footnote>) {
        this.footnoteSet = footnoteSet
    }

    fun addFootnote(footnote: Footnote?) {
        requireNotNull(footnote) { "null Footnote" }
        footnoteSet.add(footnote)
        footnote.setNutrientDefinition(this)
    }
}