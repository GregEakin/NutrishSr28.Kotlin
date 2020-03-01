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
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String -> parseLine(session, line) }
        }
    }

    private fun parseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = NutrientData()
        val NDB_No = fields[0].substring(1, fields[0].length - 1)
        val foodDescription = session.load(FoodDescription::class.java, NDB_No)
        val Nutr_No = fields[1].substring(1, fields[1].length - 1)
        val nutrientDefinition =
            session.load(NutrientDefinition::class.java, Nutr_No)
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        item.nutrientDataKey = nutrientDataKey
        item.nutr_Val = fields[2].toDouble()
        item.num_Data_Pts = fields[3].toInt()
        if (fields[4].length > 0) item.std_Error = fields[4].toDouble()
        val Src_Cd = fields[5].substring(1, fields[5].length - 1)
        val sourceCode =
            session.load(SourceCode::class.java, Src_Cd)
        item.addSourceCode(sourceCode)
        if (fields[6].length > 2) {
            val Deriv_Cd = fields[6].substring(1, fields[6].length - 1)
            val dataDerivation = session.load(DataDerivation::class.java, Deriv_Cd)
            item.addDataDerivation(dataDerivation)
        }
        if (fields[7].length > 2) {
            val Ref_NDB_No = fields[7].substring(1, fields[7].length - 1)
            val refFoodDescription =
                session.load(FoodDescription::class.java, Ref_NDB_No)
            item.refFoodDescription = refFoodDescription
        }
        if (fields[8].length > 2) item.add_Nutr_Mark = fields[8].substring(1, fields[8].length - 1)
        if (fields[9].length > 0) item.num_Studies = fields[9].toInt()
        if (fields[10].length > 0) item.min = fields[10].toDouble()
        if (fields[11].length > 0) item.max = fields[11].toDouble()
        if (fields[12].length > 0) item.dF = fields[12].toInt()
        if (fields[13].length > 0) item.low_EB = fields[13].toDouble()
        if (fields[14].length > 0) item.up_EB = fields[14].toDouble()
        if (fields[15].length > 2) item.stat_cmt = fields[15].substring(1, fields[15].length - 1)
        if (fields[16].length > 0) item.addMod_Date = fields[16]
        if (fields[17].length > 0) item.cC = fields[17]
        foodDescription.addNutrientData(item)
        nutrientDefinition.addNutrientData(item)
        session.save(item)
    }
}