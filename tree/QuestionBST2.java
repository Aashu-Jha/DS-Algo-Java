import java.util.LinkedList;

public class QuestionBST2 {

    public class TreeNode{
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public class Pair {
        TreeNode pred = null;
        TreeNode prev = null;
        TreeNode succ = null;
    }

    //To find predecessor and successor in binary tree in O(n)/O(1)
    public void findPreAndSucc(TreeNode root, int val, Pair pair) {
        findPreAndSucc(root.left, val, pair);
        if(root.val == val && pair.pred == null) pair.pred = pair.prev;
        if(pair.prev != null && pair.prev.val == val && pair.succ == null) pair.succ = root;
        findPreAndSucc(root.right, val, pair);
    }

    //Leetcode-173
    class BSTIterator {
        Stack<TreeNode> st;
        public BSTIterator(TreeNode root) {
            st = new Stack<>();
            fillBST(root);
        }
        
        public void fillBST(TreeNode root) {
            while(root != null) {
                st.push(root);
                root = root.left;
            }
        }
        
        public int next() {
            TreeNode node = st.pop();
            fillBST(node.right);
            return node.val;
        }
        
        public boolean hasNext() {
            return !st.isEmpty();
        }
    }

    //Leetcode - 510
    public TreeNode inorderSuccessor(TreeNode node) {
        if(node.right != null) {
            TreeNode succ = node.right;
            while(succ.left != null) {
                succ = succ.left;
            }
            return succ; 
        }else{
            while(node != null) {
                //It's a code
                // if(node.parent != null && node.parent.left == node) return node.parent;
                // node = node.parent;
            }
            return null;
        }
    }


    //Leetcode 114
    public void flatten(TreeNode root) {
        solver(root);
    }
    
    public TreeNode solver(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)) return root;
        
        TreeNode tail = solver(root.left);
        TreeNode right = solver(root.right);
        
        if(tail != null) {
            tail.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        
        if(right != null) return right;
        else 
            return tail;
    }
    //[end]
    
    //Leetcode 106
    //in and post to BT
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length - 1;
        return postToTree(inorder, 0, n, postorder, 0, n);
    }
    
    public TreeNode postToTree(int[] in, int isi, int iei, int[] p, int psi, int pei) {
        if(psi > pei) return null;
        
        TreeNode node = new TreeNode(p[pei]);
        
        int idx = isi;
        while(in[idx] != p[pei]) idx++;
        int tnl = idx - isi;
        
        node.left = postToTree(in, isi, idx - 1, p, psi, psi + tnl - 1);
        node.right = postToTree(in, idx + 1, iei, p, psi + tnl, pei - 1);
        return node;
    }
    //[end]

    
    //Leetcode 105
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Stack<TreeNode> st = new Stack<>();
        int idxPO = 0;
        int idxIO = 0;
        
        TreeNode prev = null;
        TreeNode root = new TreeNode(preorder[idxPO++]);
        st.push(root);
        while(idxPO < preorder.length) {
            while(!st.isEmpty() && st.peek().val == inorder[idxIO]) {
                prev = st.pop();
                idxIO++;
            }
            TreeNode node = new TreeNode(preorder[idxPO++ ]);
            if(prev != null) {
                prev.right = node;
            }else if(!st.isEmpty()) {
                TreeNode currTop = st.peek();
                currTop.left = node;
            }
            st.push(node);
            prev = null;
            
        }
        return root;
    }
    //[end]

    public static TreeNode rightMostNode(TreeNode next, TreeNode curr) {
        while(next.right != null && next.right != curr) {
            next = next.right;
        }
        return next;
    }

    public static void morrisTraversal(TreeNode root) {
        TreeNode curr = root;
        while(curr != null) {
            TreeNode next = curr.left;
            if(next == null) {
                System.out.print(curr.val + " ");
                curr = curr.right;
            }else{
                TreeNode rightMost = rightMostNode(next, curr);
                if(rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                }else{
                    rightMost.right = null;
                    System.out.print(curr.val + " ");
                    curr = curr.right;
                }
            }
        }

    }

    class IterPair {
        TreeNode node;
        boolean leftDone = false;
        boolean rightDone = false;
        boolean selfDone = false;

        IterPair(TreeNode node) {
            this.node = node;
        }
    }

    //Binary IterTraversal
    public static void iterTraversal(TreeNode root) {
        LinkedList<IterPair> ll = new LinkedList<>();
        ll.addFirst(new IterPair(root, false, false, false));
        while(ll.size() != 0) {
            IterPair pair = ll.getFirst();
            if(!pair.leftDone) {
                pair.leftDone = true;
                if(pair.node.left != null) 
                    ll.addFirst(node.left);
            }else if(!pair.rightDone) {
                pair.rightDone = true;
                if(pair.node.right != null) 
                    ll.addFirst(node.right);
            }else if(!pair.selfDone) {
                pair.selfDone = true;
                System.out.println(pair.node.val + " ");
            }else {
                ll.removeFirst();
            }
        }
    }

    //Leetcode 1008
    int bst_idx = 0;
    public TreeNode constructBSTFromPreOrder(int[] arr, int lr, int rr) {
        if(bst_idx == arr.length || arr[bst_idx] < lr || arr[bst_idx] > rr) return null;

        TreeNode node = new TreeNode(arr[bst_idx]);
        node.left = constructBSTFromPreOrder(arr, lr, node.val);
        node.right = constructBSTFromPreOrder(arr, node.val, rr);

        return node;
    }

    public TreeNode bstFromPre(int[] arr) {
        return constructBSTFromPreOrder(arr, -(int)1e9, (int)1e9);
    }

    
}
