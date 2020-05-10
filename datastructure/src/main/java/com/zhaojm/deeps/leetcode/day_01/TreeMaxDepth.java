package com.zhaojm.deeps.leetcode.day_01;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhaojm
 * @date 2020/5/3 20:37
 */
public class TreeMaxDepth {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode (int x) {
            this.val = x;
        }

        public int maxDepth(TreeNode root) {
            if(null == root) {
                return 0;
            } else {
                int leftDepth = maxDepth(root.left);
                int rightDepth = maxDepth(root.right);
                return Math.max(leftDepth, rightDepth) + 1;
            }
        }

        /**
         * 广度优先
         * @param root
         * @return
         */
        public int maxDepthBfs(TreeNode root) {
            if(null == root)
                return 0;
            Queue<TreeNode> queue = new LinkedList<>();
            int depth = 0;
            queue.add(root);
            while (!queue.isEmpty()){
                int size = queue.size();
                depth++;
                for (int i = 0; i < size; i++) {
                    TreeNode temp = queue.poll();
                    if(null != temp.left)
                        queue.add(temp.left);
                    if(null != temp.right)
                        queue.add(temp.right);
                }
            }
            return depth;
        }

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        System.out.println(root.maxDepth(root));
        System.out.println(root.maxDepthBfs(root));
    }

}
