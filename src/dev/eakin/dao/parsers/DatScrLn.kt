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

// After DataSource
// After NutrientData
object DatScrLn {
    const val Filename = ".\\data\\DATSRCLN.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(session, line)) }
        }
    }

    private fun parseLine(session: Session, line: String): NutrientData {
        val fields = line.split("\\^".toRegex())
        val NDB_No = fields[0].removeSurrounding("~")
        val foodDescription = session.load(FoodDescription::class.java, NDB_No)
        val Nutr_No = fields[1].removeSurrounding("~")
        val nutrientDefinition = session.load(NutrientDefinition::class.java, Nutr_No)
        val nutrientDataKey = NutrientDataKey(foodDescription, nutrientDefinition)
        val nutrientData = session.load(NutrientData::class.java, nutrientDataKey)
        val DataSrc_ID = fields[2].removeSurrounding("~")
        val dataSource = session.load(DataSource::class.java, DataSrc_ID)
        nutrientData.addDataSource(dataSource)
        return nutrientData
    }
}