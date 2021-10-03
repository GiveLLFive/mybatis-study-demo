package ex01.entity;

import lombok.Data;

@Data
public class Blog {
    private Integer id;
    private String name;

    public Blog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
