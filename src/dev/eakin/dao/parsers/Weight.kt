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
import dev.eakin.dao.entities.Weight
import dev.eakin.dao.entities.WeightKey
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object Weight {
    const val Filename = ".\\data\\WEIGHT.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String ->
                praseLine(session, line)
            }
        }
    }

    private fun praseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = Weight()

        // NDB_No A 5* N 5-digit Nutrient Databank number that uniquely identifies a food item.
        // Seq A 2* N Sequence number.
        val foodDescriptionId = fields[0].substring(1, fields[0].length - 1)
        val foodDescription =
            session.load(FoodDescription::class.java, foodDescriptionId)
        item.weightKey = WeightKey(foodDescription, fields[1])

        // Amount N 5.3 N Unit modifier (for example, 1 in “1 cup”).
        item.amount = fields[2].toDouble()

        // Msre_Desc A 84 N Description (for example, cup, diced, and 1-inch pieces).
        val description = fields[3].substring(1, fields[3].length - 1)
        item.msre_Desc = description

        // Gm_Wgt N 7.1 N Gram weight.
        item.gm_Wgt = fields[4].toDouble()

        // Num_Data_Pts N 3 Y Number of data points.
        if (fields[5].length > 0) item.num_Data_Pts = fields[5].toInt()

        // Std_Dev N 7.3 Y Standard deviation.
        if (fields[6].length > 0) item.std_Dev = fields[6].toDouble()
        foodDescription.addWeight(item)
        session.save(item)
    }
}