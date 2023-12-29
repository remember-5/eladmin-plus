package com.remember5.system.intercepter;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGDeleteStatement;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGInsertStatement;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGUpdateStatement;
import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public class MyIntercepter implements StatementInspector {

    public static final String TENANT_ID = "tenant_id";
    public static final String TEST_USER_TENANT_ID = "abcd";

    @Override
    public String inspect(String s) {
        // 解析sql，可以线判断数据库类型
        final PGSQLStatementParser pgsqlStatementParser = new PGSQLStatementParser(s);
        final SQLStatement sqlStatement = pgsqlStatementParser.parseStatement();

        // select
        if (sqlStatement instanceof PGSelectStatement) {
            final PGSelectStatement pgSelectStatement = (PGSelectStatement) sqlStatement;
            // 需要加单引号
            pgSelectStatement.addWhere(new SQLIdentifierExpr(TENANT_ID + " = '" + TEST_USER_TENANT_ID + "'"));
            return pgSelectStatement.toString();
        }


        // insert
        if (sqlStatement instanceof PGInsertStatement) {
            final PGInsertStatement pgInsertStatement = (PGInsertStatement) sqlStatement;
            pgInsertStatement.getColumns().add(new SQLIdentifierExpr(TENANT_ID));
            pgInsertStatement.getValues().getValues().add(new SQLCharExpr(TEST_USER_TENANT_ID));
            return pgInsertStatement.toString();
        }

        // update
        if (sqlStatement instanceof PGUpdateStatement) {
            final PGUpdateStatement pgUpdateStatement = (PGUpdateStatement) sqlStatement;
            final SQLExpr where = pgUpdateStatement.getWhere();
            if (where.toString().isEmpty()) {
                pgUpdateStatement.setWhere(new SQLIdentifierExpr(TENANT_ID + " = '" + TEST_USER_TENANT_ID + "'"));
            } else {
                pgUpdateStatement.setWhere(new SQLIdentifierExpr(where.toString() + " AND " + TENANT_ID + " = '" + TEST_USER_TENANT_ID + "'"));
            }

            return pgUpdateStatement.toString();
        }

        // delete
        if (sqlStatement instanceof PGDeleteStatement) {
            final PGDeleteStatement pgDeleteStatement = (PGDeleteStatement) sqlStatement;
            final SQLExpr where = pgDeleteStatement.getWhere();
            if (where.toString().isEmpty()) {
                pgDeleteStatement.setWhere(new SQLIdentifierExpr(TENANT_ID + " = '" + TEST_USER_TENANT_ID + "'"));
            } else {
                pgDeleteStatement.setWhere(new SQLIdentifierExpr(where.toString() + " AND " + TENANT_ID + " = '" + TEST_USER_TENANT_ID + "'"));
            }
            return pgDeleteStatement.toString();
        }


        return s;
    }


}
