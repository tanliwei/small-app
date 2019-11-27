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
        StringSelection stringSelection = new StringSelection(args[0]);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        System.out.println("The copy action has been done.");
        Thread.sleep(2000);
    }
}
