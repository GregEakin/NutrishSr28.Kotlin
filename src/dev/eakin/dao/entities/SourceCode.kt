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
@Table(name = "SRC_CD")
class SourceCode {
    //  Links to the Nutrient Data file by Src_Cd
    @get:Column(name = "Src_Cd", length = 2, nullable = false)
    @get:Id
    var src_Cd: String? = null

    @get:Column(name = "SrcCd_Desc", length = 60, nullable = false)
    var srcCd_Desc: String? = null
    private var nutrientDataSet: MutableSet<NutrientData> = HashSet()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceCode")
    fun getNutrientDataSet(): MutableSet<NutrientData> {
        return nutrientDataSet
    }

    fun setNutrientDataSet(nutrientDataSet: MutableSet<NutrientData>) {
        this.nutrientDataSet = nutrientDataSet
    }

    fun addNutrientData(nutrientData: NutrientData) {
        nutrientData.setSourceCode(this)
        nutrientDataSet.add(nutrientData)
    }
}