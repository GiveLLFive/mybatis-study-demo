package ex01;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ex01.entity.Blog;
import ex01.mapper.BlogMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author lilei
 * @since 2021/10/1
 */
public class MybatisDemo {

    private Configuration configuration;

    @Before
    public void prepareConfiguration(){
        // 准备工作：创建数据源 DataSource
        DataSource dataSource = getDataSource();
        // 准备工作：实例化一个 TransactionFactory
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        // 准备工作：通过持有数据源 DataSource 引用和 TransactionFactory 引用，创建环境配置 Environment 实例
        Environment environment = new Environment("development", transactionFactory, dataSource);
        // 准备工作：持有环境配置 Environment 引用，创建基本的 Configuration 实例
        configuration = new Configuration(environment);
        // 第一步：把 BlogMapper interface 添加到 mapperRegistry 注册仓库中
        configuration.addMapper(BlogMapper.class);

    }

    @Test
    public void baseMapperTest() {
        // 第二步：通过 SqlSessionFactoryBuilder.build 构建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        // 第三步：通过 SqlSessionFactory 工厂创建一个 SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 第四步：从 session 中拿到 BlogMapper 的代理对象
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            // 第五步：代理对象通过反射进行动态代理，最后得到返回结果
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pageHelperMapperTest() {
        /** 很重要的一步：采用编程式的方式添加拦截器*/
        configuration.addInterceptor(new PageHelper());
        // 第二步：通过 SqlSessionFactoryBuilder.build 构建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        // 第三步：通过 SqlSessionFactory 工厂创建一个 SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 第四步：添加拦截器需要的参数，count 参数代表是否需要查询满足条件的所有行数，为防止打断点的时候受到干扰，这里设置为 false
            Page page = PageHelper.startPage(1, 1, false);
            // 第五步：从 session 中拿到 BlogMapper 的代理对象
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            // 第六步：代理对象通过反射进行动态代理，最后得到返回结果
            List<Blog> hasPageList = mapper.listByKeyword("水");
            System.out.println("分页之后查询的行数：" + hasPageList.size());
            // 对比结果：代理对象通过反射进行动态代理，最后得到返回结果
            List<Blog> noPageList = mapper.listByKeyword("水");
            System.out.println("未分页查询的行数：" + noPageList.size());
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
