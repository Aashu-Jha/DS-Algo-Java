import java.util.Stack;

public class QuestionAVL {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        int bal = 0;
        int h = 0;

        TreeNode() {

        }

        TreeNode(int val) {
            this.val = val;
        }
    }

    private static void updateHeightAndBal(TreeNode node) {
        int lh = -1;
        int rh = -1;

        if(node.left != null) lh = node.left.h; 
        if(node.right != null) rh = node.right.h;
        
        node.h = Math.max(lh, rh) + 1;
        node.bal = lh - rh;
    }

    private static TreeNode getRotation(TreeNode node) {
        updateHeightAndBal(node);
        if(node.bal == 2) {
            if(node.left.bal == 1) { //ll
                return rightRotation(node);
            }else{//lr
                node.left = leftRotation(node.left);
                return rightRotation(node);
            }
        }else if(node.bal == -2){
            if(node.right.bal == -1) { //rr
                return leftRotation(node);
            }else{ //rl
                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
        }
        return node;
    }

    protected static TreeNode insertNode(TreeNode node, int val) {
        if (node == null)
            return new TreeNode(val);

        if (node.val > val) {
            node.left = insertNode(node.left, val);
        }

        if (node.val < val) {
            node.right = insertNode(node.right, val);
        }

        return getRotation(node);
    }

    protected static TreeNode deleteNode(TreeNode node, int key) {
        if (node == null)
            return null;

        if (node.val == key) {
            if (node.left == null || node.right == null)
                return node.left == null ? node.right : node.left;

            int leftTreeMaxValue = getLeftTreeMaxValue(node.left);
            node.val = leftTreeMaxValue;
            node.left = deleteNode(node.left, node.val);
        } else if (node.val > key) {
            node.left = deleteNode(node.left, key);
        } else if (node.val < key) {
            node.right = deleteNode(node.right, key);
        }

        return node;
    }

    private static int getLeftTreeMaxValue(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.val;
    }

    private static TreeNode rightRotation(TreeNode node) {
        TreeNode B = node.left;
        TreeNode bkaRight = B.right;

        B.right = node;
        node.left = bkaRight;

        updateHeightAndBal(node);
        updateHeightAndBal(B);

        return B;
    }

    private static TreeNode leftRotation(TreeNode node) {
        TreeNode B = node.right;
        TreeNode bkaLeft = B.left;

        B.left = node;
        node.right = bkaLeft;

        updateHeightAndBal(node);
        updateHeightAndBal(B);

        return B;
    }

    public static void solve() {
        TreeNode root = null;
        for(int i = 0; i<= 14; i++) {
            root = insertNode(root, i * 10);
        }
    }

    public static void prettyPrintTree(TreeNode node, String prefix, boolean isLeft) {
        if (node == null) {
            System.out.println("Empty tree");
            return;
        }
    
        if (node.right != null) {
            prettyPrintTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    
        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);
    
        if (node.left != null) {
            prettyPrintTree(node.left, prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    public static void prettyPrintTree(TreeNode node) {
        prettyPrintTree(node,  "", true);
    }

    public static void main(String[] args) {
        solve();
    }

    public static class Pair {
        TreeNode pred = null;
        TreeNode prev = null;
        TreeNode succ = null;

        Pair() {

        }
    }

    //To find predecessor and successor in binary tree in O(n)/O(1)
    public static void findPreAndSucc(TreeNode root, int val, Pair pair) {
        if(root == null) return;
        findPreAndSucc(root.left, val, pair);
        if(root.val == val && pair.pred == null) pair.pred = pair.prev;
        if(pair.prev != null && pair.prev.val == val && pair.succ == null) pair.succ = root;
        pair.prev = root;
        findPreAndSucc(root.right, val, pair);
    }


    //To find predecessor and successor in binary search tree in O(n)/O(1)
    public static void findPreAndSuccInBST(TreeNode root, int val) {
        TreeNode pred = new TreeNode();
        TreeNode succ = new TreeNode();
        TreeNode curr = root;

        while(curr != null) {
            if(curr.val == val) {
                pred = curr.left;
                while(pred.right != null) {
                    pred = pred.right;
                }

                succ = curr.right;
                while(succ.left != null) {
                    succ = pred.left;
                }

            }else if(curr.val < val){
                pred = curr;
                curr = curr.right;
            }else{
                succ = curr;
                curr = curr.left;
            }
        }
    }

    
    


}
