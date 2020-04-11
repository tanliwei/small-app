package cn.tanlw.smallapp.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class FileCopier implements Runnable {
    public static String TARGET_FOLDER;
    public static String SOURCE_FOLDER;

    /**
     * args[0]: the target folder
     * args[1]: the source folder
     *
     * @param args
     */
    public static void main(String[] args) {
        setParams(args);
        new Thread(new FileCopier()).start();
    }

    private static void setParams(String[] args) {
        if (args == null || args.length == 0)
            throw new RuntimeException("Please input params");
        TARGET_FOLDER = args[0];
        SOURCE_FOLDER = args[1];
    }

    /**
     * Copy non-empty files from the source folder to the target folder
     * when the target file does not exist or is empty.
     */
    @Override
    public void run() {
        List<Path> targetFiles = new LinkedList<>();
        List<Path> sourceFiles = new LinkedList<>();
        try {
            Files.walkFileTree(Paths.get(TARGET_FOLDER), new MyFileVisitor(TARGET_FOLDER, targetFiles, ".txt"));
            Files.walkFileTree(Paths.get(SOURCE_FOLDER), new MyFileVisitor(SOURCE_FOLDER, sourceFiles, ".txt"));
            for (Path sourceFile : sourceFiles) {
                BasicFileAttributes sourceAttr = Files.readAttributes(sourceFile, BasicFileAttributes.class);
                if (sourceAttr.size() == 0) {
                    continue;
                }
                String replaceAbsPath = sourceFile.toFile().getAbsolutePath().replace(SOURCE_FOLDER, TARGET_FOLDER);
                Path replacePath = Paths.get(replaceAbsPath);


                if (!replacePath.toFile().exists()) {
                    if (!replacePath.getParent().toFile().exists()) {
                        Files.createDirectory(replacePath.getParent());
                    }
                    System.out.println("Copy file:" + sourceFile.toFile().getAbsolutePath() + " \tto: \t" + replaceAbsPath);
                    Files.copy(sourceFile, replacePath);
                } else {
                    BasicFileAttributes attributes = Files.readAttributes(replacePath, BasicFileAttributes.class);
                    if (attributes.size() == 0) {
                        System.out.println("Copy file:" + sourceFile.toFile().getAbsolutePath() + " \tto: \t" + replaceAbsPath);
                        Files.copy(sourceFile, replacePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPath() {
        Path path = Paths.get("c:/path");
        System.out.println(path.toFile().exists());
        path = Paths.get("c:/test/a.txt");
        System.out.println(path.toFile().exists());
    }
}
