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
package dev.eakin.flatfile.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "ABBREV")
class Abbreviations {
    @get:Column(name = "NDB_No", length = 5, nullable = false)
    @get:Id
    var nDB_No // 5-digit Nutrient Databank number that uniquely identifies a food item.
            : String? = null

    @get:Column(name = "Shrt_Desc", length = 60, nullable = false)
    var shrt_Desc // A 60 60-character abbreviated description of food item.†
            : String? = null

    @get:Column(name = "Water")
    var water // N 10.2 Water (g/100 g)
            : Double? = null

    @get:Column(name = "Energ_Kcal")
    var energ_Kcal //    Energ_Kcal N 10 Food energy (kcal/100 g)
            : Int? = null

    @get:Column(name = "Protein")
    var protein //    Protein N 10.2 Protein (g/100 g)
            : Double? = null

    @get:Column(name = "Lipid_Tot")
    var lipid_Tot //    Lipid_Tot N 10.2 Total lipid (fat) (g/100 g)
            : Double? = null

    @get:Column(name = "Ash")
    var ash //    Ash N 10.2 Ash (g/100 g)
            : Double? = null

    @get:Column(name = "Carbohydrt", nullable = false)
    var carbohydrt //    Carbohydrt N 10.2 Carbohydrate, by difference (g/100 g)
            : Double? = null

    @get:Column(name = "Fiber_TD")
    var fiber_TD //    Fiber_TD N 10.1 Total dietary fiber (g/100 g)
            : Double? = null

    @get:Column(name = "Sugar_Tot")
    var sugar_Tot //    Sugar_Tot N 10.2 Total sugars (g/100 g)
            : Double? = null

    @get:Column(name = "Calcium")
    var calcium //    Calcium N 10 Calcium (mg/100 g)
            : Int? = null

    @get:Column(name = "Iron")
    var iron //    Iron N 10.2 Iron (mg/100 g)
            : Double? = null

    @get:Column(name = "Magnesium")
    var magnesium //    Magnesium N 10 Magnesium (mg/100 g)
            : Int? = null

    @get:Column(name = "Phosphorus")
    var phosphorus //    Phosphorus N 10 Phosphorus (mg/100 g)
            : Int? = null

    @get:Column(name = "Potassium")
    var potassium //    Potassium N 10 Potassium (mg/100 g)
            : Int? = null

    @get:Column(name = "Sodium")
    var sodium //    Sodium N 10 Sodium (mg/100 g)
            : Int? = null

    @get:Column(name = "Zinc")
    var zinc //    Zinc N 10.2 Zinc (mg/100 g)
            : Double? = null

    @get:Column(name = "Copper")
    var copper //    Copper N 10.3 Copper (mg/100 g)
            : Double? = null

    @get:Column(name = "Manganese")
    var manganese //    Manganese N 10.3 Manganese (mg/100 g)
            : Double? = null

    @get:Column(name = "Selenium")
    var selenium //    Selenium N 10.1 Selenium (μg/100 g)
            : Double? = null

    @get:Column(name = "Vit_C")
    var vit_C //    Vit_C N 10.1 Vitamin C (mg/100 g)
            : Double? = null

    @get:Column(name = "Thiamin")
    var thiamin //    Thiamin N 10.3 Thiamin (mg/100 g)
            : Double? = null

    @get:Column(name = "Riboflavin")
    var riboflavin //    Riboflavin N 10.3 Riboflavin (mg/100 g)
            : Double? = null

    @get:Column(name = "Niacin")
    var niacin //    Niacin N 10.3 Niacin (mg/100 g)
            : Double? = null

    @get:Column(name = "Panto_acid")
    var panto_acid //    Panto_acid N 10.3 Pantothenic acid  (mg/100 g)
            : Double? = null

    @get:Column(name = "Vit_B6")
    var vit_B6 //    Vit_B6 N 10.3 Vitamin B6 (mg/100 g)
            : Double? = null

    @get:Column(name = "Folate_Tot")
    var folate_Tot //    Folate_Tot N 10 Folate, total (μg/100 g)
            : Int? = null

    @get:Column(name = "Folic_acid")
    var folic_acid //    Folic_acid N 10 Folic acid (μg/100 g)
            : Int? = null

    @get:Column(name = "Food_Folate")
    var food_Folate //    Food_Folate N 10 Food folate (μg/100 g)
            : Int? = null

    @get:Column(name = "Folate_DFE")
    var folate_DFE //    Folate_DFE N 10 Folate (μg dietary folate equivalents/100 g)
            : Int? = null

    @get:Column(name = "Choline_Tot")
    var choline_Tot //    Choline_Tot N 10.1 Choline, total (mg/100 g)
            : Double? = null

    @get:Column(name = "Vit_B12")
    var vit_B12 //    Vit_B12 N 10.2 Vitamin B12 (μg/100 g)
            : Double? = null

    @get:Column(name = "Vit_A_IU")
    var vit_A_IU //    Vit_A_IU N 10 Vitamin A (IU/100 g)
            : Int? = null

    @get:Column(name = "Vit_A_RAE")
    var vit_A_RAE //    Vit_A_RAE N 10 Vitamin A (μg retinol activity equivalents/100g)
            : Int? = null

    @get:Column(name = "Retinol")
    var retinol //    Retinol N 10 Retinol (μg/100 g)
            : Int? = null

    @get:Column(name = "Alpha_Carot")
    var alpha_Carot //    Alpha_Carot N 10 Alpha-carotene (μg/100 g)
            : Int? = null

    @get:Column(name = "Beta_Carot")
    var beta_Carot //    Beta_Carot N 10 Beta-carotene (μg/100 g)
            : Int? = null

    @get:Column(name = "Beta_Crypt")
    var beta_Crypt //    Beta_Crypt N 10 Beta-cryptoxanthin (μg/100 g)
            : Int? = null

    @get:Column(name = "Lycopene")
    var lycopene //    Lycopene N 10 Lycopene (μg/100 g)
            : Int? = null

    @get:Column(name = "Lut_Zea")
    var lut_Zea //    Lut+Zea N 10 Lutein+zeazanthin (μg/100 g)
            : Int? = null

    @get:Column(name = "Vit_E")
    var vit_E //    Vit_E N 10.2 Vitamin E (alpha-tocopherol) (mg/100 g)
            : Double? = null

    @get:Column(name = "Vit_D_mcg")
    var vit_D_mcg //    Vit_D_mcg N 10.1 Vitamin D (μg/100 g)
            : Double? = null

    @get:Column(name = "Vit_D_IU")
    var vit_D_IU //    Vit_D_IU N 10 Vitamin D (IU/100 g)
            : Int? = null

    @get:Column(name = "Vit_K")
    var vit_K //    Vit_K N 10.1 Vitamin K (phylloquinone) (μg/100 g)
            : Double? = null

    @get:Column(name = "FA_Sat")
    var fA_Sat //    FA_Sat N 10.3 Saturated fatty acid (g/100 g)
            : Double? = null

    @get:Column(name = "FA_Mono")
    var fA_Mono //    FA_Mono N 10.3 Monounsaturated fatty acids (g/100 g)
            : Double? = null

    @get:Column(name = "FA_Poly")
    var fA_Poly //    FA_Poly N 10.3 Polyunsaturated fatty acids (g/100 g)
            : Double? = null

    @get:Column(name = "Cholestrl")
    var cholestrl //    Cholestrl N 10.3 Cholesterol (mg/100 g)
            : Double? = null

    @get:Column(name = "GmWt_1")
    var gmWt_1 //    GmWt_1 N 9.2 First household weight for this item from the Weight file.‡
            : Double? = null

    @get:Column(name = "GmWt_Desc1", length = 120)
    var gmWt_Desc1 //    GmWt_Desc1 A 120 Description of household weight number 1.
            : String? = null

    @get:Column(name = "GmWt_2")
    var gmWt_2 //    GmWt_2 N 9.2 Second household weight for this item from the Weight file.‡
            : Double? = null

    @get:Column(name = "GWt_Desc2", length = 120)
    var gmWt_Desc2 //    GmWt_Desc2 A 120 Description of household weight number 2.
            : String? = null

    @get:Column(name = "Refuse_Pct")
    var refuse_Pct //    Refuse_Pct N 2 Percent refuse.
            : Int? = null
}