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

import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistry

object HibernateUtil {
    private fun buildSessionFactory(): SessionFactory {
        return try {
            val configuration = Configuration().configure(
                HibernateUtil::class.java.getResource("resources\\hibernate.cfg.xml")
            )
            val serviceRegistryBuilder = StandardServiceRegistryBuilder()
            serviceRegistryBuilder.applySettings(configuration.properties)
            val serviceRegistry: ServiceRegistry = serviceRegistryBuilder.build()
            configuration.buildSessionFactory(serviceRegistry)
        } catch (ex: Throwable) {
            System.err.println("Initial SessionFactory creation failed.$ex")
            throw ExceptionInInitializerError(ex)
        }
    }

    val sessionFactory: SessionFactory
        get() = LazyHolder.sessionFactory

    fun shutdown() {
        sessionFactory.close()
    }

    private object LazyHolder {
        val sessionFactory = buildSessionFactory()
    }
}