package com.zhaojm.deeps.datastructure.list;

public class DoubleLinkList<E> {

    private DNode<E> head;
    private DNode<E> tail;

    public DoubleLinkList(){
        head = null;
        tail = null;
    }

    public void insertHead(E value){
        DNode<E> newNode = new DNode<>(value);
        if(null == head) {
            tail = newNode;
        }else {
            head.pre = newNode;
            newNode.next = head;
        }
    }

    public void deleteHead(){
        if(null == head) return;
        if(null == head.next) {
            tail = null;
        }else {
            head.next.pre = null;
        }
        head = head.next;
    }

    public void deleteKey(E value){
        DNode<E> cur = head;
        while (cur.value.equals(value)){
            if (cur.next == null) {
                System.out.println("没找到节点");
                return ;
            }
            cur = cur.next;
        }if (cur == head) {// 指向下个就表示删除第一个
            deleteHead();
        } else {
            cur.pre.next = cur.next;
            if(cur == tail){		//删除的是尾部
                tail = cur.pre;
                cur.pre = null;
            }else{
                cur.next.pre = cur.pre;
            }
        }
    }

    class DNode<E>{
        E value;
        DNode<E> pre;
        DNode<E> next;

        DNode(E value){
            this.value = value;
            pre = null;
            next = null;
        }
    }
}
