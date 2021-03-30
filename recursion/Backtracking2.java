import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Backtracking2 {
    
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        // int[] nums = {1,1,2,3};
        // System.out.println(permuteUnique(nums));

        // int[] candidates = {2,3,6,7};
        // int target = 7;
        // System.out.println(combinationSum(candidates, target));

        // int k = 3;
        // int n = 7;
        // System.out.println(combinationSum3(k, n));

        queenCombination();
        
    }

    //QueenCombination
    public static void queenCombination() {
        // nQueens2D(new boolean[4][4],4,  0, "");

        nQueens2DOP(4,  0, "");

        // System.out.println(queenCombination1D(5, 0, 3, 0, ""));

        // System.out.println(queenPermutation1D(new boolean[5], 0, 3, 0, ""));
    }

    //leetcode 44[start]
    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        
        List<Integer> res = new ArrayList<>();
        return permute(nums, 0,res,  new boolean[nums.length]);
    }
    
    //faith: ( celebrity approach)
    public static List<List<Integer>> permute(int[] nums, int idx,List<Integer> res, boolean[] vis) {
        if(idx == nums.length) {
            List<List<Integer>> ans = new ArrayList<>(); 
            ans.add(new ArrayList<>(res));
            return ans;
        }
        
        List<List<Integer>> lis = new ArrayList<>(); 
        
        int prev = -11;     //use prev as a duplicate checker in same level (Recursion Tree)
        for(int i = 0; i < nums.length; i++){
            if(prev != -11 && prev == nums[i]) continue;
            if(!vis[i]) {
                vis[i] = true;
                res.add(nums[i]);
                lis.addAll(permute(nums, idx + 1, res, vis));
                res.remove(res.size() - 1);
                vis[i] = false;
                prev = nums[i];
                
            }
            
            
        }
        return lis;
        
    }
    //[leetcode 44] end

    //[Leetcode 39] start
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<Integer> lis = new ArrayList<>();
        return combination(candidates, target, 0, lis);
    }
    
    //faith: ( celebrity approach )call every element in loop until tar != 0 and also not use backward elements of loop
    //in next call.
    public static List<List<Integer>> combination(int[] coins, int tar, int idx, List<Integer> lis) {
        if(tar == 0) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(new ArrayList<>(lis));
            return ans;
        }
    
    
        List<List<Integer>> res = new ArrayList<>();
    
        for(int i = idx; i < coins.length; i++){
            if(tar - coins[i] >= 0) {
                lis.add(coins[i]);
                res.addAll(combination(coins, tar - coins[i], i , lis));
                lis.remove(lis.size() - 1);
            }
        }
    
        return res;
    }
    
    
    //faith: i'm IN or i'm OUT   (Subsequence method)
    //we will select one coin and call that index until target != 0
    //if we won't select the coin, then just pass next index
    public static List<List<Integer>> combinationSubs(int[] coins, int tar, int idx, List<Integer> lis) {
        if(tar == 0 || idx == coins.length){
            List<List<Integer>> ans = new ArrayList<>();
            if(tar == 0) {
                ans.add(new ArrayList<>(lis));
                return ans;
            }
            return ans;
        }
        
        List<List<Integer>> res = new ArrayList<>();
    
        int num = coins[idx];
        if(tar - num >= 0) {
            lis.add(num);
            res.addAll(combination(coins, tar - num, idx , lis));
            lis.remove(lis.size() - 1);
        }
        res.addAll(combination(coins, tar, idx + 1, lis));
    
    
        return res;
    }
    //[Leetcode 39] end

    //[Leetcode 216] start
    public static List<List<Integer>> combinationSum3(int k, int n) {

        List<Integer> lis = new ArrayList<>();
        return combinationSum3C(n, 1, k , lis);
    }
    
    //faith: (celebrity approach) call every element in loop until tar != 0 and also not use backward elements of loop
    //in next call.
    public static List<List<Integer>> combinationSum3C(int tar, int idx, int k, List<Integer> lis)     {
        if(tar == 0 || k == 0) {
            List<List<Integer>> ans = new ArrayList<>();
            if(tar == 0 && k == 0){
                ans.add(new ArrayList<>(lis));
            }
            return ans;
        }
    
        List<List<Integer>> res = new ArrayList<>();
    
    
        for(int i = idx ; i<= 9; i++ ){
            if(tar - i >= 0 && k > 0) {
                lis.add(i);
                res.addAll(combinationSum3C(tar - i, i + 1 , k - 1, lis));
                lis.remove(lis.size() - 1);
            }
        }
    
        return res;
    }
    //[Leetcode 216] end

    //QueenProblem [start] ===========================================

    //faith: (i'm in, i'm out) combinationsubs
    //tnb = total no of boxes , busf = boxes used so far, qpsf = queens placed so far
    public static int queenCombination1D(int tnb, int busf, int tnq, int qpsf, String asf) {
        if(qpsf == tnq || busf == tnb) {
            if(qpsf == tnq){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        int count = 0;

        count += queenCombination1D(tnb, busf + 1, tnq, qpsf + 1, asf + "b" + busf + "q" + qpsf + "\n");
        count += queenCombination1D(tnb, busf + 1, tnq, qpsf, asf);

        return count;
    }

    public static int queenPermutation1D(boolean[] tnb, int busf, int tnq, int qpsf, String asf) {
        if(qpsf == tnq || busf == tnb.length) {
            if(qpsf == tnq){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        int count = 0;

        if(!tnb[busf]){
            tnb[busf] = true;
            count += queenPermutation1D(tnb, 0, tnq, qpsf + 1, asf + "b" + busf + "q" + qpsf + "\n");
            tnb[busf] = false;
        }
        count += queenPermutation1D(tnb, busf + 1, tnq, qpsf, asf);

        return count;
    }

    //faith: (celebrity approach) Combination problem 
    //tnq = total no of queens 
    public static void nQueens2D(boolean[][] vis,int tnq,  int idx, String asf) {
        if(tnq <= 0 ){
            System.out.println(asf);
            return;
        }

        for(int i = idx; i < 16; i++) {
            int r = i / 4;
            int c = i % 4;

            if(r >= 0 && c >=0 && r < 4 && c < 4 && isQueenSafe(vis, r, c)) {
                vis[r][c] = true;
                nQueens2D(vis,tnq - 1,  i + 1, asf + "(" + r + "," + c + ")\n");
                vis[r][c] = false;
            }
        }
    }

    static boolean[] rows = new boolean[4];
    static boolean[] cols = new boolean[4];
    static boolean[] diag = new boolean[7];
    static boolean[] aDiag = new boolean[7];

    public static void nQueens2DOP(int tnq,  int idx, String asf) {
        if(tnq <= 0 ){
            System.out.println(asf);
            return;
        }

        for(int i = idx; i < 16; i++) {
            int r = i / 4;
            int c = i % 4;

            int  n = 4;
            if(!rows[r] && !cols[c] && !diag[r - c + n - 1] && !aDiag[r + c]) { 
                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r+c] = !aDiag[r+c];
                diag[r-c+n-1] = !diag[r-c+n-1];

                nQueens2DOP(tnq - 1,  i + 1, asf + "(" + r + "," + c + ")\n");

                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r+c] = !aDiag[r+c];
                diag[r-c+n-1] = !diag[r-c+n-1];
            }
        }
    }


    public static boolean isQueenSafe(boolean[][] vis, int row , int col) {
        int[][] dirs = {{0, -1}, {-1, -1}, {-1,0}, {-1,1}};

        for(int d = 0; d < dirs.length; d++ ){
            for(int i = 0; i < 4; i++){
                int r = row + dirs[d][0] * i;
                int c = col + dirs[d][1] * i;

            if(r >= 0 && c >= 0 && r < 4 && c < 4){
                if(vis[r][c]) return false;
            } 
            else 
                break;
            }
        }
        return true;
    }

    

    

    
    
}
