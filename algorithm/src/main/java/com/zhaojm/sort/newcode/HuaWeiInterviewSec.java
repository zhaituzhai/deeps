package com.zhaojm.sort.newcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给出一个字符串 str（仅含有小写英文字母和括号）。
 * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。
 * 注意，您的结果中 不应 包含任何括号。
 *
 * public String reverseStr(String str)
 *
 *
 *
 * 输入：s = "(abcd)"
 * 输出："dcba"
 *
 * 输入：s = "(ed(llo(oc))eh)"
 * 输出："hellocode"
 */
public class HuaWeiInterviewSec {

    public static void main(String[] args) {
//        System.out.println(reverseStr("(ed(llo(oc))eh)"));
//        "kae(c(x((ffr()(t(ky)esr()oc)bl)kwx)qz)a(yovg(())dpx((w(co(tuz))zldu(nukcno(j)nohpg)g)bxp)djjbw))ho(h)"
//        "kaeyovgdpxpxbwtuzoczldugphonjonckungdjjbwaxxwkffrcorsekytblqzchoh"

        System.out.println(reverseStr("kae(c(x((ffr()(t(ky)esr()oc)bl)kwx)qz)a(yovg(())dpx((w(co(tuz))zldu(nukcno(j)nohpg)g)bxp)djjbw))ho(h)"));
        System.out.println(reverseStr("kaeyovgdpxpxbwtuzoczldugphonjonckungdjjbwaxxwkffrcorsekytblqzchoh"));

    }

    public static String reverseStr(String str) {
        int len = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if(str.charAt(i) == ')') {
                String substring = str.substring(0, i);
                int last1 = substring.lastIndexOf('(');
                sb.append(substring.substring(0, last1));
                String revStr = substring.substring(last1, i);
                if (revStr.length() > 0) {
                    for (int j = revStr.length() - 1; j > 0; j--) {
                        sb.append(revStr.charAt(j));
                    }
                }
                sb.append(str.substring(i + 1));
                str = sb.toString();
//                System.out.println(str);
                if ( str.lastIndexOf('(') < 0) {
                    return sb.toString();
                }
                len = str.length();
                i = i - revStr.length() - 1;
                sb = new StringBuilder();
            }
        }
        return str;
    }

}
