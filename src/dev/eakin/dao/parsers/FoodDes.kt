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
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String -> parseLine(session, line) }
        }
    }

    private fun parseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = parseFoodDescription(session, fields)
        session.save(item)
    }

    private fun parseFoodDescription(
        session: Session,
        fields: Array<String>
    ): FoodDescription {
        val item = FoodDescription()
        item.nDB_No = fields[0].substring(1, fields[0].length - 1)
        val foodGroupId = fields[1].substring(1, fields[1].length - 1)
        val foodGroup = session.load(FoodGroup::class.java, foodGroupId)
        item.addFoodGroup(foodGroup)
        item.long_Desc = fields[2].substring(1, fields[2].length - 1)
        item.shrt_Desc = fields[3].substring(1, fields[3].length - 1)
        if (fields[4].length > 2) item.comName = fields[4].substring(1, fields[4].length - 1)
        if (fields[5].length > 2) item.manufacName = fields[5].substring(1, fields[5].length - 1)
        if (fields[6].length > 2) item.survey = fields[6].substring(1, fields[6].length - 1)
        if (fields[7].length > 2) item.ref_desc = fields[7].substring(1, fields[7].length - 1)
        if (fields[8].length > 0) item.refuse = fields[8].toInt()
        if (fields[9].length > 2) item.sciName = fields[9].substring(1, fields[9].length - 1)
        if (fields[10].length > 0) item.n_Factor = fields[10].toDouble()
        if (fields[11].length > 0) item.pro_Factor = fields[11].toDouble()
        if (fields[12].length > 0) item.fat_Factor = fields[12].toDouble()
        if (fields[13].length > 0) item.cHO_Factor = fields[13].toDouble()
        return item
    }
}