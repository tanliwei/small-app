package cn.tanlw.smallapp.file.extractQQ;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Creator Tan Liwei
 * @Date 2018/11/10 21:39
 */
public class ExtractQQ {
    public static void main(String[] args) throws IOException {
        String sourceDirectory = "D:\\data\\QQQun\\";
        String targetDirectory = "D:\\data\\QQQunRes\\";


        //遍历整个目录，包括子目录
        Path startingDir = Paths.get(sourceDirectory);
        List<Path> result = new LinkedList<>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        for (int i = 0; i < result.size(); i++) {
            String filename = result.get(i).getFileName().toString();
            String readPath = result.get(i).toString();

            String writePath = targetDirectory + filename;

            dealOne(readPath,writePath);
        }
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

    private static void dealOne(String filepath, String writePath) {
        String content = readByNio(filepath);
        String regex = "\"uin\":(\\d+)[}]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> qqList = new ArrayList<>();
        while(matcher.find()){
            int i = 1;
            qqList.add(matcher.group(i++)+ "@qq.com\r\n");
        }

        writeByNio(qqList,writePath);
    }

    private static void writeByNio(List<String> qqList, String filepath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            FileChannel channel = fos.getChannel();
            ByteBuffer bf;
            for (int i = 0; i < qqList.size(); i++) {
                bf = Charset.forName("utf-8").encode(qqList.get(i) );
                channel.write(bf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * NIO读取文件代码
     * https://blog.csdn.net/z69183787/article/details/77101184
     * @param filepath
     * @return
     */
    public static String readByNio(String filepath) {
        FileInputStream fis = null;
        StringBuilder content = new StringBuilder();
        try {
            fis = new FileInputStream(new File(filepath));
            FileChannel channel = fis.getChannel();

            int capacity = 1024;//字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;
            while((length = channel.read(bf)) != -1){
                //Clears this buffer.  The position is set to zero, the limit is set to
                //the capacity, and the mark is discarded.
                bf.clear();
                byte[] bytes = bf.array();
                content.append(new String(bytes));
            }
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
