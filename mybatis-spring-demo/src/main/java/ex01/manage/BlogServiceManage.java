package ex01.manage;

import ex01.entity.Blog;
import ex01.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceManage {

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    public void updateDefaultTransaction(Integer blogId){
        long currentTime = System.currentTimeMillis();
        blogMapper.updateBlogById(new Blog(blogId, currentTime + "test"));
        int i = 1/0;
    }

}
