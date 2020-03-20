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
package dev.eakin.flatfile.parsers

import dev.eakin.flatfile.entities.Abbreviations
import org.hibernate.Session
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object Abbrev {
    const val Filename = ".\\data\\ABBREV.txt"

    @Throws(IOException::class)
    fun parseFile(session: Session) {
        val path = Paths.get(Filename)
        Files.lines(path, StandardCharsets.US_ASCII).use { lines ->
            lines.forEach { line: String -> session.save(parseLine(line)) }
        }
    }

    private fun parseLine(line: String): Abbreviations {
        val fields = line.split("\\^".toRegex())
        return parseAbbreviation(fields)
    }

    private fun parseAbbreviation(fields: List<String>): Abbreviations {
        val item = Abbreviations()
        item.nDB_No = fields[0].removeSurrounding("~")
        item.shrt_Desc = fields[1].removeSurrounding("~")
        item.water = fields[2].toDoubleOrNull()
        item.energ_Kcal = fields[3].toIntOrNull()
        item.protein = fields[4].toDoubleOrNull()
        item.lipid_Tot = fields[5].toDoubleOrNull()
        item.ash = fields[6].toDoubleOrNull()
        item.carbohydrt = fields[7].toDoubleOrNull()
        item.fiber_TD = fields[8].toDoubleOrNull()
        item.sugar_Tot = fields[9].toDoubleOrNull()
        item.calcium = fields[10].toIntOrNull()
        item.iron = fields[11].toDoubleOrNull()
        item.magnesium = fields[12].toIntOrNull()
        item.phosphorus = fields[13].toIntOrNull()
        item.potassium = fields[14].toIntOrNull()
        item.sodium = fields[15].toIntOrNull()
        item.zinc = fields[16].toDoubleOrNull()
        item.copper = fields[17].toDoubleOrNull()
        item.manganese = fields[18].toDoubleOrNull()
        item.selenium = fields[19].toDoubleOrNull()
        item.vit_C = fields[20].toDoubleOrNull()
        item.thiamin = fields[21].toDoubleOrNull()
        item.riboflavin = fields[22].toDoubleOrNull()
        item.niacin = fields[23].toDoubleOrNull()
        item.panto_acid = fields[24].toDoubleOrNull()
        item.vit_B6 = fields[25].toDoubleOrNull()
        item.folate_Tot = fields[26].toIntOrNull()
        item.folic_acid = fields[27].toIntOrNull()
        item.food_Folate = fields[28].toIntOrNull()
        item.folate_DFE = fields[29].toIntOrNull()
        item.choline_Tot = fields[30].toDoubleOrNull()
        item.vit_B12 = fields[31].toDoubleOrNull()
        item.vit_A_IU = fields[32].toIntOrNull()
        item.vit_A_RAE = fields[33].toIntOrNull()
        item.retinol = fields[34].toIntOrNull()
        item.alpha_Carot = fields[35].toIntOrNull()
        item.beta_Carot = fields[36].toIntOrNull()
        item.beta_Crypt = fields[37].toIntOrNull()
        item.lycopene = fields[38].toIntOrNull()
        item.lut_Zea = fields[39].toIntOrNull()
        item.vit_E = fields[40].toDoubleOrNull()
        item.vit_D_mcg = fields[41].toDoubleOrNull()
        item.vit_D_IU = fields[42].toIntOrNull()
        item.vit_K = fields[43].toDoubleOrNull()
        item.fA_Sat = fields[44].toDoubleOrNull()
        item.fA_Mono = fields[45].toDoubleOrNull()
        item.fA_Poly = fields[46].toDoubleOrNull()
        item.cholestrl = fields[47].toDoubleOrNull()
        item.gmWt_1 = fields[48].toDoubleOrNull()
        item.gmWt_Desc1 = fields[49].removeSurrounding("~").ifBlank { null }
        item.gmWt_2 = fields[50].toDoubleOrNull()
        item.gmWt_Desc2 = fields[51].removeSurrounding("~").ifBlank { null }
        item.refuse_Pct = fields[52].toIntOrNull()
        return item
    }
}