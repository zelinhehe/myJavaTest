package algorithm;

/**
 * 206. 反转链表
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 反转一个单链表。
 * 示例:
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * <p>
 * 讲解：https://leetcode-cn.com/problems/reverse-linked-list/solution/shi-pin-jiang-jie-die-dai-he-di-gui-hen-hswxy/
 */
public class ReverseLink {

    public static ListNode reverseLink(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static ListNode reverseLink2(ListNode head) {
        // 递归终止条件
        if (head == null || head.next == null) {
            return head;
        }
        // 递归调用
        ListNode p = reverseLink2(head.next);
        // 归的时候
        head.next.next = head;
        head.next = null;
        return p;
    }

    /**
     * Definition for singly-linked list.
     **/
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        print(node);

        ListNode node1 = reverseLink(node);
        print(node1);

        ListNode node2 = reverseLink2(node1);
        print(node2);
    }

    public static void print(ListNode node) {
        while (node != null) {
            System.out.print(" " + node.val);
            node = node.next;
        }
        System.out.println();
    }
}



