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

    //Leetcode 337
    public int rob(TreeNode root) {
        int[] ans = rob_(root);
        return Math.max(ans[0], ans[1]);
    }
    //Intuition: ans.robbed = left.notrobbed + node.val + right.notroobed;
    // ans.notroobed = Math.max(left.notrobbed, left.robbed) + Math.max(right.notrobbed, right.roobed);
    //In this, we are using array but also use class and define robbed , notrobbed.
    
    public int[] rob_(TreeNode root) {
        if(root == null) return new int[]{0,0};
        
        int[] lans = rob_(root.left);
        int[] rans = rob_(root.right);
        
        int[] myAns = new int[2];
        myAns[0] = lans[1] + root.val + rans[1];
        myAns[1] = Math.max(lans[0], lans[1]) + Math.max(rans[0], rans[1]);
        
        return myAns;
    }
    //[end]

    //Leetcode 653
    public boolean findTarget(TreeNode root, int k) {
        if(root == null) return false;
        Stack<TreeNode> miner = new Stack<TreeNode>(); 
        fillLeft(root, miner);
        Stack<TreeNode> maxer = new Stack<TreeNode>(); 
        fillRight(root, maxer);
        
        
        while(!miner.isEmpty() && !maxer.isEmpty() && miner.peek().val < maxer.peek().val) {
            int sum = miner.peek().val + maxer.peek().val;
            
            if(sum > k) {
                TreeNode node = maxer.pop();
                fillRight(node.left, maxer);
            }else if(sum < k) {
                TreeNode node = miner.pop();
                fillLeft(node.right, miner);
            }else{
                return true;
            }
        }
        
        
        return false;
    }
    
    public void fillLeft(TreeNode root, Stack<TreeNode> st) {
        TreeNode curr = root;
        
        while(curr != null) {
            st.push(curr);
            curr = curr.left;
        }
    }
    
    public void fillRight(TreeNode root, Stack<TreeNode> st) {
        TreeNode curr = root;
        
        while(curr != null) {
            st.push(curr);
            curr = curr.right;
        }
    }
    //[end]


    //Leetcode 230
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr = root;
        while(curr != null) {
            TreeNode next = curr.left;
            if(next == null) {
                if(k == 1) return curr.val;
                k--;
                curr = curr.right;
            }
            else{
                TreeNode rightMost = rightMostNode(next, curr);
                if(rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                }else{
                    rightMost.right = null;
                    if(k == 1) return curr.val;
                    k--;
                    curr = curr.right;
                }
            }
        }
        return -1;
    }
    
    public static TreeNode rightMostNode(TreeNode next, TreeNode curr) {
        while(next.right != null && next.right != curr) {
            next = next.right;
        }
        return next;
    }
    //[end]

    //Leetcode 1372
    public int longestZigZag(TreeNode root) {
        int[] res = dfs(root);
        return res[2];
        
    }
    
    // int[] arr; arr[0] = root.left[1], arr[1] = root.right[0], 
    //arr[2] = max(arr[0],arr[1], left[2], right[2]);
    public int[] dfs(TreeNode root) {
        if(root == null) return new int[]{-1,-1,0};
        
        int[] arr1 = dfs(root.left);
        int[] arr2 = dfs(root.right);
        
        int[] res = new int[3];
        res[0] = arr1[1] + 1;
        res[1] = arr2[0] + 1;
        res[2] = Math.max(res[1], Math.max(arr1[2], arr2[2]));
        res[2] = Math.max(res[2], res[0]);
        return res;
    }
    //[end]
}
