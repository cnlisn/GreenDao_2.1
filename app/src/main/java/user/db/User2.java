package user.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USER2".
 */
public class User2 {

    private Long id;
    private String name;
    private Integer age;
    private String tel;

    public User2() {
    }

    public User2(Long id) {
        this.id = id;
    }

    public User2(Long id, String name, Integer age, String tel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
