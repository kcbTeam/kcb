package com.kcb.student.util;

/**
 * 
 * @className: IntToBooleans
 * @description:
 * @author: Tao Li
 * @date: 2015-5-27 下午6:54:00
 */
public class IntToBooleans {

    public static boolean[] IntCvToBooleans(int answers) {
        boolean[] correctAns = new boolean[] {false, false, false, false};
        correctAns[3] = toBoolean(answers % 2);
        correctAns[2] = toBoolean(answers / 2 % 2);
        correctAns[1] = toBoolean(answers / 2 / 2 % 2);
        correctAns[0] = toBoolean(answers / 2 / 2 / 2 % 2);
        return correctAns;
    }

    public static int BooleansCvToInt(boolean[] booleans) {
        int answers =
                toInt(booleans[0]) * 8 + toInt(booleans[1]) * 4 + toInt(booleans[2]) * 2
                        + toInt(booleans[3]);
        return answers;
    }

    public static boolean toBoolean(int i) {
        if (i == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static int toInt(boolean boolean1) {
        if (boolean1) {
            return 1;
        } else {
            return 0;
        }
    }

}
