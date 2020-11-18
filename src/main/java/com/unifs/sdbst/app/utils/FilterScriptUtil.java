package com.unifs.sdbst.app.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterScriptUtil
{
    private static List<Pattern> listXssScript = new ArrayList();

    static
    {
        listXssScript.add(Pattern.compile("<script(\\s+.*)?>(.*?)</script>", 2));

        listXssScript.add(Pattern.compile("</script>", 2));

        listXssScript.add(Pattern.compile("<script(.*?)>", 42));

        listXssScript.add(Pattern.compile("eval\\((.*?)\\)", 42));

        listXssScript.add(Pattern.compile("e?xpression\\((.*?)\\)", 42));

        listXssScript.add(Pattern.compile("javascript:", 2));

        listXssScript.add(Pattern.compile("vbscript:", 2));

        listXssScript.add(Pattern.compile("onload(.*?)=", 42));
    }

    public static boolean validateXSS(String value)
    {
        if (value != null) {
            for (Pattern scriptPattern : listXssScript)
            {
                Matcher matcher = scriptPattern.matcher(value);
                if (matcher.find())
                {
                    System.out.print("[XSS]发现跨域脚本" + value);
                    System.out.println("  == " + value);
                    return true;
                }
            }
        }
        return false;
    }

    public static String stripXSS(String value)
    {
        if (value != null) {
            for (Pattern scriptPattern : listXssScript)
            {
                Matcher matcher = scriptPattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll("");
                }
            }
        }
        return value;
    }

    private static String listSqlScript = "'| and | or | exec | execute | insert | select | delete | update | drop | truncate |";

    public static boolean validateSql(String value)
    {
        if (value != null)
        {
            value = value.toLowerCase();

            String[] badStrs = listSqlScript.split("\\|");
            for (int i = 0; i < badStrs.length; i++) {
                if (value.indexOf(badStrs[i]) >= 0)
                {
                    System.out.print("[SQL]发现注入脚本" + value);
                    System.out.println("  == " + value);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateSql(Object obj)
    {
        if (obj == null) {
            return false;
        }
        return validateSql(obj.toString());
    }

    public static String stripSql(String value)
    {
        if (value != null)
        {
            String[] badStrs = listSqlScript.split("\\|");
            for (int i = 0; i < badStrs.length; i++)
            {
                Pattern scriptPattern = Pattern.compile(badStrs[i], 2);
                Matcher matcher = scriptPattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll("");
                }
            }
        }
        return value;
    }

    public static String stripSql(Object obj)
    {
        if (obj == null) {
            return "";
        }
        return stripSql(obj);
    }

    public static void main(String[] args)
    {
        String testStr = "<script>alert(1)</script>A' or 'B'='B";

        System.out.println("SQL注入校验=" + validateSql(testStr));
        System.out.println("XSS跨域校验=" + validateXSS(testStr));

        System.out.println("stripSql = " + stripSql(testStr));
        System.out.println("stripXSS = " + stripXSS(testStr));
        System.out.println("stripALL = " + stripSql(stripXSS(testStr)));
    }
}
