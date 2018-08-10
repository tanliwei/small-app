package cn.tanlw.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON语法说明：http://json.org/json-zh.html
 */
public class TestJson {

    public static void main(String[] args) {
        testPingping();
    }
    //里面的任务都是我的好友，希望他们不要看到 ^_^^_^^_^
    public static void testPingping(){

        //JSON 不能存储这样的Map对象，以对象作为key的Map对象
        Student s = new Student();
        s.name = "tom";
        Student s2 = new Student();
        s2.name = "Jack";
        Map<Student, Student> objectMap = new HashMap<>();
        objectMap.put(s, s2);
        //Console: {"name":"tom"}
        System.out.println(JSON.toJSONString(s));
        String objectJsonString = JSON.toJSONString(objectMap);
        //Console: objectJsonString:{{"name":"tom"}:{"name":"Jack"}}
        System.out.println("objectJsonString:"+ objectJsonString);

        Map<Student, Student> objectMap2 = new HashMap<>();
        objectMap2.put(s, s);
        //Console: objectJsonString2:{{"name":"tom"}:{"$ref":"$.null"}}
        System.out.println("objectJsonString2:" + JSON.toJSONString(objectMap2));
    }
}