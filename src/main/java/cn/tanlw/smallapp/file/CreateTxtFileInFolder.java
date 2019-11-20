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
public class CreateTxtFileInFolder {
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
        Path targetFolder = Paths.get(targetFolderPath);

        List<Path> files = new LinkedList<>();
        Files.walkFileTree(targetFolder, new MyFileVisitor(targetFolderPath,files, suffix));
        for (int i = 0; i < files.size(); i++) {
            process(files.get(i), suffix, ".txt");
        }
    }

    private static void process(Path path, String oldSuffix, String newSuffix) {
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

    private static class MyFileVisitor implements FileVisitor {
        private final String targetFolder;
        private List<Path> files;
        private String suffix;

        public MyFileVisitor(String targetFolder, List<Path> files, String suffix) {
            this.targetFolder = targetFolder;
            this.files = files;
            this.suffix = suffix;
        }

        @Override
        public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
//            boolean matches = pattern.matcher(path.toString()).matches();
//            TODO: Put here your business logic when matches equals true/false
//            return (matches)? FileVisitResult.CONTINUE:FileVisitResult.SKIP_SUBTREE;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs){
            System.out.println(((Path) file).getFileName());
            if (((Path) file).getFileName().toString().endsWith(suffix)) {
                files.add((Path) file);
            }
            return FileVisitResult.CONTINUE;

        }

        /**
         * https://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java
         * @param file
         * @param exc
         * @return
         * @throws IOException
         */
        @Override
        public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
            exc.printStackTrace();
            // If the root directory has failed it makes no sense to continue
            return ((Path)file).equals(targetFolder)? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }
}
