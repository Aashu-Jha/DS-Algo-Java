import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Question1{

    public class TreeNode{
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val){
            this.val = val;
        }
    }

    public void solve() {
        
    }

    public int size(TreeNode node){
        return node==null?0:size(node.left) + size(node.right) + 1;
    }

    public int height(TreeNode node){
        //for edges node == -1 , for nodes node == 0;
        return node==null ? -1 : Math.max(height(node.left), height(node.right)) + 1;
    }

    public boolean find(TreeNode node,int val){
        if(node==null) return false;

        if(node.val == val) return true;

        return find(node.left,val) || find(node.right,val);
    }

    public boolean nodeToRootPath(TreeNode node, int val, ArrayList<TreeNode> arr) {
        if(node == null) return false;
        if(node.val == val) {
            arr.add(node);
            return true;
        }

        boolean res = nodeToRootPath(node.left, val, arr) || nodeToRootPath(node.right, val, arr);
        if(res) 
            arr.add(node);

        return res;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        ArrayList<TreeNode> list1 = new ArrayList<>();
        ArrayList<TreeNode> list2 = new ArrayList<>();

        nodeToRootPath(root, p.val, list1);
        nodeToRootPath(root, q.val, list2);

        int list1_size = list1.size() - 1;
        int list2_size = list2.size() - 1;
        
        TreeNode LCA = null;
        while(list1_size >= 0 && list2_size >= 0) {
            if(list1.get(list1_size) != list2.get(list2_size))
                break;
            
            LCA = list1.get(list1_size);
            list1_size--;
            list2_size--;
        }
        return LCA;
    }

    //GFG - kDistance to root
    ArrayList<Integer> Kdistance(TreeNode node, int k)
     {
          if(node == null || k == 0) {
              ArrayList<Integer> res = new ArrayList<>();
              if(k == 0 && node != null) {
                  res.add(node.val);
              }
              return res;
          }
          ArrayList<Integer> left = Kdistance(node.left, k - 1);
          ArrayList<Integer> right = Kdistance(node.right, k - 1);
          
          ArrayList<Integer> ans = new ArrayList<>();
          ans.addAll(left);
          ans.addAll(right);
          return ans;
     }

    //Leetcode 863[start]
    // for beginners- use nodeToRoot and Kdistance func to solve this problem
    //this is optimised solution⏬
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        
        List<Integer> res = new ArrayList<>();
        find(root, target, K, null, res);
        return res;
    }
    
    public int find(TreeNode node,TreeNode target, int r,TreeNode block, List<Integer> res){
        if(node==null) {
            return -1;
        }

        if(node == target) {
            Kdistance(node, null , r , res);
            return 1;
        }

        int i = find(node.left,target, r, block, res);
        if(i > -1) {
            Kdistance(node, node.left, r - i , res);
            return i + 1;
        }
        int j = find(node.right,target, r, block, res);
        if(j > -1) {
            Kdistance(node, node.right, r - j, res);
            return j + 1;
        }
        return -1;
    }
    
    static void Kdistance(TreeNode node,TreeNode block, int k, List<Integer> res)
     {
          if(node == null || k < 0 ||  node == block) return;
          
          if(k == 0) {
              res.add(node.val);
          }

        Kdistance(node.left,block, k - 1,res);
        Kdistance(node.right,block,  k - 1, res);
        
     }
     //[end] Leetcode 863

     //[start] Leetcode 543
     public static int diameterOfBinaryTree(TreeNode root) {
        int[] ans = diameter_01(root);
        return ans[0];
    }

    public static int[] diameter_01(TreeNode node) {
        if(node == null) return new int[]{-1,-1};

        int[] lt = diameter_01(node.left);
        int[] rt = diameter_01(node.right);

        int[] ans = new int[2];
        ans[0] = Math.max(Math.max(lt[0], rt[0]), lt[1] + rt[1] + 2);
        ans[1] = Math.max(lt[1], rt[1]) + 1;

        return ans;
    }

    //second method(Optimised)
    public static int diameter_02(TreeNode node, int[] ans) {
        if(node == null) return -1;

        int lt = diameter_02(node.left, ans);
        int rt = diameter_02(node.right, ans);

        ans[0] = Math.max(ans[0], lt + rt + 1);

        return Math.max(lt, rt) + 1;
    }
    //[end]

    //Leetcode 102
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null) return false;
        
        if(root.left == null && root.right == null) 
            return (targetSum - root.val) == 0;
        
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    //[start] Leetcode 113
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        pathSum(root, targetSum, list, res);
        return res;
    }
    
    public void  pathSum(TreeNode root, int targetSum, List<Integer> list, List<List<Integer>> res) {
        if(root == null) return;
        
        if(root.left == null && root.right == null) {
            if(targetSum - root.val == 0) {
                List<Integer> ans = new ArrayList<>(list);
                ans.add(root.val);
                res.add(new ArrayList<>(ans));
            }
            return;
        }
        
        list.add(root.val);
        pathSum(root.left, targetSum - root.val, list, res);
        pathSum(root.right, targetSum - root.val, list, res);
        list.remove(list.size() - 1);
    
    }
    //[end]


    //GFG https://practice.geeksforgeeks.org/problems/maximum-path-sum/1#
    static int maxPathSum(TreeNode root)
    { 
        int[] ans= new int[]{-(int)1e9};
        diameter_01(root,ans);
        return ans[0];
    } 
    
    
    public static int diameter_01(TreeNode node, int[] ans) {
        if(node == null) return -(int)1e9;
        
        if(node.left == null && node.right == null) return node.val;

        int lt = diameter_01(node.left,ans);
        int rt = diameter_01(node.right,ans);
        
        if(node.left != null && node.right != null)
            ans[0] = Math.max(ans[0], lt + rt + node.val);
         

        return Math.max(lt, rt) + node.val;
    }
    //[end]

    //[start] Leetcode 124
    public int maxPathSum2(TreeNode root) {
        int res = maxSinglePath(root);
        return Math.max(maxDia,res);
    }
    
    int maxDia = -(int) 1e9;
    public int maxSinglePath(TreeNode node) {
        if(node == null) return -(int)1e7;
        
        if(node.left == null && node.right == null) return node.val;
        
        int li = maxSinglePath(node.left);
        int ri = maxSinglePath(node.right);
        
        int num = node.val;
        
        maxDia = Math.max(maxDia, li + ri + num); //diameter
        maxDia = Math.max(maxDia, Math.max(li, ri)); //root.left or root.right
        
        
        int res = Math.max(li,ri) + node.val;
        res = Math.max(res, num);
        maxDia = Math.max(maxDia, res);//root.child + root.val
        
        return res;
    }
    //[end]

    //Leetcode 199
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        
        LinkedList<TreeNode> list =new LinkedList<>();
        
        list.addLast(root);
        while(list.size() != 0){
            int size = list.size();
            
            while(size--> 0) {
                TreeNode rn = list.removeFirst();
                if(size == 0)res.add(rn.val);
                
                if(rn.left != null) list.addLast(rn.left);
                if(rn.right != null) {
                    list.addLast(rn.right);
                }
            }
        }
        
        return res;
        
    }

    //Leetcode 102
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> que = new LinkedList<>();
        
        que.addLast(root);
        
        while(que.size() != 0) {
            int size = que.size();
            
            while(size-- > 0) {
                TreeNode rn = que.removeFirst();
                list.add(rn.val);
                
                if(rn.left != null) que.addLast(rn.left);
                if(rn.right != null) que.addLast(rn.right);
                
            }
            res.add(new ArrayList<>(list));
            list = new ArrayList<>();
        }
        
        return res;
    }

    //Leetcode 99
    public void recoverTree(TreeNode root) {
        recoverTree_(root);
        if(a != null) {
            int temp = a.val;
            a.val = b.val;
            b.val = temp;
        }
        
    }

    
    TreeNode prev = null, a = null, b = null;
    public boolean recoverTree_(TreeNode root) {
        if(root == null) return true;
        
        if(!recoverTree_(root.left)) return false;
        
        if(prev != null && prev.val > root.val) {
            b = root;
            if(a == null) {
                a = prev;
            }else
                return false;
        }
        prev = root;
        if(!recoverTree_(root.right)) return false;
        return true;
    }

    
    //Leetcode 98
    long preVal = -(long) 1e13;
    public boolean isValidBST(TreeNode root) {
        if(root == null) return true;
        
        if(!isValidBST(root.left)) return false;
        
        if(root.val <= preVal) return false;
        
        preVal = root.val;
        if(!isValidBST(root.right))
            return false;
        
        return true;
    }


}