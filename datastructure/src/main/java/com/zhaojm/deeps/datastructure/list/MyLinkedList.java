package com.zhaojm.deeps.datastructure.list;

public class MyLinkedList<E> {

    private ListNode<E> head;
    private int size = 0;

    class ListNode<E>{
        E value;		//值
        ListNode next;	//下一个的指针
        ListNode(E value){
            this.value = value;
            this.next = null;
        }
    }

    public void insertHead(E e){
        ListNode newNode = new ListNode(e);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void insertNth(int position, E e){
        if(null == head){
            this.insertHead(e);
        }else {
            ListNode<E> cur = head;
            for (int i = 1; i < position; i++) {
                cur = cur.next;
            }
            ListNode<E> newNode = new ListNode<>(e);
            newNode.next = cur.next;
            cur.next = newNode;
            size++;
        }
    }

    public void deleteHead(){
        head = head.next;
        size--;
    }

    public void deleteNth(int position){
        if(position == 0){
            deleteHead();
        }else {
            ListNode<E> cur = head;
            for (int i = 1; i < position; i++) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
            size--;
        }
    }

    public boolean contain(E e){
        boolean contain = false;
        ListNode<E> cur = head;
        while (true) {
            if(e.equals(cur.value)) {
                contain = true;
                break;
            }
            cur = cur.next;
        }
        return contain;
    }

    public void print(){
        ListNode cur = head;
        while(cur != null){
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        myList.insertHead(5);
        myList.insertHead(7);
        myList.insertHead(10);
        myList.print(); // 10 -> 7 -> 5
        myList.deleteNth(0);
        myList.print(); // 7 -> 5
        myList.deleteHead();
        myList.print(); // 5
        myList.insertNth(1, 11);
        myList.print(); // 5 -> 11
        myList.deleteNth(1);
        myList.print(); // 5
        System.out.println(myList.contain(5));
    }

}
