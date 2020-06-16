package cn.tanlw.smallapp.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilesRename implements Runnable {

    private static String TARGETFOLDER, SUFFIX;
    private static Map<String, Integer> counter = new HashMap<>();

    /**
     * args[0]: folder path
     * EXAMPLE: "C:\\Users\\admin\\Documents\\录音" ".m4a"
     * @param args
     */
    public static void main(String[] args) {
        setParams(args);
        new Thread(new FilesRename()).start();
        
    }

    private static void setParams(String[] args) {
        if (args == null || args.length<2){
            throw new RuntimeException("Please input params");
        }
        TARGETFOLDER = args[0];
        SUFFIX = args[1];
    }

    @Override
    public void run() {
        List<Path> files = new LinkedList<>();

        Path targetFolder = Paths.get(TARGETFOLDER);
        try {
            Files.walkFileTree(targetFolder, new MyFileVisitor(TARGETFOLDER, files, ".m4a"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern compile = Pattern.compile("\\w+\\d{4}\\w+");
        SimpleDateFormat sdf = new SimpleDateFormat("MMddYYYY");
        for (Path path : files) {
            File currentFile = path.toFile();
            try {

                Matcher matcher = compile.matcher(path.getFileName().toString());
                if(!matcher.find()){// There isn't a date in the filename
                    BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                    Date createTime = new Date(attributes.creationTime().toMillis());
                    File newFile = new File(targetFolder + "/" + sdf.format(createTime) + getPartFileName(sdf.format(createTime)) + SUFFIX);
                    System.out.println("Change old file:"+currentFile.getPath()+" to " + newFile.getPath());
                    currentFile.renameTo(newFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done.");
    }

    private Object getPartFileName(String date) {
        Integer integer = counter.get(date);
        if (integer == null){
            counter.put(date, 1);
            return "";
        }
        counter.put(date, counter.get(date) + 1);
        return "-"+counter.get(date);
    }
}
//                System.out.println("creationTime: " + attr.creationTime());
//                System.out.println("lastAccessTime: " + attr.lastAccessTime());
//                System.out.println("lastModifiedTime: " + attr.lastModifiedTime());