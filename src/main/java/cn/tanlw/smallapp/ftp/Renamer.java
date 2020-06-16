package cn.tanlw.smallapp.ftp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
/*
https://www.codejava.net/java-se/ftp/rename-file-or-directory-on-ftp-server
 */
public class Renamer {

    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("Please input the username and password");
        String server = "ftpserver";
        int port = 2112;
        String user = args[0];
        String pass = args[1];

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            boolean success;
            // renaming directory
//            String oldDir = "/photo";
//            String newDir = "/photo_2012";
//
//             = ftpClient.rename(oldDir, newDir);
//            if (success) {
//                System.out.println(oldDir + " was successfully renamed to: "
//                        + newDir);
//            } else {
//                System.out.println("Failed to rename: " + oldDir);
//            }

            ftpClient.changeWorkingDirectory("weather");
            // renaming file
            String oldFile = "06142020.csv.gz";
            String newFile = "badfiles/06142020.csv.gz";

            success = ftpClient.rename(oldFile, newFile);
            if (success) {
                System.out.println(oldFile + " was successfully renamed to: "
                        + newFile);
            } else {
                System.out.println("Failed to rename: " + oldFile);
            }

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
