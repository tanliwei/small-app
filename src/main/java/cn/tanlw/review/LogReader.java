package cn.tanlw.review;

import java.io.*;

/**
 * Java工程读取resources中资源文件路径问题
 * -- https://www.cnblogs.com/shuimuzhushui/p/7247864.html
 */
public class LogReader {
    public static void main(String[] args) {
//        File b = new File("");
//        File a = new File("src/main/resource/input.txt");
        InputStream is = LogReader.class.getClass().getResourceAsStream("/input.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            /**
             * Console:
             * hello world.
             * hello java.
             * hello github.
             */
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
