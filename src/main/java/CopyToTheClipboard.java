import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
 */
public class CopyToTheClipboard {
    public static void main(String[] args) throws InterruptedException {
        if (null == args || args.length == 0){
            System.err.println("Need to input the content being copied to the system clipboard!");
            return;
        }
        copyToTheClipboard(args[0]);
        Thread.sleep(2000);
    }

    public static void copyToTheClipboard(String arg) {
        StringSelection stringSelection = new StringSelection(arg);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        System.out.println("The copy action has been done.");
    }
}
