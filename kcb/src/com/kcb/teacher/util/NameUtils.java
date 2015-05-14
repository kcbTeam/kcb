package com.kcb.teacher.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class NameUtils {

    @SuppressWarnings("unused")
    private static final String TAG = "NameUtils";

    @SuppressWarnings("deprecation")
    // return zhangqinjie
    public static String getEnName(String name) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
    }

    // return zqj
    public static String getNameAbbr(String name) throws BadHanyuPinyinOutputFormatCombination {
        String nameAbbr = "";
        for (int i = 0; i < name.length(); i++) {
            String temp = getEnName(String.valueOf(name.charAt(i)));
            nameAbbr += temp.substring(0, 1);
        }
        return nameAbbr;
    }

    public static boolean isMatch(String name, String compare)
            throws BadHanyuPinyinOutputFormatCombination {
        if (name.startsWith(compare) || name.toUpperCase().startsWith(compare)
                || name.startsWith(compare) || name.toUpperCase().startsWith(compare)) {
            return true;
        }
        if (NameUtils.getEnName(name).startsWith(compare)
                || NameUtils.getEnName(name).toUpperCase().startsWith(compare)
                || NameUtils.getNameAbbr(name).startsWith(compare)
                || NameUtils.getNameAbbr(name).toUpperCase().startsWith(compare)) {
            return true;
        }
        for (int i = 0; i < name.length(); i++) {
            if (String.valueOf(name.charAt(i)).startsWith(compare)
                    || NameUtils.getEnName(String.valueOf(name.charAt(i))).startsWith(compare)) {
                return true;
            }

        }
        return false;
    }
}
