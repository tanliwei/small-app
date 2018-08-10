package cn.tanlw.json;

/**
 * @create 2018/7/18
 */
public class Student {
    String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
