package com.zhaojm.sort;

public class HeapSort implements ISort{
    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        // 构造二叉树,从最后一个叶子节点开始
        for (int i = (length - 1) / 2; i >= 0; i--) {
            buildHeap(arr, i, length);
        }
        // 根据得到的树，最大值在根节点，把最后一个字符和根节点交换，重新构建一下树节点，每次排一个数字
        for (int i = length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            buildHeap(arr, 0, i);
        }
        return arr;
    }

    private void buildHeap(int[] arr, int parent, int length) {
        // 暂存小树的根节点
        int temp = arr[parent];
        // 找出左子节点
        int lChildIndex = 2 * parent + 1;
        while (lChildIndex < length) {
            // 右子节点
            int rChildIndex = lChildIndex + 1;
            // 找出比较大的子节点的位置
            if (rChildIndex < length && arr[lChildIndex] < arr[rChildIndex])
                lChildIndex++;
            // 比较根节点和子节点，如果根节点大于子节点，则构建树成功
            if (temp >= arr[lChildIndex])
                break;
            // 如果根节点小于子节点
            arr[parent] = arr[lChildIndex];
            parent = lChildIndex;
            lChildIndex = 2 * lChildIndex + 1;
        }
        arr[parent] = temp;
    }

    /*@Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        for (int i = (length - 1) / 2; i >= 0; i--) {
            heapSort(arr, i, length);
        }

        for (int i = length - 1; i > 0 ; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            heapSort(arr, 0, i);
        }
        return arr;
    }

    public void heapSort(int[] arr, int parent, int length) {
        int temp = arr[parent];
        int lChildIndex = 2 * parent + 1;
        while (lChildIndex < length) {
            int rChildIndex = lChildIndex + 1;
            if (rChildIndex < length && arr[lChildIndex] < arr[rChildIndex]) {
                lChildIndex++;
            }
            if (temp >= arr[lChildIndex]) {
                break;
            }
            arr[parent] = arr[lChildIndex];
            parent = lChildIndex;
            lChildIndex = 2 * lChildIndex + 1;
        }
        arr[parent] = temp;
    }*/
}
