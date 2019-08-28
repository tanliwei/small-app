import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * Search target String in the contents of files of the target folder path
 */
public class ContextSearch {
    /**
     * args[0] : folder path
     * args[1] : target String
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (null == args || args.length == 0){
            System.err.println("Need input the folder path argument and target String");
            return;
        }
        Path targetFolder = Paths.get(args[0]);

        List<Path> files = new LinkedList<>();
        Files.walkFileTree(targetFolder, new MyFileVisitor(files));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            dealOne(sb, files.get(i), args[1]);
        }
        File output = new File("output.out");
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write(sb.toString());
        writer.close();
    }

    private static void dealOne(StringBuilder sb, Path path, String target) {
        
        BufferedReader reader;
        try{
            int i = 1;
            File file = path.toFile();
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                if(line.toLowerCase().contains(target.toLowerCase())){
                    sb.append(file.getAbsolutePath() + " : " + i + "\r\n");
                }
                line = reader.readLine();
                i++;
            }
        }catch (IOException e){
            System.err.println(e.toString());
        }
    }

    private static class MyFileVisitor extends SimpleFileVisitor {
        private List<Path> files;
        public MyFileVisitor(List<Path> files) {
            this.files = files;
        }
        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
            files.add((Path)file);
            return FileVisitResult.CONTINUE;
        }
    }
}
