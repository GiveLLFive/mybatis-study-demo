package ex01.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.annotation.Resource;

@Configuration
@MapperScan("ex01.mapper")
public class MybatisConfig {

    private static final String mapperLocations = "classpath:mapper/*.xml";

    @Resource
    private DataSourceConfig dataSourceConfig;

    @Bean
    public SqlSessionFactory createSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 配置mapper的扫描，找到所有的mapper.xml映射文件
        org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        factoryBean.setMapperLocations(resources);
        factoryBean.setDataSource(dataSourceConfig.getDataSource());

        factoryBean.setPlugins(new Interceptor[]{new PageHelper()});
        return factoryBean.getObject();
    }

    @Bean
    public TransactionManager getTransactionManager (){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSourceConfig.getDataSource());
        return dataSourceTransactionManager;
    }
}
