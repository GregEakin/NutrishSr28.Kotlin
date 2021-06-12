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

import dev.eakin.dao.entities.NutrientDefinition
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object NutrDef {
    const val Filename = ".\\data\\NUTR_DEF.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).forEach { line: String -> session.save(parseLine(line)) }
    }

    private fun parseLine(line: String): NutrientDefinition {
        val fields = line.split("\\^".toRegex())
        val item = NutrientDefinition()
        item.nutr_No = fields[0].removeSurrounding("~")
        item.units = fields[1].removeSurrounding("~")
        item.tagname = fields[2].removeSurrounding("~").ifBlank { null }
        item.nutrDesc = fields[3].removeSurrounding("~")
        item.num_Dec = fields[4].removeSurrounding("~")
        item.sR_Order = fields[5].removeSurrounding("~").toInt()
        return item
    }
}