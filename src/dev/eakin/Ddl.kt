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

import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.hibernate.tool.schema.TargetType
import java.io.File
import java.util.*

object Ddl {
    @JvmStatic
    fun main(args: Array<String>) {
        val filename = "db-schema.hibernate5.ddl"
        val file = File(filename)
        val deleted = file.delete()
        println("Previous file deleted. $deleted")
        val serviceRegistry = StandardServiceRegistryBuilder()
            .configure("resources/hibernate.cfg.xml")
            .build()
        val metadata = MetadataSources(serviceRegistry)
            .buildMetadata()
        SchemaExport()
            .setOutputFile(filename)
            .create(EnumSet.of(TargetType.SCRIPT), metadata)
        metadata.buildSessionFactory().close()
    }
}