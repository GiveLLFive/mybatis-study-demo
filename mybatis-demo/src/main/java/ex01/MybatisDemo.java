package ex01;

import com.alibaba.druid.pool.DruidDataSource;
import ex01.entity.Blog;
import ex01.mapper.BlogMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @author lilei
 * @since 2021/10/1
 */
public class MybatisDemo {

    public static void main(String[] args) {
        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataSource getDataSource() {
        // com.alibaba.druid.pool 使用的是阿里巴巴 druid 数据库连接池
        DruidDataSource dataSource = new DruidDataSource();
        // 本地数据库链接地址
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&userSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        // 数据库连接驱动
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // mysql 数据库用户名
        dataSource.setUsername("root");
        // mysql 数据库连接地址
        dataSource.setPassword("L143426l");
        return dataSource;
    }

}
