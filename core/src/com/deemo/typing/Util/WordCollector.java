package com.deemo.typing.Util;

public class WordCollector {
    public static String[] collectWord(String s){
        String withOutComma = s.replace(",", " ");
        String withOutPeriod = s.replace(".", " ");
        return withOutPeriod.split(" ");
    }

}
