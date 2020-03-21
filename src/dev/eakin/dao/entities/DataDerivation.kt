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

import dev.eakin.dao.entities.NutrientData
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "DERIV_CD")
class DataDerivation {
    //  Links to the Nutrient Data file by Deriv_Cd
    @get:Column(name = "Deriv_Cd", length = 4, nullable = false)
    @get:Id
    var deriv_Cd: String? = null

    @get:Column(name = "Deriv_Desc", length = 120, nullable = false)
    var deriv_Desc: String? = null
    private var nutrientDataSet: MutableSet<NutrientData> = HashSet<NutrientData>(0)

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dataDerivation")
    fun getNutrientDataSet(): Set<NutrientData> {
        return nutrientDataSet
    }

    fun setNutrientDataSet(nutrientDataSet: MutableSet<NutrientData>) {
        this.nutrientDataSet = nutrientDataSet
    }

    fun addNutrientData(nutrientData: NutrientData) {
        nutrientData.dataDerivation = this
        nutrientDataSet.add(nutrientData)
    }
}