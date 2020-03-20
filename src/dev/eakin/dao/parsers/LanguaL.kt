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
import dev.eakin.dao.entities.Language
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.SQLException

object LanguaL {
    const val Filename = ".\\data\\LANGUAL.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(session, line)) }
        }
    }

    fun parseLine(session: Session, line: String): Language {
        val fields = line.split("\\^".toRegex())
        val NDB_no = fields[0].removeSurrounding("~")
        val foodDescription = session.load(FoodDescription::class.java, NDB_no)
        val factor_code = fields[1].removeSurrounding("~")
        val language = session.load(Language::class.java, factor_code)
        language.addFoodDescription(foodDescription)
        return language
    }

    @Throws(SQLException::class)
    fun sqlSelectRows(con: Connection): Int {
        con.createStatement().use { stmt ->
            val sql = "SELECT * FROM LANGUAL"
            val result = stmt.executeQuery(sql)
            var count = 0
            while (result.next()) {
                val x0 = result.getString("NDB_No")
                val x1 = result.getString("Factor_Code")
                println("$x0, $x1")
                count++
            }
            return count
        }
    }
}