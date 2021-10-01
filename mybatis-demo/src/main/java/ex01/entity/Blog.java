package ex01.entity;

import lombok.Data;

/**
 * @author lilei
 * @since 2021/10/1
 */
@Data
public class Blog {
    private Integer id;
    private String name;

    public Blog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
