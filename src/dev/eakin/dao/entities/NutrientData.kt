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
@Table(name = "NUT_DATA")
class NutrientData {
    //  Links to the Food Description file by NDB_No
    //  Links to the Footnote file by NDB_No and when applicable, Nutr_No
    //  Links to the Nutrient Definition file by Nutr_No
    //  Links to the Footnote file by NDB_No and when applicable, Nutr_No
    //  Links to the Source Code file by Src_Cd
    //  Links to the Data Derivation Code Description file by Deriv_Cd
    //  Links to the Food Description file by Ref_NDB_No
    //  Links to the Sources of Data Link file by NDB_No and Nutr_No
    //  Links to the Weight file by NDB_No
    @get:EmbeddedId
    var nutrientDataKey: NutrientDataKey? = null

    @get:Column(name = "Nutr_Val", nullable = false)
    var nutr_Val // Amount in 100 grams, edible portion †.
            : Double? = null

    @get:Column(name = "Num_Data_Pts", nullable = false)
    var num_Data_Pts // Number of data points is the number of analyses used to calculate the nutrient value. If the number of data points is 0, the value was calculated or imputed.
            : Int? = null

    @get:Column(name = "Std_Error")
    var std_Error // Standard error of the mean. Null if cannot be calculated. The standard error is also not given if the number of data points is less than three.
            : Double? = null
    private var sourceCode // Src_Cd - Code indicating type of data.
            : SourceCode? = null

    @get:JoinColumn(name = "Deriv_Cd")
    @get:ManyToOne(fetch = FetchType.LAZY)
    var dataDerivation // Deriv_Cd - Data Derivation Code giving specific information on how the value is determined.  This field is populated only for items added or updated starting with SR14.  This field may not be populated if older records were used in the calculation of the mean value.
            : DataDerivation? = null

    @get:JoinColumn(name = "Ref_NDB_No")
    @get:ManyToOne(fetch = FetchType.LAZY)
    var refFoodDescription // The item used to calculate a missing value. Populated only for items added or updated starting with SR14.
            : FoodDescription? = null

    @get:Column(name = "Add_Nutr_Mark", length = 1)
    var add_Nutr_Mark // Indicates a vitamin or mineral added for fortification or enrichment. This field is populated for ready-toeat breakfast cereals and many brand-name hot cereals in food group 08.
            : String? = null

    @get:Column(name = "Num_Studies")
    var num_Studies // Number of studies.
            : Int? = null

    @set:Column(name = "Min")
    var min // Min.
            : Double? = null

    @get:Column(name = "Max")
    var max // Max.
            : Double? = null

    @get:Column(name = "DF")
    var dF // Degrees of freedom.
            : Int? = null

    @get:Column(name = "Low_EB")
    var low_EB // Lower 95% error bound.
            : Double? = null

    @get:Column(name = "Up_EB")
    var up_EB // Upper 95% error bound.
            : Double? = null

    @get:Column(name = "Stat_cmt", length = 10)
    var stat_cmt // Statistical comments.
            : String? = null

    @get:Column(name = "AddMod_Date", length = 10)
    var addMod_Date // Indicates when a value was either added to the database or last modified.
            : String? = null

    @get:Column(name = "CC", length = 1)
    var cC // Confidence Code indicating data quality, based on evaluation of sample plan, sample handling, analytical method, analytical quality control, and number of samples analyzed.
            : String? = null

    @get:JoinTable(
        name = "DATSRCLN",
        joinColumns = [JoinColumn(name = "NDB_No", nullable = false), JoinColumn(
            name = "Nutr_No",
            nullable = false
        )],
        inverseJoinColumns = [JoinColumn(name = "DataSrc_ID", nullable = false)]
    )
    @get:ManyToMany(cascade = [CascadeType.ALL])
    var dataSourceSet: MutableSet<DataSource> =
        HashSet(0)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Src_Cd", nullable = false)
    fun getSourceCode(): SourceCode? {
        return sourceCode
    }

    fun setSourceCode(sourceCode: SourceCode?) {
        this.sourceCode = sourceCode
    }

    fun addSourceCode(sourceCode: SourceCode) {
        this.sourceCode = sourceCode
        sourceCode.getNutrientDataSet().add(this)
    }

    fun addDataDerivation(dataDerivation: DataDerivation) {
        dataDerivation.addNutrientData(this)
        //        this.dataDerivation = dataDerivation;
        //        dataDerivation.getNutrientDataSet().add(this);
    }

    fun addDataSource(dataSource: DataSource) {
        dataSource.addNutrientData(this)
        //        this.dataSourceSet.add(dataSource);
        //        dataSource.getNutrientDataSet().add(this);
    }
}