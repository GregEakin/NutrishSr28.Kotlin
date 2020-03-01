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
package dev.eakin.dao.utilities

import dev.eakin.dao.entities.*
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import org.junit.jupiter.api.extension.*

class NutrishRepositoryExtension : BeforeAllCallback, BeforeTestExecutionCallback,
    AfterTestExecutionCallback, AfterAllCallback, ParameterResolver {
    private var sessionFactory: SessionFactory? = null
    private var session: Session? = null
    private var transaction: Transaction? = null
    override fun beforeAll(extensionContext: ExtensionContext) {
        sessionFactory = createSessionFactory()
        session = createSession()
    }

    override fun beforeTestExecution(extensionContext: ExtensionContext) {
        transaction = session!!.beginTransaction()
    }

    override fun afterTestExecution(extensionContext: ExtensionContext) {
        try {
            transaction!!.rollback()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun afterAll(extensionContext: ExtensionContext) {
        try {
            try {
                session?.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            sessionFactory?.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @Throws(ParameterResolutionException::class)
    override fun supportsParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean {
        return parameterContext.parameter.type == Session::class.java
    }

    @Throws(ParameterResolutionException::class)
    override fun resolveParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Any {
        return session!!
    }

    private fun createSessionFactory(): SessionFactory {
        val configuration = Configuration().configure("resources\\hibernate.cfg.xml")
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
        configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
        configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost/nutrish")
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate")
        configuration
            .addAnnotatedClass(DataDerivation::class.java)
            .addAnnotatedClass(DataSource::class.java)
            .addAnnotatedClass(FoodDescription::class.java)
            .addAnnotatedClass(FoodGroup::class.java)
            .addAnnotatedClass(Footnote::class.java)
            .addAnnotatedClass(Language::class.java)
            .addAnnotatedClass(NutrientData::class.java)
            .addAnnotatedClass(NutrientDefinition::class.java)
            .addAnnotatedClass(SourceCode::class.java)
            .addAnnotatedClass(Weight::class.java)
        return configuration.buildSessionFactory()
    }

    fun createSession(): Session? {
        session = sessionFactory!!.openSession()
        return session
    }
}