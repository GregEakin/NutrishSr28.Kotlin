/*
 * Copyright (c) 2019. Greg Eakin
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
package dev.eakin.server

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Shutdown {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver")
            DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/nutrish", "SA", "")
                .use { con -> sqlShutdown(con) }
        } catch (e: Exception) {
            e.printStackTrace(System.out)
        }
    }

    @Throws(SQLException::class)
    fun sqlShutdown(con: Connection) {
        con.createStatement().use { statement -> statement.execute("SHUTDOWN") }
    }
}