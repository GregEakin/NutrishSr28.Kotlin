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
        Files.lines(path, StandardCharsets.ISO_8859_1).use { lines ->
            lines.forEach { line: String -> parseLine(session, line) }
        }
    }

    private fun parseLine(session: Session, line: String) {
        val fields =
            line.split("\\^".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val item = parseAbbreviation(fields)
        session.save(item)
    }

    private fun parseAbbreviation(fields: Array<String>): Abbreviations {
        val item = Abbreviations()
        item.nDB_No = fields[0].substring(1, fields[0].length - 1)
        item.shrt_Desc = fields[1].substring(1, fields[1].length - 1)
        if (fields[2].length > 0) item.water = fields[2].toDouble()
        if (fields[3].length > 0) item.energ_Kcal = fields[3].toInt()
        if (fields[4].length > 0) item.protein = fields[4].toDouble()
        if (fields[5].length > 0) item.lipid_Tot = fields[5].toDouble()
        if (fields[6].length > 0) item.ash = fields[6].toDouble()
        if (fields[7].length > 0) item.carbohydrt = fields[7].toDouble()
        if (fields[8].length > 0) item.fiber_TD = fields[8].toDouble()
        if (fields[9].length > 0) item.sugar_Tot = fields[9].toDouble()
        if (fields[10].length > 0) item.calcium = fields[10].toInt()
        if (fields[11].length > 0) item.iron = fields[11].toDouble()
        if (fields[12].length > 0) item.magnesium = fields[12].toInt()
        if (fields[13].length > 0) item.phosphorus = fields[13].toInt()
        if (fields[14].length > 0) item.potassium = fields[14].toInt()
        if (fields[15].length > 0) item.sodium = fields[15].toInt()
        if (fields[16].length > 0) item.zinc = fields[16].toDouble()
        if (fields[17].length > 0) item.copper = fields[17].toDouble()
        if (fields[18].length > 0) item.manganese = fields[18].toDouble()
        if (fields[19].length > 0) item.selenium = fields[19].toDouble()
        if (fields[20].length > 0) item.vit_C = fields[20].toDouble()
        if (fields[21].length > 0) item.thiamin = fields[21].toDouble()
        if (fields[22].length > 0) item.riboflavin = fields[22].toDouble()
        if (fields[23].length > 0) item.niacin = fields[23].toDouble()
        if (fields[24].length > 0) item.panto_acid = fields[24].toDouble()
        if (fields[25].length > 0) item.vit_B6 = fields[25].toDouble()
        if (fields[26].length > 0) item.folate_Tot = fields[26].toInt()
        if (fields[27].length > 0) item.folic_acid = fields[27].toInt()
        if (fields[28].length > 0) item.food_Folate = fields[28].toInt()
        if (fields[29].length > 0) item.folate_DFE = fields[29].toInt()
        if (fields[30].length > 0) item.choline_Tot = fields[30].toDouble()
        if (fields[31].length > 0) item.vit_B12 = fields[31].toDouble()
        if (fields[32].length > 0) item.vit_A_IU = fields[32].toInt()
        if (fields[33].length > 0) item.vit_A_RAE = fields[33].toInt()
        if (fields[34].length > 0) item.retinol = fields[34].toInt()
        if (fields[35].length > 0) item.alpha_Carot = fields[35].toInt()
        if (fields[36].length > 0) item.beta_Carot = fields[36].toInt()
        if (fields[37].length > 0) item.beta_Crypt = fields[37].toInt()
        if (fields[38].length > 0) item.lycopene = fields[38].toInt()
        if (fields[39].length > 0) item.lut_Zea = fields[39].toInt()
        if (fields[40].length > 0) item.vit_E = fields[40].toDouble()
        if (fields[41].length > 0) item.vit_D_mcg = fields[41].toDouble()
        if (fields[42].length > 0) item.vit_D_IU = fields[42].toInt()
        if (fields[43].length > 0) item.vit_K = fields[43].toDouble()
        if (fields[44].length > 0) item.fA_Sat = fields[44].toDouble()
        if (fields[45].length > 0) item.fA_Mono = fields[45].toDouble()
        if (fields[46].length > 0) item.fA_Poly = fields[46].toDouble()
        if (fields[47].length > 0) item.cholestrl = fields[47].toDouble()
        if (fields[48].length > 0) item.gmWt_1 = fields[48].toDouble()
        if (fields[49].length > 2) item.gmWt_Desc1 = fields[49].substring(1, fields[49].length - 1)
        if (fields[50].length > 0) item.gmWt_2 = fields[50].toDouble()
        if (fields[51].length > 2) item.gmWt_Desc2 = fields[51].substring(1, fields[51].length - 1)
        if (fields[52].length > 0) item.refuse_Pct = fields[52].toInt()
        return item
    }
}