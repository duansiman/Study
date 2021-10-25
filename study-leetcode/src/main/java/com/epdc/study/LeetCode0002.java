package com.epdc.study;

public class LeetCode0002 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 2. 两数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head=null, pre = null;
        byte carryNum = 0;
        ListNode li=l1, lj=l2;
        for (; li!=null && lj!=null; li=li.next,lj=lj.next) {
            int sum = li.val + lj.val + carryNum;
            int val;
            if (sum > 10) {
                carryNum = 1;
                val = sum % 10;
            } else {
                carryNum = 0;
                val = sum;
            }
            if (pre == null) {
                head = new ListNode(val);
                pre = head;
            } else {
                pre.next = new ListNode(val);
                pre = pre.next;
            }
        }
        if (li != null) {
           for (; li!=null; li=li.next) {
               int sum = li.val + carryNum;
               int val;
               if (sum > 10) {
                   carryNum = 1;
                   val = sum % 10;
               } else {
                   carryNum = 0;
                   val = sum;
               }
               if (pre == null) {
                   head = new ListNode(val);
                   pre = head;
               } else {
                   pre.next = new ListNode(val);
                   pre = pre.next;
               }
           }
        } else if (lj != null) {
            for (; lj!=null; lj=lj.next) {
                int sum = lj.val + carryNum;
                int val;
                if (sum > 10) {
                    carryNum = 1;
                    val = sum % 10;
                } else {
                    carryNum = 0;
                    val = sum;
                }
                if (pre == null) {
                    head = new ListNode(val);
                    pre = head;
                } else {
                    pre.next = new ListNode(val);
                    pre = pre.next;
                }
            }
        }
        if (carryNum != 0) {
            pre.next = new ListNode(carryNum);
        }
        return head;
    }
}
