package cn.tanlw.smallapp.file;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class MyFileVisitor implements FileVisitor {
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
