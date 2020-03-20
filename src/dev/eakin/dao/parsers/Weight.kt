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
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(praseLine(session, line)) }
        }
    }

    private fun praseLine(session: Session, line: String): Weight {
        val fields = line.split("\\^".toRegex())
        val item = Weight()

        // NDB_No A 5* N 5-digit Nutrient Databank number that uniquely identifies a food item.
        // Seq A 2* N Sequence number.
        val foodDescriptionId = fields[0].removeSurrounding("~")
        val foodDescription = session.load(FoodDescription::class.java, foodDescriptionId)
        val seq = fields[1]
        item.weightKey = WeightKey(foodDescription, seq)

        // Amount N 5.3 N Unit modifier (for example, 1 in “1 cup”).
        item.amount = fields[2].toDouble()

        // Msre_Desc A 84 N Description (for example, cup, diced, and 1-inch pieces).
        item.msre_Desc = fields[3].removeSurrounding("~")

        // Gm_Wgt N 7.1 N Gram weight.
        item.gm_Wgt = fields[4].toDouble()

        // Num_Data_Pts N 3 Y Number of data points.
        item.num_Data_Pts = fields[5].toIntOrNull()

        // Std_Dev N 7.3 Y Standard deviation.
        item.std_Dev = fields[6].toDoubleOrNull()
        foodDescription.addWeight(item)
        return item
    }
}