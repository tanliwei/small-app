package cn.tanlw.smallapp.string;

import cn.tanlw.util.CopyToTheClipboard;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceTimeMillisecondsIntoString {
    public static void main(String[] args) {
        if (StringUtils.isEmpty(INPUT))
            return;
        List<String> targets = findTargets(INPUT);
        System.out.println(targets.size());
        Date date;
        String target;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < targets.size(); i++) {
            target = targets.get(i);
            date = new Date(Long.parseLong(target));
            INPUT = INPUT.replace(target, "\""+sdf.format(date)+"\"");
        }
        System.out.println(INPUT);
        CopyToTheClipboard.copyToTheClipboard(INPUT);
//        INPUT.replace("",)
    }

    public static List<String> findTargets(String input) {
        List list = new ArrayList();
        Pattern patt = Pattern.compile("([1][0-9]{12})");
        Matcher m = patt.matcher(input);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * https://www.javamex.com/tutorials/regular_expressions/search_replace_loop.shtml
     *
     * @param htmlString
     * @return helloworld when INPUT <b>hello</b><b>world</b>
     */
    public static String removeBoldTags(CharSequence htmlString) {
        Pattern patt = Pattern.compile("<b>([^<]{})</b>");
        Matcher m = patt.matcher(htmlString);
        StringBuffer sb = new StringBuffer(htmlString.length());
        while (m.find()) {
            String text = m.group(1);
            // ... possibly process 'text' ...
            m.appendReplacement(sb, Matcher.quoteReplacement(text));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String INPUT = "{\"lines\":[{\"date\":1575878400000,\"count\":26,\"upToDate\":false},{\"date\":1575446400000,\"count\":24,\"upToDate\":false},{\"date\":1576310400000,\"count\":24,\"upToDate\":false},{\"date\":1575360000000,\"count\":26,\"upToDate\":false},{\"date\":1576224000000,\"count\":23,\"upToDate\":false},{\"date\":1575792000000,\"count\":27,\"upToDate\":false},{\"date\":1575705600000,\"count\":28,\"upToDate\":false},{\"date\":1576569600000,\"count\":6,\"upToDate\":false},{\"date\":1576137600000,\"count\":24,\"upToDate\":false},{\"date\":1576051200000,\"count\":25,\"upToDate\":false},{\"date\":1576483200000,\"count\":24,\"upToDate\":false},{\"date\":1575619200000,\"count\":28,\"upToDate\":false},{\"date\":1575532800000,\"count\":28,\"upToDate\":false},{\"date\":1575964800000,\"count\":26,\"upToDate\":false},{\"date\":1576396800000,\"count\":23,\"upToDate\":false}],\"orders\":[{\"date\":1575878400000,\"count\":7,\"upToDate\":false},{\"date\":1575446400000,\"count\":8,\"upToDate\":false},{\"date\":1576310400000,\"count\":7,\"upToDate\":false},{\"date\":1575360000000,\"count\":8,\"upToDate\":false},{\"date\":1576224000000,\"count\":7,\"upToDate\":false},{\"date\":1575792000000,\"count\":8,\"upToDate\":false},{\"date\":1575705600000,\"count\":8,\"upToDate\":false},{\"date\":1576569600000,\"count\":4,\"upToDate\":false},{\"date\":1576137600000,\"count\":7,\"upToDate\":false},{\"date\":1576051200000,\"count\":7,\"upToDate\":false},{\"date\":1576483200000,\"count\":7,\"upToDate\":false},{\"date\":1575619200000,\"count\":8,\"upToDate\":false},{\"date\":1575532800000,\"count\":8,\"upToDate\":false},{\"date\":1575964800000,\"count\":7,\"upToDate\":false},{\"date\":1576396800000,\"count\":7,\"upToDate\":false}],\"revenue\":[{\"date\":1575878400000,\"count\":834245.72,\"upToDate\":false},{\"date\":1575446400000,\"count\":790302.91,\"upToDate\":false},{\"date\":1576310400000,\"count\":762499.64,\"upToDate\":false},{\"date\":1575360000000,\"count\":659073.55,\"upToDate\":false},{\"date\":1576224000000,\"count\":865306.9,\"upToDate\":false},{\"date\":1575792000000,\"count\":896384.55,\"upToDate\":false},{\"date\":1575705600000,\"count\":996468.8,\"upToDate\":false},{\"date\":1576569600000,\"count\":150524.31,\"upToDate\":false},{\"date\":1576137600000,\"count\":848667.6799999999,\"upToDate\":false},{\"date\":1576051200000,\"count\":806629.56,\"upToDate\":false},{\"date\":1576483200000,\"count\":887291.48,\"upToDate\":false},{\"date\":1575619200000,\"count\":808402.89,\"upToDate\":false},{\"date\":1575532800000,\"count\":938369.77,\"upToDate\":false},{\"date\":1575964800000,\"count\":802047.83,\"upToDate\":false},{\"date\":1576396800000,\"count\":871291.78,\"upToDate\":false}],\"hasMultiCurrencyWarning\":false,\"currencyCode\":\"USD\",\"currencySymbol\":\"$\",\"lastCompleteLines\":24,\"lastCompleteOrders\":7,\"lastCompleteRev\":887291.48,\"lastCompletedDate\":1570645077000}";
}
