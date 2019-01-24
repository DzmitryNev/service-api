/*
 * Copyright 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.store;

import com.epam.ta.reportportal.config.DatabaseConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@Import(DatabaseConfiguration.class)
@PropertySource("classpath:test-application.properties")
public class InMemoryDbConfig {

	/*@Autowired
	private Provider<DataSource> dataSource;

	@Bean
	public DatabaseConnection dbunitConnection() throws SQLException, DatabaseUnitException {
		DatabaseConnection connection = new DatabaseConnection(dataSource.get().getConnection());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory() {
			@Override
			public boolean isEnumType(String sqlTypeName) {
				return sqlTypeName.endsWith("enum");
			}

			@Override
			public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
				if (isEnumType(sqlTypeName)) {
					sqlType = Types.OTHER;
				}
				return super.createDataType(sqlType, sqlTypeName);
			}
		});
		return connection;
	}*/

}
