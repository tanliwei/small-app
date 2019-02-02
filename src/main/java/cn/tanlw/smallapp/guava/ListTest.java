package cn.tanlw.smallapp.guava;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    @Test
    public void list(){
        ArrayList<Student> tom = Lists.newArrayList(new Student("Tom"));
        Assert.assertEquals(1, tom.size());


        Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();
        List<String> strings = SPLITTER.splitToList("a,b,c");
        Assert.assertEquals(3, strings.size());
    }
}

class Student{
    public Student(String sname){this.sname = sname;}
    private String sname;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
