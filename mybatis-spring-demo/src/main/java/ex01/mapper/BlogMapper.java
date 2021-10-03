package ex01.mapper;

import ex01.entity.Blog;

import java.util.List;

public interface BlogMapper {

//    @Select("select * from blog where id = #{id}")
    Blog selectBlog(Integer id);

    List<Blog> listByKeyword(String keyword);

    int updateBlogById(Blog blog);

}


