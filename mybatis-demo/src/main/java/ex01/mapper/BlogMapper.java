package ex01.mapper;

import ex01.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lilei
 * @since 2021/10/1
 */
public interface BlogMapper {

    Blog selectBlog(@Param("id") Integer id);

    List<Blog> listByKeyword(@Param("keyword") String keyword);

}


