package algorithm;

import java.util.Stack;

/**
 * 有两个单向链表（链表长度分别为 m，n），这两个单向链表有可能在某个元素合并，也可能不合并。
 * 现在给定两个链表的头指针，在不修改链表的情况下，如何快速地判断这两个链表是否合并？
 * 如果合并，找到合并的元素，也就是图中的 c 元素。
 * <p>
 * 请用（伪）代码描述算法，并给出时间复杂度和空间复杂度。
 * <p>
 * 例如以下示例中 A 和 B 两个链表相交于 c1：
 * <p>
 * A:          a1 → a2
 *                      ↘
 *                      c → c2 → c3
 *                      ↗
 * B:    b1 → b2 → b3
 */
public class FindMergeNode {

    /**
     * 解题：时间复杂度：O(m+n),空间复杂度O(m+n)
     * 两个链表从头压栈，然后出栈，也就是从链表末尾开始
     */
    public static Node findMergeNode(Node nodeOne, Node nodeTwo) {
        Node node1 = nodeOne;
        Node node2 = nodeTwo;
        Stack<Node> nodeStack1 = new Stack<>();
        Stack<Node> nodeStack2 = new Stack<>();
        while (node1 != null || node2 != null) {
            if (node1 != null) {
                nodeStack1.push(node1);
                node1 = node1.next;
            }
            if (node2 != null) {
                nodeStack2.push(node2);
                node2 = node2.next;
            }
        }
        Node resultNode = null;
        while (!nodeStack1.empty() && !nodeStack2.empty()) {
            Node nodeTemp1 = nodeStack1.pop();
            Node nodeTemp2 = nodeStack2.pop();
            if (!nodeTemp1.equals(nodeTemp2)) {
                break;
            }
            resultNode = nodeTemp1;
        }
        return resultNode;
    }

    /**
     * 设 A 的长度为 a + c，B 的长度为 b + c，其中 c 为尾部公共部分长度，可知 a + c + b = b + c + a。
     * 当访问 A 的指针访问到链表尾部时，接着从 B 的头部开始访问 B；
     * 同样地，当访问 B 的指针访问到链表尾部时，接着从链A 的头部开始访问 A。
     * 这样就能控制访问 A 和 B 两个链表的指针能同时访问到交点。
     * 也就是
     * a -> c -> b 然后有交点的话，下一个节点就是
     * b -> c -> a 然后有交点的话，下一个节点就是
     *
     * A:          a1 → a2
     *                      ↘
     *                      c → c2 → c3
     *                      ↗
     * B:    b1 → b2 → b3
     * A：a1 → a2 → c → c2 → c3 → NULL → b1 → b2 → b3 → c
     * B: b1 → b2 → b3 → c → c2 → c3 → NULL → a1 → a2 → c
     *
     * 如果不存在交点，那么 a + b = b + a，以下实现代码中 l1 和 l2 会同时为 null，从而退出循环。
     */
    public static Node findMergeNode2(Node headA, Node headB) {
        Node l1 = headA, l2 = headB;
        while (l1 != l2) {
            l1 = (l1 == null) ? headB : l1.next;
            l2 = (l2 == null) ? headA : l2.next;
            System.out.println(l1 + " " + l2);
        }
        return l1;
    }

    static class Node {
        Integer data;
        Node next;

        public Node(Integer data, Node next) {
            this.next = next;
            this.data = data;
        }

        @Override
        public String toString() {
            return "" + data;
        }
    }

    public static void main(String[] args) {
        Node common = new Node(6, new Node(7, null));
        Node node1 = new Node(4, new Node(5, common));
        Node node2 = new Node(14, new Node(15, new Node(16, common)));

//        Node mergeNode = findMergeNode(node1, node2);
        Node mergeNode = findMergeNode2(node1, node2);
        System.out.println(mergeNode.data);
    }
}
