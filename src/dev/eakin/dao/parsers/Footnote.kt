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
import dev.eakin.dao.entities.Footnote
import dev.eakin.dao.entities.NutrientDefinition
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

// after FoodDescription
// after NutrientDefinition
object Footnote {
    const val Filename = ".\\data\\FOOTNOTE.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String ->
                parseLine(session, line)
            }
        }
    }

    private fun parseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = parseFootnote(session, fields)
        session.save(item)
    }

    private fun parseFootnote(
        session: Session,
        fields: Array<String>
    ): Footnote {
            val item = Footnote()
        val foodDescriptionId = fields[0].substring(1, fields[0].length - 1)
        val foodDescription =
            session.load(FoodDescription::class.java, foodDescriptionId)
        item.foodDescription = foodDescription
        item.footnt_No = fields[1].substring(1, fields[1].length - 1)
        item.footnt_Typ = fields[2].substring(1, fields[2].length - 1)
        if (fields[3].length > 2) {
            val Nutr_No = fields[3].substring(1, fields[3].length - 1)
            val nutrientDefinition =
                session.load(NutrientDefinition::class.java, Nutr_No)
            item.addNutrientDefinition(nutrientDefinition)
        }
        item.footnt_Txt = fields[4].substring(1, fields[4].length - 1)
        foodDescription.addFootnote(item)
        return item
    }
}