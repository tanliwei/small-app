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
            INPUT = INPUT.replace(target, sdf.format(date));
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

    private static String INPUT = "{\n" +
            "\t\"lines\": [{\n" +
            "\t\t\"date\": 1575014400000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575446400000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574582400000,\n" +
            "\t\t\"count\": 4,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574496000000,\n" +
            "\t\t\"count\": 6,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574928000000,\n" +
            "\t\t\"count\": 3,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575360000000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575273600000,\n" +
            "\t\t\"count\": 3,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574841600000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574409600000,\n" +
            "\t\t\"count\": 3,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574323200000,\n" +
            "\t\t\"count\": 5,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575187200000,\n" +
            "\t\t\"count\": 3,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574755200000,\n" +
            "\t\t\"count\": 6,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575100800000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575532800000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574668800000,\n" +
            "\t\t\"count\": 5,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}],\n" +
            "\t\"orders\": [{\n" +
            "\t\t\"date\": 1575014400000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575446400000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574582400000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574496000000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574928000000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575360000000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575273600000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574841600000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574409600000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574323200000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575187200000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574755200000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575100800000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575532800000,\n" +
            "\t\t\"count\": 1,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574668800000,\n" +
            "\t\t\"count\": 2,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}],\n" +
            "\t\"revenue\": [{\n" +
            "\t\t\"date\": 1575014400000,\n" +
            "\t\t\"count\": 69342.64,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575446400000,\n" +
            "\t\t\"count\": 102161.91,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574582400000,\n" +
            "\t\t\"count\": 85359.61,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574496000000,\n" +
            "\t\t\"count\": 194702.91999999998,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574928000000,\n" +
            "\t\t\"count\": 141455.52000000002,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575360000000,\n" +
            "\t\t\"count\": 72191.0,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575273600000,\n" +
            "\t\t\"count\": 103366.23000000001,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574841600000,\n" +
            "\t\t\"count\": 71538.26999999999,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574409600000,\n" +
            "\t\t\"count\": 77875.33,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574323200000,\n" +
            "\t\t\"count\": 144301.91999999998,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575187200000,\n" +
            "\t\t\"count\": 175008.5,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574755200000,\n" +
            "\t\t\"count\": 229689.39,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575100800000,\n" +
            "\t\t\"count\": 67182.94,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1575532800000,\n" +
            "\t\t\"count\": 33707.39,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}, {\n" +
            "\t\t\"date\": 1574668800000,\n" +
            "\t\t\"count\": 177503.25,\n" +
            "\t\t\"upToDate\": false\n" +
            "\t}],\n" +
            "\t\"hasMultiCurrencyWarning\": false,\n" +
            "\t\"currencyCode\": \"USD\",\n" +
            "\t\"currencySymbol\": \"$\",\n" +
            "\t\"lastCompleteLines\": 2,\n" +
            "\t\"lastCompleteOrders\": 1,\n" +
            "\t\"lastCompleteRev\": 102161.91,\n" +
            "\t\"lastCompletedDate\": 1570645077000\n" +
            "}";
}
