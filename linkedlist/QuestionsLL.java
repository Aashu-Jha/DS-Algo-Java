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

    //Leetcode 876
    public ListNode middleNode(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    //Our Use
    public ListNode middleNode2(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode slow = head;
        ListNode fast = head;

        while(fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    //Leetcode 206
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode curr = head;
        ListNode prev = null;

        while(curr != null) {
            ListNode forward = curr.next;
            curr.next = prev;

            prev = curr;
            curr = forward;
        }
        return prev;
    }

    //Leetcode 234
    public boolean isPalindrome(ListNode head) {

        ListNode mid = middleNode2(head);

        ListNode midN = mid.next;
        mid.next = null;

        ListNode tail = reverseList(midN);

        ListNode tailRef = tail;
        ListNode headRef = head;
        while(tailRef != null) {
            if(tailRef.val != headRef.val) return false;

            tailRef = tailRef.next;
            headRef = headRef.next;
        }

        tail = reverseList(tail);
        mid.next = tail;
        return true;
    }

    //Leetcode 143
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) return;

        ListNode temp = head;
        ListNode mid = middleNode2(temp);

        temp = mid.next;
        mid.next = null;        //<--imporatant point 
        ListNode tail = reverseList(temp);

        ListNode curr = head;
        while(tail != null) {
            ListNode forC = curr.next;      //forC = forward curr
            ListNode forT = tail.next;      //forT = forward tail

            curr.next = tail;
            tail.next = forC;

            curr = forC;
            tail = forT;
        }
    }

    public void resetReorderList(ListNode head) {
        if(head == null || head.next == null) return;

        ListNode curr = head;
        ListNode cNext = curr.next;      //currNext

        ListNode tempN = cNext;
        while(curr.next != null && curr.next.next != null) {
            ListNode forwardC = curr.next.next;
            curr.next = forwardC;
            curr = forwardC;
            
            if(forwardC.next != null) {
                cNext.next = forwardC.next;
                cNext = forwardC.next;
            }
            
        }
        reverseList(tempN);
        curr.next = cNext;
    }

    //Leetcode 21
    public ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        ListNode temp = null;
        if(l1 == null && l2 == null) {
            return l1;
        }
        if(l1 == null) return l2;
        else if(l2 == null) return l1;
        
        
        if(l1.val < l2.val) {
            temp = l1;
            l1 = l1.next;
        }else{
            temp = l2;
            l2 = l2.next;
        }
        
        ListNode curr = temp;
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                curr.next = l1;
                curr = l1;
                l1 = l1.next;
            }else{
                curr.next = l2;
                curr = l2;
                l2 = l2.next;
            }
        }
        if(l1 != null) curr.next = l1;
        else if(l2 != null) curr.next = l2;
        
        return temp;
    }

    //Leetcode 148
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode mid = middleNode2(head);
        ListNode midNext = mid.next;
        mid.next = null;

        return mergeTwoSortedLists(sortList(mid), sortList(midNext));
    }

    //Leetcode 23
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0) return null;
        return merge(lists, 0, lists.length - 1);
    }
    
    public ListNode merge(ListNode[] lists, int si , int ei) {
        if(si == ei) 
            return lists[si];
        
        int mid = (si + ei) / 2;
        ListNode start = merge(lists, si, mid);
        ListNode end = merge(lists, mid + 1, ei);
        
        return mergeTwoSortedLists(start, end);
    }
}
