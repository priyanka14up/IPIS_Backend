/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: PostGreSQLDialect
 */
package com.innobitsysytems.ipis.model.reports;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class PostGreSQLDialect extends PostgreSQL94Dialect {

	public PostGreSQLDialect() {

		this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}

}