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
@Table(name = "FOOD_DES")
class FoodDescription {
    //  Links to the Food Group Description file by the FdGrp_Cd field
    //  Links to the Nutrient Data file by the NDB_No field
    //  Links to the Weight file by the NDB_No field
    //  Links to the Footnote file by the NDB_No field
    //  Links to the LanguaL Factor file by the NDB_No field
    @get:Column(name = "NDB_No", length = 5, nullable = false)
    @get:Id
    var nDB_No: String? = null
    private var foodGroup: FoodGroup? = null

    @get:Column(name = "Long_Desc", length = 200, nullable = false)
    var long_Desc: String? = null

    @get:Column(name = "Shrt_Desc", length = 60, nullable = false)
    var shrt_Desc: String? = null

    @get:Column(name = "ComName", length = 100)
    var comName: String? = null

    @get:Column(name = "ManufacName", length = 65)
    var manufacName: String? = null

    @get:Column(name = "Survey", length = 1)
    var survey: String? = null

    @get:Column(name = "Ref_desc", length = 135)
    var ref_desc: String? = null

    @get:Column(name = "Refuse")
    var refuse: Int? = null

    @get:Column(name = "SciName", length = 65)
    var sciName: String? = null

    @get:Column(name = "N_Factor")
    var n_Factor: Double? = null

    @get:Column(name = "Pro_Factor")
    var pro_Factor: Double? = null

    @get:Column(name = "Fat_Factor")
    var fat_Factor: Double? = null

    @get:Column(name = "CHO_Factor")
    var cHO_Factor: Double? = null
    private var nutrientDataSet: MutableSet<NutrientData> = HashSet<NutrientData>(0)
    private var weightSet: MutableSet<Weight> = HashSet<Weight>(0)
    private var footnoteSet: MutableSet<Footnote> = HashSet<Footnote>(0)
    private var languageSet: MutableSet<Language> = HashSet<Language>(0)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FdGrp_Cd", nullable = false)
    fun getFoodGroup(): FoodGroup? {
        return foodGroup
    }

    fun setFoodGroup(foodGroup: FoodGroup?) {
        this.foodGroup = foodGroup
    }

    fun addFoodGroup(foodGroup: FoodGroup) {
        //        if (this.foodGroup != null)
        //            this.foodGroup.getFoodDescriptionSet().remove(this);
        foodGroup.getFoodDescriptionSet().add(this)
        this.foodGroup = foodGroup
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nutrientDataKey.foodDescription")
    fun getNutrientDataSet(): Set<NutrientData> {
        return nutrientDataSet
    }

    fun setNutrientDataSet(nutrientData: MutableSet<NutrientData>) {
        nutrientDataSet = nutrientData
    }

    fun addNutrientData(nutrientData: NutrientData) {
        val nutrientDataKey: NutrientDataKey? = nutrientData.nutrientDataKey
        requireNotNull(nutrientDataKey) { "Null NutrientDataKey" }
        nutrientDataKey.foodDescription = this
        nutrientDataSet.add(nutrientData)
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "weightKey.foodDescription")
    fun getWeightSet(): Set<Weight> {
        return weightSet
    }

    fun setWeightSet(weightSet: MutableSet<Weight>) {
        this.weightSet = weightSet
    }

    fun addWeight(weight: Weight) {
        require(weight.weightKey!!.foodDescription!!.nDB_No.equals(nDB_No)) { "Weight not related to FoodDescription" }
        weightSet.add(weight)
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodDescription")
    fun getFootnoteSet(): Set<Footnote> {
        return footnoteSet
    }

    fun setFootnoteSet(footnoteSet: MutableSet<Footnote>) {
        this.footnoteSet = footnoteSet
    }

    fun addFootnote(footnote: Footnote) {
        footnote.foodDescription = this
        footnoteSet.add(footnote)
    }

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "LANGUAL",
        joinColumns = [JoinColumn(name = "NDB_No", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "Factor_Code", nullable = false)]
    )
    fun getLanguageSet(): MutableSet<Language> {
        return languageSet
    }

    fun setLanguageSet(languageSet: MutableSet<Language>) {
        this.languageSet = languageSet
    }

    fun addLanguage(language: Language) {
        language.getFoodDescriptionSet().add(this)
        languageSet.add(language)
    }
}