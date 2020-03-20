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
package dev.eakin.dao.parsers

import dev.eakin.dao.entities.*
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object NutData {
    const val Filename = ".\\data\\NUT_DATA.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(session, line)) }
        }
    }

    private fun parseLine(session: Session, line: String): NutrientData {
        val fields = line.split("\\^".toRegex())
        val item = NutrientData()
        val NDB_No = fields[0].removeSurrounding("~")
        val foodDescription = session.load(FoodDescription::class.java, NDB_No)
        val Nutr_No = fields[1].removeSurrounding("~")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, Nutr_No)
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        item.nutrientDataKey = nutrientDataKey
        item.nutr_Val = fields[2].toDouble()
        item.num_Data_Pts = fields[3].toInt()
        item.std_Error = fields[4].toDoubleOrNull()
        val Src_Cd = fields[5].removeSurrounding("~")
        val sourceCode = session.load(SourceCode::class.java, Src_Cd)
        item.addSourceCode(sourceCode)
        val Deriv_Cd = fields[6].removeSurrounding("~").ifBlank { null }
        if (Deriv_Cd != null) {
            val dataDerivation = session.load(DataDerivation::class.java, Deriv_Cd)
            item.addDataDerivation(dataDerivation)
        }
        val Ref_NDB_No = fields[7].removeSurrounding("~").ifBlank { null }
        if (Ref_NDB_No != null) {
            val refFoodDescription = session.load(FoodDescription::class.java, Ref_NDB_No)
            item.refFoodDescription = refFoodDescription
        }
        item.add_Nutr_Mark = fields[8].removeSurrounding("~").ifBlank { null }
        item.num_Studies = fields[9].toIntOrNull()
        item.min = fields[10].toDoubleOrNull()
        item.max = fields[11].toDoubleOrNull()
        item.dF = fields[12].toIntOrNull()
        item.low_EB = fields[13].toDoubleOrNull()
        item.up_EB = fields[14].toDoubleOrNull()
        item.stat_cmt = fields[15].removeSurrounding("~").ifBlank { null }
        item.addMod_Date = fields[16].ifBlank { null }
        item.cC = fields[17].ifBlank { null }
        foodDescription.addNutrientData(item)
        nutrientDefinition.addNutrientData(item)
        return item
    }
}