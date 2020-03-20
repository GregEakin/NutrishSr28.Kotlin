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
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(session, line)) }
        }
    }

    private fun parseLine(session: Session, line: String): Footnote {
        val fields = line.split("\\^".toRegex())
        return parseFootnote(session, fields)
    }

    private fun parseFootnote(session: Session, fields: List<String>): Footnote {
        val item = Footnote()
        val foodDescriptionId = fields[0].removeSurrounding("~")
        val foodDescription = session.load(FoodDescription::class.java, foodDescriptionId)
        item.foodDescription = foodDescription
        item.footnt_No = fields[1].removeSurrounding("~")
        item.footnt_Typ = fields[2].removeSurrounding("~")
        val Nutr_No = fields[3].removeSurrounding("~").ifBlank { null }
        if (Nutr_No != null) {
            val nutrientDefinition = session.load(NutrientDefinition::class.java, Nutr_No)
            item.addNutrientDefinition(nutrientDefinition)
        }
        item.footnt_Txt = fields[4].removeSurrounding("~")
        foodDescription.addFootnote(item)
        return item
    }
}