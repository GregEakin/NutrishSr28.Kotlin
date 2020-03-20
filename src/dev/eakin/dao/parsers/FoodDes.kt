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

import dev.eakin.dao.entities.FoodDescription
import dev.eakin.dao.entities.FoodGroup
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object FoodDes {
    const val Filename = ".\\data\\FOOD_DES.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(session, line)) }
        }
    }

    private fun parseLine(session: Session, line: String): FoodDescription {
        val fields = line.split("\\^".toRegex())
        return parseFoodDescription(session, fields)
    }

    private fun parseFoodDescription(session: Session, fields: List<String>): FoodDescription {
        val item = FoodDescription()
        item.nDB_No = fields[0].removeSurrounding("~")
        val foodGroupId = fields[1].removeSurrounding("~")
        val foodGroup = session.load(FoodGroup::class.java, foodGroupId)
        item.addFoodGroup(foodGroup)
        item.long_Desc = fields[2].removeSurrounding("~")
        item.shrt_Desc = fields[3].removeSurrounding("~")
        item.comName = fields[4].removeSurrounding("~").ifBlank { null }
        item.manufacName = fields[5].removeSurrounding("~").ifBlank { null }
        item.survey = fields[6].removeSurrounding("~").ifBlank { null }
        item.ref_desc = fields[7].removeSurrounding("~").ifBlank { null }
        item.refuse = fields[8].toIntOrNull()
        item.sciName = fields[9].removeSurrounding("~").ifBlank { null }
        item.n_Factor = fields[10].toDoubleOrNull()
        item.pro_Factor = fields[11].toDoubleOrNull()
        item.fat_Factor = fields[12].toDoubleOrNull()
        item.cHO_Factor = fields[13].toDoubleOrNull()
        return item
    }
}