package com.zhaojm.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。 

 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。 

 

 示例 1: 

 
输入: list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]，list2 = [
"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
输出: ["Shogun"]
解释: 他们唯一共同喜爱的餐厅是“Shogun”。
 

 示例 2: 

 
输入:list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]，list2 = ["KFC", 
"Shogun", "Burger King"]
输出: ["Shogun"]
解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
 

 

 提示: 

 
 1 <= list1.length, list2.length <= 1000 
 1 <= list1[i].length, list2[i].length <= 30 
 list1[i] 和 list2[i] 由空格 ' ' 和英文字母组成。 
 list1 的所有字符串都是 唯一 的。 
 list2 中的所有字符串都是 唯一 的。 
 
 Related Topics 数组 哈希表 字符串 👍 170 👎 0

*/

public class MinimumIndexSumOfTwoLists {
    public static void main(String[] args) {
        Solution solution = new MinimumIndexSumOfTwoLists().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String[] findRestaurant(String[] list1, String[] list2) {
            Map<String, Integer> index = new HashMap<>();
            for (int i = 0; i < list1.length; i++) {
                index.put(list1[i], i);
            }
            List<String> ret = new ArrayList<>();
            int indexSum = Integer.MAX_VALUE;
            for (int i = 0; i < list2.length; i++) {
                if(index.containsKey(list2[i])) {
                    Integer j = index.get(list2[i]);
                    if(i + j < indexSum) {
                        ret.clear();
                        ret.add(list2[i]);
                        indexSum = i + j;
                    } else if (i + j == indexSum) {
                        ret.add(list2[i]);
                    }
                }
            }
            return ret.toArray(new String[0]);
        }
    }
    //leetcode submit region end(Prohibit modification and deletion)

}