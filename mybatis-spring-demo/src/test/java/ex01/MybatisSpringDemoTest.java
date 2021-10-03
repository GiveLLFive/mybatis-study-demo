package ex01;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ex01.config.ApplicationConfig;
import ex01.entity.Blog;
import ex01.manage.BlogServiceManage;
import ex01.mapper.BlogMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

public class MybatisSpringDemoTest {

    AnnotationConfigApplicationContext ac = null;

    @Before
    public void prepareApplicationTest(){
        ac =  new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Arrays.stream(ac.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Test
    public void TestMybatisToMysqlIsConnected() {
        BlogMapper blogMapper = (BlogMapper) ac.getBean("blogMapper");
        System.out.println(blogMapper.selectBlog(1));
    }

    @Test
    public void TestPageHelperToMybatis(){
        BlogMapper blogMapper = (BlogMapper) ac.getBean("blogMapper");
        Page page = PageHelper.startPage(1, 1, false);
        List<Blog> list = blogMapper.listByKeyword("水");
        System.out.println(list.size());
    }

    @Test
    public void TestDefaultTransaction(){
        BlogServiceManage blogServiceManage = (BlogServiceManage)ac.getBean("blogServiceManage");
        blogServiceManage.updateDefaultTransaction(1);
    }

}
