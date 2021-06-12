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
package dev.eakin

import dev.eakin.dao.parsers.*
import dev.eakin.dao.parsers.LanguaL.sqlSelectRows
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import java.sql.DriverManager
import java.sql.Connection

object DbLoad {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val configuration =
            Configuration().configure("resources\\hibernate.cfg.xml")
        val serviceRegistryBuilder = StandardServiceRegistryBuilder()
        serviceRegistryBuilder.applySettings(configuration.properties)
        configuration.buildSessionFactory().use { sessionFactory ->
            sessionFactory.openSession().use { session ->
                var transaction: Transaction
                try {
                    transaction = session.beginTransaction()
                    FdGroup.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    SrcCd.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    DerivCD.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    LangDesc.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    DataSrc.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    NutrDef.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    FoodDes.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    LanguaL.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    Weight.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    NutData.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    DatScrLn.parseFile(session)
                    transaction.commit()
                    transaction = session.beginTransaction()
                    Footnote.parseFile(session)
                    transaction.commit()
                } catch (ex: Exception) {
                    throw ex
                }
            }
        }
    }

    private fun DumpEntities(sessionFactory: SessionFactory) {
        sessionFactory.openSession().use { session ->
            println("querying all the managed entities...")
            val metamodel = session.sessionFactory.metamodel
            for (entityType in metamodel.entities) {
                val entityName = "from " + entityType.name
                val query = session.createQuery(entityName)
                println("executing: " + query.queryString)
                for (o in query.list()) {
                    println("  $o")
                }
            }
        }
    }

    fun dump() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver")
//            DriverManager.getConnection("jdbc:hsqldb:hsql:nutrhis", "SA", "").use { con: Connection -> sqlSelectRows(con) }
        } catch (e: Exception) {
            e.printStackTrace(System.out)
        }
    }
}