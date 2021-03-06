/**
 * Copyright (C) 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.flyway.core.dbsupport.oracle;

import com.googlecode.flyway.core.dbsupport.SqlScript;
import com.googlecode.flyway.core.dbsupport.SqlStatement;
import com.googlecode.flyway.core.util.ClassPathResource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for OracleSqlScript.
 */
public class OracleSqlScriptSmallTest {
    @Test
    public void parseSqlStatements() throws Exception {
        String source = new ClassPathResource("migration/dbsupport/oracle/sql/placeholders/V1__Placeholders.sql").loadAsString("UTF-8");

        SqlScript sqlScript = new SqlScript(source, new OracleDbSupport(null));
        List<SqlStatement> sqlStatements = sqlScript.getSqlStatements();
        assertEquals(3, sqlStatements.size());
        assertEquals(18, sqlStatements.get(0).getLineNumber());
        assertEquals(27, sqlStatements.get(1).getLineNumber());
        assertEquals(32, sqlStatements.get(2).getLineNumber());
        assertEquals("COMMIT", sqlStatements.get(2).getSql());
    }

    @Test
    public void parseSqlStatementsWithInlineCommentsInsidePlSqlBlocks() throws Exception {
        String source = new ClassPathResource("migration/dbsupport/oracle/sql/function/V2__FunctionWithConditionals.sql").loadAsString("UTF-8");

        SqlScript sqlScript = new SqlScript(source, new OracleDbSupport(null));
        List<SqlStatement> sqlStatements = sqlScript.getSqlStatements();
        assertEquals(1, sqlStatements.size());
        assertEquals(18, sqlStatements.get(0).getLineNumber());
        assertTrue(sqlStatements.get(0).getSql().contains("/* for the rich */"));
    }

    @Test
    public void parseFunctionsAndProcedures() throws Exception {
        String source = new ClassPathResource("migration/dbsupport/oracle/sql/function/V1__Function.sql").loadAsString("UTF-8");

        SqlScript sqlScript = new SqlScript(source, new OracleDbSupport(null));
        List<SqlStatement> sqlStatements = sqlScript.getSqlStatements();
        assertEquals(3, sqlStatements.size());
        assertEquals(17, sqlStatements.get(0).getLineNumber());
        assertEquals(26, sqlStatements.get(1).getLineNumber());
        assertEquals(34, sqlStatements.get(2).getLineNumber());
        assertEquals("COMMIT", sqlStatements.get(2).getSql());
    }

    @Test
    public void parseQQuotes() throws Exception {
        String source = new ClassPathResource("migration/dbsupport/oracle/sql/qquote/V1__Q_Quote.sql").loadAsString("UTF-8");

        SqlScript sqlScript = new SqlScript(source, new OracleDbSupport(null));
        List<SqlStatement> sqlStatements = sqlScript.getSqlStatements();
        assertEquals(10, sqlStatements.size());
    }
}
