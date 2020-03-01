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
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String -> parseLine(session, line) }
        }
    }

    private fun parseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = parseDataSource(fields)
        session.save(item)
    }

    private fun parseDataSource(fields: Array<String>): DataSource {
        val item = DataSource()
        item.dataSrc_ID = fields[0].substring(1, fields[0].length - 1)
        if (fields[1].length > 2) item.authors = fields[1].substring(1, fields[1].length - 1)
        item.title = fields[2].substring(1, fields[2].length - 1)
        if (fields[3].length > 2) item.year = fields[3].substring(1, fields[3].length - 1)
        if (fields[4].length > 2) item.journal = fields[4].substring(1, fields[4].length - 1)
        if (fields[5].length > 2) item.vol_City = fields[5].substring(1, fields[5].length - 1)
        if (fields[6].length > 2) item.issue_State = fields[6].substring(1, fields[6].length - 1)
        if (fields[7].length > 2) item.start_Page = fields[7].substring(1, fields[7].length - 1)
        if (fields[8].length > 2) item.end_Page = fields[8].substring(1, fields[8].length - 1)
        return item
    }
}