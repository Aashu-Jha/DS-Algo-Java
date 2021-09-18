public class lvl05_cut {
    public static void main(String[] args) {
        minimaAndMaxima();
    }

    //[start] https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
    static int matrixMultiplication(int N, int arr[])
    {
        return matrixDP(arr, 0, N - 1, new int[N][N]);
    }
    
    public static int memo(int[] arr, int si, int ei, int[][] dp) {
        if(si + 1 == ei) {
            return dp[si][ei] = 0;
        }
        
        if(dp[si][ei] != 0) return dp[si][ei];
        
        int minAns = (int) 1e9;
        
        for(int cut = si + 1; cut < ei; cut++) {
            int lans = memo(arr, si, cut, dp);
            int rans = memo(arr, cut, ei, dp);
            
            minAns = Math.min(minAns, lans + arr[si] * arr[cut] * arr[ei] + rans);
        }
        
        return dp[si][ei] = minAns;
    }
    
    public static int matrixDP(int[] arr, int SI, int EI, int[][] dp) {
        int n = arr.length;
        
        for(int gap = 1; gap < n; gap++) {
            for(int si = 0, ei = gap; ei < n; si++, ei++) {
                if(si + 1 == ei) {
                    dp[si][ei] = 0;
                    continue;
                }
                
                int minAns = (int) 1e9;
                
                for(int cut = si + 1; cut < ei; cut++) {
                    int lans = dp[si][cut]; // memo(arr, si, cut, dp);
                    int rans = dp[cut][ei]; // memo(arr, cut, ei, dp);
                    
                    minAns = Math.min(minAns, lans + arr[si] * arr[cut] * arr[ei] + rans);
                }
                
                dp[si][ei] = minAns;
            }
        }
        
        return dp[SI][EI];
    }
    //[end]

    // [start] https://www.geeksforgeeks.org/minimum-maximum-values-expression/
    public static class Pair{
        int minValue = (int) 1e9;
        int maxValue = -(int) 1e9;

        String minExpression = "";
        String maxExpression = "";

        Pair(){

        }

        Pair(int minValue, int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        Pair(int minValue, int maxValue, String minExpression, String maxExpression) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.minExpression = minExpression;
            this.maxExpression = maxExpression;
        }
    }

    public static int evaluate(int a, int b, Character oper) {
        if(oper == '+') return a + b;
        return a * b;
    }

    public static void minimaAndMaxima() {
        String str = "1+2*3+4*5";
        int n = str.length(); 
        Pair[][] dp = new Pair[n][n];

        Pair ans = evaluateExpPrint(str, 0, n - 1, dp);
        System.out.println("Min Value:" + ans.minValue + "\nMinExpression: " + ans.minExpression);
        System.out.println("Max Value:" + ans.maxValue + "\nMaxExpression: " + ans.maxExpression);
        // System.out.println(ans.minValue + " " + ans.maxValue);
    }

    public static Pair evaluateExpPrint(String str, int si, int ei, Pair[][] dp) {
        if(si == ei) {
            int val = str.charAt(si) - '0';
            return dp[si][ei] = new Pair(val, val, val + " ", val + " ");
        }

        if(dp[si][ei] != null) return dp[si][ei];

        Pair p = new Pair();
        for(int cut = si + 1; cut < ei; cut+= 2) {
            Pair lans = evaluateExpPrint(str, si, cut - 1, dp);
            Pair rans = evaluateExpPrint(str, cut + 1, ei, dp);

            int minValue = evaluate(lans.minValue, rans.minValue, str.charAt(cut));
            int maxValue = evaluate(lans.maxValue, rans.maxValue, str.charAt(cut));

            // p.minValue = Math.min(minValue, p.minValue);
            // p.maxValue = Math.max(maxValue, p.maxValue);

            if(minValue < p.minValue) {
                p.minValue = minValue;
                p.minExpression = "(" + lans.minExpression + " " + str.charAt(cut) + " " + rans.minExpression + ")"; 
            }

            if(maxValue > p.maxValue) {
                p.maxValue = maxValue;
                p.maxExpression = "(" + lans.maxExpression + " " + str.charAt(cut) + " " + rans.maxExpression + ")"; 
            }
        }

        return dp[si][ei] = p;
    }

    public static Pair evaluateExp(String str, int si, int ei, Pair[][] dp) {
        if(si == ei) {
            int val = str.charAt(si) - '0';
            return dp[si][ei] = new Pair(val, val);
        }

        if(dp[si][ei] != null) return dp[si][ei];

        Pair p = new Pair();
        for(int cut = si + 1; cut < ei; cut+= 2) {
            Pair lans = evaluateExp(str, si, cut - 1, dp);
            Pair rans = evaluateExp(str, cut + 1, ei, dp);

            int minValue = evaluate(lans.minValue, rans.minValue, str.charAt(cut));
            int maxValue = evaluate(lans.maxValue, rans.maxValue, str.charAt(cut));

            p.minValue = Math.min(minValue, p.minValue);
            p.maxValue = Math.max(maxValue, p.maxValue);
        }

        return dp[si][ei] = p;
    }
    // [end]

    // Leetcode 5
    public String longestPalindrome(String s) {
        int n = s.length();
        
        int len = 0;
        int si = 0;
        
        int[][] dp = new int[n][n];
        for(int gap = 0; gap < n; gap++) {
            for(int i = 0, j = gap; j < n; i++, j++) {
                if(gap == 0) dp[i][j] = 1;
                else if(gap == 1) dp[i][j] = (s.charAt(i) == s.charAt(j)) ? 2 : 0;
                else{
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && (dp[i + 1][j - 1] > 0)) ? dp[i + 1][j - 1] + 2 : 0;
                }
                
                if(len < dp[i][j]) {
                    len = dp[i][j];
                    si = i;
                }
            }
        }
        
        return s.substring(si, si + len);
    }
    // [ end]

    // [start] Leetcode 312
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for(int[] a: dp) Arrays.fill(a, -1);
        return maxCoinsDP(nums, dp, 0, n - 1);
    }
    
    public int maxCoins(int[] nums, int[][] dp, int si, int ei) {
        if(dp[si][ei] != -1) return dp[si][ei];
        
        int lval = si - 1 == -1 ? 1 : nums[si - 1];
        int rval = ei + 1 == nums.length ? 1 : nums[ei + 1];
        
        int maxAns = 0;
        for(int cut = si; cut <= ei; cut++) {
            int lans = (cut == si) ? 0 : maxCoins(nums, dp, si, cut - 1);
            int rans = (cut == ei) ? 0 : maxCoins(nums, dp, cut + 1, ei);
            
            maxAns = Math.max(maxAns, lans + lval * nums[cut] * rval + rans);
        }
        
        return dp[si][ei] = maxAns;
    }
    
    public int maxCoinsDP(int[] nums, int[][] dp, int SI, int EI) {
        for(int gap = 0; gap <= EI; gap++) {
            for(int si = 0, ei = gap; ei <= EI; si++, ei++) {
                int lval = si - 1 == -1 ? 1 : nums[si - 1];
                int rval = ei + 1 == nums.length ? 1 : nums[ei + 1];

                int maxAns = 0;
                for(int cut = si; cut <= ei; cut++) {
                    int lans = (cut == si) ? 0 : dp[si][cut - 1]; // maxCoins(nums, dp, si, cut - 1);
                    int rans = (cut == ei) ? 0 : dp[cut + 1][ei]; // maxCoins(nums, dp, cut + 1, ei);

                    maxAns = Math.max(maxAns, lans + lval * nums[cut] * rval + rans);
                }

                dp[si][ei] = maxAns;
            }
        }
        return dp[SI][EI];
        
    }
    //[end]
}
