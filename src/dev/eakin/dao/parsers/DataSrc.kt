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

import dev.eakin.dao.entities.DataSource
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object DataSrc {
    const val Filename = ".\\data\\DATA_SRC.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).forEach { line: String -> session.save(parseLine(line)) }
    }

    private fun parseLine(line: String): DataSource {
        val fields = line.split("\\^".toRegex())
        return parseDataSource(fields)
    }

    private fun parseDataSource(fields: List<String>): DataSource {
        val item = DataSource()
        item.dataSrc_ID = fields[0].removeSurrounding("~")
        item.authors = fields[1].removeSurrounding("~").ifBlank { null }
        item.title = fields[2].removeSurrounding("~")
        item.year = fields[3].removeSurrounding("~").ifBlank { null }
        item.journal = fields[4].removeSurrounding("~").ifBlank { null }
        item.vol_City = fields[5].removeSurrounding("~").ifBlank { null }
        item.issue_State = fields[6].removeSurrounding("~").ifBlank { null }
        item.start_Page = fields[7].removeSurrounding("~").ifBlank { null }
        item.end_Page = fields[8].removeSurrounding("~").ifBlank { null }
        return item
    }
}