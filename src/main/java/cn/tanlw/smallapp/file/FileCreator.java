package cn.tanlw.smallapp.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * Program arguments: C:/Downloads/test .mp4
 * Create a .txt file for each .mp4 file
 */
public class FileCreator {
    /**
     * args[0] : folder path
     * args[1] : target file suffix
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (null == args || args.length == 0) {
            System.err.println("Need input the folder path and target file type");
            return;
        }
        String targetFolderPath = args[0];
        String suffix = args[1];

        List<Path> files = new LinkedList<>();
        Files.walkFileTree(Paths.get(targetFolderPath), new MyFileVisitor(targetFolderPath,files, suffix));
        for (int i = 0; i < files.size(); i++) {
            doCreation(files.get(i), suffix, ".txt");
        }
    }

    private static void doCreation(Path path, String oldSuffix, String newSuffix) {
        try {
            String newFile = path.toFile().getAbsolutePath().replace(oldSuffix, newSuffix);
            File file = new File(newFile);
            if (file.exists()){
                //do nothing
            }else{
                file.createNewFile();
            }
            file = null;
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
