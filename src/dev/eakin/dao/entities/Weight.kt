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
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

/*
    Weight File  (file name = WEIGHT). This file (Table 12) contains the weight in grams of a number of common measures for each food item.
 */
@Entity
@Table(name = "WEIGHT")
class Weight : Serializable {
    //  Links to Food Description file by NDB_No
    //  Links to Nutrient Data file by NDB_No
    @get:EmbeddedId
    var weightKey: WeightKey? = null

    // Amount N 5.3 N Unit modifier (for example, 1 in “1 cup”).
    @get:Column(name = "Amount", nullable = false)
    var amount: Double? = null

    // Msre_Desc A 84 N Description (for example, cup, diced, and 1-inch pieces).
    @get:Column(name = "Msre_Desc", length = 84, nullable = false)
    var msre_Desc: String? = null

    // Gm_Wgt N 7.1 N Gram weight.
    @get:Column(name = "Gm_Wgt", nullable = false)
    var gm_Wgt: Double? = null

    // Num_Data_Pts N 3 Y Number of data points.
    @get:Column(name = "Num_Data_Pts")
    var num_Data_Pts: Int? = null

    // Std_Dev N 7.3 Y Standard deviation.
    @get:Column(name = "Std_Dev")
    var std_Dev: Double? = null

}