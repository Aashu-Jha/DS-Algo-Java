import java.util.Arrays;

public class lvl04_tar {

    public static void main(String[] args) {
        solve();
    }
    
    public static void print1D(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void print2D(int[][] arr) {
        for(int[] ar: arr) {
            print1D(ar);
        }
    }
    
    public static void solve() {
        // coinChange();
        noOfVariables();
    }

    public static void coinChange() {
        int tar = 10;
        int[] coins = new int[]{2,3,5,7};

        System.out.println(coinChangeCombinationInfiDP(coins, tar));

    }

    public static int coinChangePermuteInfi(int[] coins, int tar) {
        int[] dp = new int[tar + 1];
        Arrays.fill(dp, -1);
        int ans = coinChangePermuteInfi(coins, tar, dp);
        print1D(dp);
        return ans;
    }

    public static int coinChangePermuteInfi(int[] coins, int tar, int[] dp) {
        if(tar == 0) return dp[tar] = 1; 

        if(dp[tar] != -1 ) return dp[tar];

        int count = 0;
        for(int val: coins) {
            if(tar - val >= 0) count += coinChangePermuteInfi(coins, tar - val, dp);
        }

        return dp[tar] = count;
    }

    public static int coinChangePermuteInfiDP(int[] coins, int Tar) {
        int[] dp = new int[Tar + 1];
        dp[0] = 1;
        for(int tar = 1; tar <= Tar; tar++) {
            int count = 0;
            for(int val: coins) {
                if(tar - val >= 0) count += dp[tar - val];
            }

            dp[tar] = count;
        }
        // print1D(dp);
        return dp[Tar];
    }

    public static int coinChangeCombinationInfi(int[] coins, int tar) {
        int[][] dp = new int[coins.length][tar + 1];
        for(int[] a: dp) Arrays.fill(a, -1);
        int ans = coinChangeCombinationInfi(coins, tar,0, dp);
        print2D(dp);
        return ans;
    }

    public static int coinChangeCombinationInfi(int[] coins, int tar, int idx,  int[][] dp) {
        if(tar == 0) return dp[idx][tar] = 1; 

        if(dp[idx][tar] != -1 ) return dp[idx][tar];

        int count = 0;
        for(int i = idx; i < coins.length; i++) {
            if(tar - coins[i] >= 0) count += coinChangeCombinationInfi(coins, tar - coins[i], i,  dp);
        }

        return dp[idx][tar] = count;
    }

    public static int coinChangeCombinationInfiDP(int[] coins, int Tar) {
        int[] dp = new int[Tar + 1];
        dp[0] = 1;
        for(int val: coins) {
            int count = 0;
            for(int tar = val; tar <= Tar; tar++) {
                if(tar - val >= 0) dp[tar] += dp[tar - val];
            }
        }
        // print1D(dp);
        return dp[Tar];
    }

    //Leetcode 322
    public int coinChangeCount(int[] coins, int tar) {
        int[] dp = new int[tar + 1];
        Arrays.fill(dp, (int) 1e9);
        int ans = coinChange(coins, tar, dp);
        return (ans == (int) 1e8) ? -1 : ans;
    }

    public int coinChange(int[] coins, int tar,int[] dp) {
        if(tar == 0) return 0; 

        if(dp[tar] != (int) 1e9) return dp[tar];
        
        int min = (int) 1e8;
        for(int val: coins) {
            if(tar - val >= 0) min = Math.min(min, coinChange(coins, tar - val, dp) + 1);
        }

        return dp[tar] = min;
    }
    //[end]

    // https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/
    public static void noOfVariables() {
        int[] nums = new int[]{2,3,5,7};
        int total = 10;

        int ans = noOfVariables(nums, total, 0, new int[nums.length]);
        System.out.println(ans);
    }

    public static int noOfVariables(int[] nums, int total, int idx, int[] coff) {
        if(total == 0) {
            for(int i = 0; i< coff.length; i++) {
                System.out.print(nums[i] + "(" + coff[i] + ")");
                if(i != coff.length - 1) {
                    System.out.print(" + ");
                }
            }
            System.out.println(" = " + 10);
            return 1;
        }

        int count = 0;
        for(int i = idx; i< nums.length; i++) {
            coff[i]++;
            if(total - nums[i] >= 0) count += noOfVariables(nums, total - nums[i], i, coff);
            coff[i]--;
        }

        return count;
    }
}
