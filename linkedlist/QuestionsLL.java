public class QuestionsLL {

    public class ListNode {
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

    // Leetcode 876
    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    // Our Use
    public ListNode middleNode2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    // Leetcode 206
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode curr = head;
        ListNode prev = null;

        while (curr != null) {
            ListNode forward = curr.next;
            curr.next = prev;

            prev = curr;
            curr = forward;
        }
        return prev;
    }

    // Leetcode 234
    public boolean isPalindrome(ListNode head) {

        ListNode mid = middleNode2(head);

        ListNode midN = mid.next;
        mid.next = null;

        ListNode tail = reverseList(midN);

        ListNode tailRef = tail;
        ListNode headRef = head;
        while (tailRef != null) {
            if (tailRef.val != headRef.val)
                return false;

            tailRef = tailRef.next;
            headRef = headRef.next;
        }

        tail = reverseList(tail);
        mid.next = tail;
        return true;
    }

    // Leetcode 143
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;

        ListNode temp = head;
        ListNode mid = middleNode2(temp);

        temp = mid.next;
        mid.next = null; // <--imporatant point
        ListNode tail = reverseList(temp);

        ListNode curr = head;
        while (tail != null) {
            ListNode forC = curr.next; // forC = forward curr
            ListNode forT = tail.next; // forT = forward tail

            curr.next = tail;
            tail.next = forC;

            curr = forC;
            tail = forT;
        }
    }

    public void resetReorderList(ListNode head) {
        if (head == null || head.next == null)
            return;

        ListNode curr = head;
        ListNode cNext = curr.next; // currNext

        ListNode tempN = cNext;
        while (curr.next != null && curr.next.next != null) {
            ListNode forwardC = curr.next.next;
            curr.next = forwardC;
            curr = forwardC;

            if (forwardC.next != null) {
                cNext.next = forwardC.next;
                cNext = forwardC.next;
            }

        }
        reverseList(tempN);
        curr.next = cNext;
    }

    // Leetcode 21
    public ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        ListNode temp = null;
        if (l1 == null && l2 == null) {
            return l1;
        }
        if (l1 == null)
            return l2;
        else if (l2 == null)
            return l1;

        if (l1.val < l2.val) {
            temp = l1;
            l1 = l1.next;
        } else {
            temp = l2;
            l2 = l2.next;
        }

        ListNode curr = temp;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                curr = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                curr = l2;
                l2 = l2.next;
            }
        }
        if (l1 != null)
            curr.next = l1;
        else if (l2 != null)
            curr.next = l2;

        return temp;
    }

    // Leetcode 148
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode mid = middleNode2(head);
        ListNode midNext = mid.next;
        mid.next = null;

        return mergeTwoSortedLists(sortList(mid), sortList(midNext));
    }

    // Leetcode 23
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int si, int ei) {
        if (si == ei)
            return lists[si];

        int mid = (si + ei) / 2;
        ListNode start = merge(lists, si, mid);
        ListNode end = merge(lists, mid + 1, ei);

        return mergeTwoSortedLists(start, end);
    }

    // Leetcode 142
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast)
                break;
        }

        if (slow != fast)
            return null; // if it's not a cycle

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    // Leetcode 82
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode dummy = new ListNode(0 == head.val ? 1 : 0);// to guarantee the dummy node is not same as the original
                                                             // head.
        dummy.next = head;

        ListNode pre = dummy;
        ListNode cur = head;

        ListNode first = dummy; // the first node in the new unduplicated(result) list.

        while (cur != null && cur.next != null) {
            if (cur.val != pre.val && cur.val != cur.next.val) { // we found a unique node, we connect it at the tail of
                                                                 // the unduplicated list, and update the first node.
                first.next = cur;
                first = first.next;
            }
            pre = cur;
            cur = cur.next;
        }

        if (pre.val != cur.val) { // the last node needs to be dealt with independently
            first.next = cur;
            first = first.next;
        }

        first.next = null; // the subsequent list is duplicate.

        return dummy.next;
    }

    // leetcode 92
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right)
            return head;

        ListNode curr = head;
        ListNode prev = null;

        int idx = 1;
        while (curr != null) {
            while (idx >= left && idx <= right) {
                ListNode temp = curr.next;
                curr.next = null;
                addFirstNode(curr);
                curr = temp;
                idx++;
            }
            if (idx > right) {
                if (prev == null)
                    head = th;
                else
                    prev.next = th;

                tt.next = curr;
                break;
            }
            prev = curr;
            curr = curr.next;
            idx++;
        }
        return head;
    }

    public void addFirstNode(ListNode node) {
        if (th == null && tt == null) {
            th = tt = node;
        } else {
            node.next = th;
            th = node;
        }
    }

    // Leetcode 19
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode prev = null;
        ListNode curr = head;

        while (n-- > 0) {
            curr = curr.next;
        }

        if (curr == null) {
            return head.next;
        }

        prev = head;
        while (curr.next != null) {
            prev = prev.next;
            curr = curr.next;
        }

        prev.next = prev.next.next;

        return head;
    }

    // [start] Leetcode 25
    public ListNode reverseKGroup(ListNode head, int k) {
        return reverseK(head, k);
    }

    ListNode th = null; // temporary head
    ListNode tt = null; // temporary tail

    public ListNode reverseK(ListNode head, int k) {
        ListNode oh = null;
        ListNode ot = null;

        int len = lengthOfList(head);

        ListNode curr = head;
        while (len >= k) {
            int iter = k;
            while (iter-- > 0) {
                ListNode forward = curr.next;
                curr.next = null;
                addFirstNode(curr);
                curr = forward;
            }

            if (oh == null && ot == null) {
                oh = th;
                ot = tt;
            } else {
                ot.next = th;
                ot = tt;
            }

            tt = th = null;
            len -= k;

        }

        ot.next = curr;
        return oh;
    }

    public int lengthOfList(ListNode head) {
        ListNode temp = head;
        int count = 0;

        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
    // [end]

    // Segragate Odd Even (GFG)
    Node divide(int N, Node head) {

        Node evenStart = new Node(-1);
        Node evenEnd = evenStart;
        Node oddStart = new Node(-1);
        Node oddEnd = oddStart;
        Node currentNode = head;

        while (currentNode != null) {
            int element = currentNode.data;

            if (element % 2 == 0) {

                if (evenStart == null) {
                    evenStart = currentNode;
                    evenEnd = evenStart;
                } else {
                    evenEnd.next = currentNode;
                    evenEnd = evenEnd.next;
                }

            } else {

                if (oddStart == null) {
                    oddStart = currentNode;
                    oddEnd = oddStart;
                } else {
                    oddEnd.next = currentNode;
                    oddEnd = oddEnd.next;
                }
            }
            // Move head pointer one step in forward direction
            currentNode = currentNode.next;
        }

        evenEnd.next = oddStart.next;
        oddEnd.next = null;
        return evenStart.next;
    }

    // 160 Leetcode
    // We can also just join the tail of one to another and check for detect cycle
    // (Leetcode 142)
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;

        ListNode tempA = headA;
        ListNode tempB = headB;

        while (tempA != tempB) {
            if (tempA == null)
                tempA = headB;
            else
                tempA = tempA.next;

            if (tempB == null)
                tempB = headA;
            else
                tempB = tempB.next;

        }
        return tempA;

    }

    //Leetcode-138 TODoO : try to do in functions
    // Three steps 
    // First make dummy next to every node
    // Second, curr.next.random = curr.ran.next;
    // Thirst, extract dummies;
    
    public Node copyRandomList(Node head) {
        if(head == null) return null;
        Node curr = head;
        
        while(curr != null) {
            Node neww = new Node(curr.val);
            Node temp = curr.next;
            curr.next = neww;
            neww.next = temp;
            curr = temp;
        } //seggragaed
        
        curr = head;
        while(curr != null) {
            if(curr.random != null)
                curr.next.random = curr.random.next;
            curr = curr.next.next;
        } //
        
        curr = head;
        Node copiedList = new Node(curr.val == 0 ? 1 : 0);
        Node copied = copiedList;
        
        while(curr != null ) {
            Node temp = curr.next.next;
            Node diff = curr.next;
            curr.next = temp;
            curr = temp;
            
            copied.next = diff;
            copied = diff;

        }
        copied.next = null;
        return copiedList.next;
    }
}
