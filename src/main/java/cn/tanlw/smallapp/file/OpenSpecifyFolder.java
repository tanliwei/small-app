package cn.tanlw.smallapp.file;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Program arguments: targetFolderName
 * Open a specify folder that have name contains targetFolderName
 */
public class OpenSpecifyFolder {

    public static String BASE_FOLDER = "C:/Cases";

    /**
     * args[0] : targetFolderName
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (null == args || args.length == 0) {
            System.err.println("Need input the folder name so as to open it");
            return;
        }
        int maxDepth = 1;
        //Optional max depth parameter
        if (args.length == 2){
            try{
                maxDepth = Integer.parseInt(args[1]);
            }catch (Exception e){}
        }
        File base = new File("base.txt");
        MyFileReader myFileReader = new MyFileReader();
        List<String> s = null;
        //Optional base folder parameter
        try{
            s = myFileReader.readFileAsList(base);
        }catch (Exception e){}
        if(s != null && s.size()>0){
            BASE_FOLDER = s.get(0);
        }
        String targetFolderPath = args[0];
        Path baseFolder = Paths.get(BASE_FOLDER);

        List<Path> list = Files.walk(baseFolder, maxDepth).filter(Files::isDirectory).collect(Collectors.toList());
        list.remove(0);
        System.out.println("Size of sub directories: "+list.size());
        
        Optional<Path> first = list.stream().filter(item -> item.getFileName().toString().contains(targetFolderPath)).findFirst();
        if (first.isPresent()){
            File file = first.get().toFile();
            System.out.println("Found the target folder: "+file.getAbsolutePath());
            Desktop.getDesktop().open(file);
        }
        Thread.sleep(1500);
    }   
}
