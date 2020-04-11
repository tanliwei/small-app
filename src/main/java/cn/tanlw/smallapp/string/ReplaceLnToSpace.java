package cn.tanlw.smallapp.string;

import cn.tanlw.util.CopyToTheClipboard;

public class ReplaceLnToSpace {
    public static void main(String[] args) {
        INPUT = INPUT.replace("\t", " ").replace("\n", "  ");
        CopyToTheClipboard.copyToTheClipboard(INPUT);
    }
    
    public static String INPUT = "{\n" +
            "\t\t\"$group\": {\n" +
            "\t\t\t\"_id\": {\n" +
            "\t\t\t\t\"currencies\": \"$Cost Currency\",\n" +
            "\t\t\t\t\"date\": \"$Report Data Date\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"date\": {\n" +
            "\t\t\t\t\"$first\": \"$Report Data Date\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"campaigns\": {\n" +
            "\t\t\t\t\"$addToSet\": \"$Campaign Identifier\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"orders\": {\n" +
            "\t\t\t\t\"$addToSet\": \"$Order ID\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"revenue\": {\n" +
            "\t\t\t\t\"$sum\": \"$Total Revenue\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"currencies\": {\n" +
            "\t\t\t\t\"$first\": \"$Cost Currency\"\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}";
}
