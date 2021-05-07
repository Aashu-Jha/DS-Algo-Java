public class QuestionBST {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // [start]Leetcode 701
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return root;
        }
        insertNode(root, val);
        return root;
    }

    public TreeNode insertNode(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        if (root.val > val) {
            root.left = insertNode(root.left, val);
        }

        if (root.val < val) {
            root.right = insertNode(root.right, val);
        }

        return root;
    }
    // [end]

    //[start] Leetcode 450
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;

        if (root.val == key) {
            if (root.left == null || root.right == null)
                return root.left == null ? root.right : root.left;

            int leftTreeMaxValue = getLeftTreeMaxValue(root.left);
            root.val = leftTreeMaxValue;
            root.left = deleteNode(root.left, root.val);
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }

        return root;
    }

    public int getLeftTreeMaxValue(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.val;
    }
    //[end]

    //Leetcode 108
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArray(nums,0,nums.length - 1);
    }
    
    public TreeNode sortedArray(int[] nums, int si, int ei) {
        if(si > ei) return null;
        
        int mid = (si + ei) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        
        node.left = sortedArray(nums, si , mid - 1);
        node.right = sortedArray(nums, mid + 1, ei);
        
        return node;
    }
    //[end]

    //Leetcode 235
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while(curr != null) {
            if(curr.val > p.val && curr.val > q.val){
                curr = curr.left;
            }
            else if(curr.val < p.val && curr.val < q.val) {
                curr = curr.right;
            }
            else{
                return curr;
            }
        }
        return  null;
    }
    //[end]

    
}
