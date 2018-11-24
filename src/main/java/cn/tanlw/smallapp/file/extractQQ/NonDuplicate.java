package cn.tanlw.smallapp.file.extractQQ;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @Creator Tan Liwei
 * @Date 2018/11/10 23:49
 */
public class NonDuplicate {
    public static void main(String[] args) throws IOException {
        String sourceDirectory = "D:\\data\\QQQunRes\\";
        Path startingDir = Paths.get(sourceDirectory);
        List<Path> result = new LinkedList<>();
        Files.walkFileTree(startingDir, new NonDuplicate.FindJavaVisitor(result));

        Set resultMap = new HashSet<>(1<<14);
        for (int i = 0; i < result.size(); i++) {
            dealOne(result.get(i).toString(), resultMap);
        }
//        sendEmail(resultMap);
    }


    @Test
    public void test(){
        Set set = new HashSet();
        set.add("23632817");
        set.add("93285937");
        set.add("93285937");
        sendEmail(set);
    }

    private static void sendEmail(Set resultMap) {
        Iterator iterator = resultMap.iterator();
        Random random = new Random();
        while (iterator.hasNext()){
            Object next = iterator.next();
            String email = getEmail(next);
            String body = "阿里云双十一折扣最后两天\\r\\nhttps://m.aliyun.com/act/team1111/#/share?params=N.Szfh1YPUJC.5a4bl5nf";
            MailUtil.sendMail(email,"阿里云双十一活动最后两天",body);
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static String getEmail(Object next) {
        return next +"@qq.com";
    }


    private static class FindJavaVisitor extends SimpleFileVisitor {
        private List<Path> result;
        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
            result.add((Path)file);
            return FileVisitResult.CONTINUE;
        }
    }

    public static void dealOne(String filepath, Set result) {
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fileReader);
            String line = null;
            while((line = br.readLine()) != null){
                result.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
