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
@Table(name = "DATA_SRC")
class DataSource {
    //  Links to Nutrient Data file by NDB No. through the Sources of Data Link file
    @get:Column(name = "DataSrc_ID", length = 6, nullable = false)
    @get:Id
    var dataSrc_ID // Unique ID identifying the reference/source.
            : String? = null

    @get:Column(name = "Authors", length = 255)
    var authors // List of authors for a journal article or name of sponsoring organization for other documents.
            : String? = null

    @get:Column(name = "Title", length = 255, nullable = false)
    var title // Title of article or name of document, such as a report from a company or trade association.
            : String? = null

    @get:Column(name = "Year", length = 4)
    var year // Year article or document was published.
            : String? = null

    @get:Column(name = "Journal", length = 135)
    var journal // Name of the journal in which the article was published.
            : String? = null

    @get:Column(name = "Vol_City", length = 16)
    var vol_City // Volume number for journal articles, books, or reports; city where sponsoring organization is located.
            : String? = null

    @get:Column(name = "Issue_State", length = 5)
    var issue_State // Issue number for journal article; State where the sponsoring organization is located.
            : String? = null

    @get:Column(name = "Start_Page", length = 5)
    var start_Page // Starting page number of article/document.
            : String? = null

    @get:Column(name = "End_Page", length = 5)
    var end_Page // Ending page number of article/document.
            : String? = null
    private var nutrientDataSet: MutableSet<NutrientData> = HashSet<NutrientData>(0)

    @ManyToMany(mappedBy = "dataSourceSet")
    fun getNutrientDataSet(): Set<NutrientData> {
        return nutrientDataSet
    }

    fun setNutrientDataSet(nutrientDataSet: MutableSet<NutrientData>) {
        this.nutrientDataSet = nutrientDataSet
    }

    fun addNutrientData(nutrientData: NutrientData) {
        nutrientData.dataSourceSet.add(this)
        nutrientDataSet.add(nutrientData)
    }
}