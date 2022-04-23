import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlSelectParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.ParameterizedOutputVisitorUtils;
import com.alibaba.druid.sql.visitor.ParameterizedVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.support.opds.udf.SqlFormat;
import com.alibaba.druid.wall.*;
import com.alibaba.druid.wall.spi.MySqlWallProvider;
import com.alibaba.druid.wall.spi.MySqlWallVisitor;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.channels.spi.SelectorProvider;
import java.util.List;

@RunWith(JUnit4.class)
public class TestMain {
    String sql = "select * from t where id=1 and name='ming' and 1=1 limit 10,50 order by age;" +
            "SELECT * FROM user";
    DbType dbType = DbType.mysql;

    @Test
    public void TestSqlParse() {

        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement sqlStatement = parser.parseStatement();

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        sqlStatement.accept(visitor);

        System.out.println("getTables:" + visitor.getTables());
        System.out.println("getParameters:" + visitor.getParameters());
        System.out.println("getOrderByColumns:" + visitor.getOrderByColumns());
        System.out.println("getGroupByColumns:" + visitor.getGroupByColumns());

    }


    @Test
    public void TestFuck() {
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, dbType);
        System.out.println(sqlStatements.size());

//        MySqlWallVisitor visitor = new MySqlWallVisitor(new MySqlWallProvider(new WallConfig()));
//        StringBuffer stringBuffer = new StringBuffer();
//        MySqlOutputVisitor visitor = new MySqlOutputVisitor(stringBuffer);

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        for (int i = 0; i < sqlStatements.size(); i++) {
            SQLStatement sqlStatement = sqlStatements.get(i);
            sqlStatement.accept(visitor);
            if(sqlStatement instanceof SQLSelectStatement){
                System.out.println("这是查询语句");
                SQLSelectStatement sqlStatement1 = (SQLSelectStatement) sqlStatement;

            }
            visitor.getTables().forEach((name, tableStat) -> {
                System.out.println(name);
                System.out.println(tableStat);
            });
        }
    }


    /**
     * sql 格式化
     */
    @Test
    public void TestSqlFormat() {
        String format = SQLUtils.format(sql, dbType, SQLUtils.DEFAULT_FORMAT_OPTION);
        System.out.println(format);
    }

    /**
     * sql 参数化
     */
    @Test
    public void TestSqlParameterize() {
        List<Object> objects = Lists.newArrayList();
        String parameterize = ParameterizedOutputVisitorUtils.parameterize(sql, dbType, objects);
        System.out.println(parameterize);
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    /**
     * sql 分页
     */
    @Test
    public void TestSqlPage() {
        //生成对应分页语句查询count的语句
        String count = PagerUtils.count(sql, dbType);
        System.out.println(count);
        System.out.println("========================================");
        //计算分页数据量
        System.out.println(PagerUtils.getLimit(sql, dbType));
        System.out.println("========================================");
        System.out.println(PagerUtils.limit(sql, dbType, 1, 40));

    }

    /**
     * sql 注入拦截
     */
    @Test
    public void TestWall() {
        WallConfig wallConfig = new WallConfig();
        MySqlWallProvider mySqlWallProvider = new MySqlWallProvider(wallConfig);
        WallCheckResult check = mySqlWallProvider.check(sql);
        List<Violation> violations = check.getViolations();
        System.out.println(violations);
        System.out.println(violations.get(0).getMessage());
    }


}
