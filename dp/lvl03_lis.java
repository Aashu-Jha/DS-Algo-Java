import java.util.ArrayList;
import java.util.Arrays;

public class lvl03_lis {
    // {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14};
    public static void main(String[] args) {
        solve();
    }

    public static void print1D(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void solve() {
        int[] nums = new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14};
        // int[] nums = new int[]{2,2,2,2,2};
        // int[] nums = new int[]{0, 12,2,35,2,6,3,1,6,7,3,9,3};
        // System.out.println(lengthOfLISDP(nums));
        // System.out.println(minDeletion(nums));
        // System.out.println(maximumSumIncSubs(nums));
        // System.out.println(maximumSumIncSubswithMaxLength(nums));
        printNoOfLIS(nums);
        
    }

    //[start] Leetcode 300
    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int[] dp = new int[nums.length];
        int len = 1;
        for (int i = 1; i < nums.length; i++) {
            len = Math.max(len, recursion(nums, i, dp));
        }
        return len;
    }

    //memo
    private static int recursion(int[] nums, int i, int[] dp) {
        if (dp[i] != 0)
            return dp[i];

        int res = 1;
        for (int j = i - 1; j >= 0; j--) {
            if (nums[j] < nums[i]) {
                res = Math.max(res, recursion(nums, j, dp) + 1);
            }
        }

        return dp[i] = res;
    }

    //dp
    public static int lengthOfLISDP(int[] nums) {
        int[] dp = new int[nums.length];
        int result = 1;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            result = Math.max(result, dp[i]);
        }
        print1D(dp);
        return result;
    }
    //[end]

    //min-deletion to make unsorted array sorted..
    public static int minDeletion(int[] nums) {
        int ans = lengthOfLISDP(nums);
        return nums.length - ans;
    }

    //maximum-sum-increasing subsequence
    public static int maximumSumIncSubs(int[] nums) {
        int[] dp = new int[nums.length];
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = nums[i];
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + nums[i], dp[i]);
                }
            }
            result = Math.max(result, dp[i]);
            System.out.print(result + " ");
        }
        System.out.println();
        return result;
    }

    // [Doubt: Giving wrong answer]maximum-sum-increasing-subsequence with maximum Length..
    public static int maximumSumIncSubswithMaxLength(int[] nums) {
        int[] dp = new int[nums.length];
        int[] len = new int[nums.length];
        int maxLen = 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = nums[i];
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if(len[j] + 1 >= len[i]) {
                        len[i] = len[j] + 1;
                        dp[i] = Math.max(dp[j] + nums[i], dp[i]);
                    }else{
                        dp[i] = Math.max(dp[j] + nums[i], dp[i]);
                    }
                }
            }
            if(len[i] > maxLen) {
                result = dp[i];
                maxLen = len[i];
            }else if(len[i] == maxLen){
                result = Math.max(result, dp[i]);
            }
            else
                result = Math.max(result, dp[i]);
            System.out.print(result + " ");
        }
        // print1D(len);
        System.out.println();
        return result;
    }
    //[end]

    //[start] https://practice.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1#
    public int LongestBitonicSequence(int[] nums)
    {
        int[] llis = lis(nums);
        int[] rris = ris(nums);
        
        int maxx = 0;
        for(int i = 0; i < nums.length; i++) {
            maxx = Math.max(maxx, llis[i] + rris[i] - 1);
        }
        return maxx;
    }
    
    public int[] lis(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        for(int i = 1; i< n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) dp[i] = Math.max(dp[j] + 1, dp[i]);
            }
        }
        // print(dp);
        return dp;
    }
    
    public int[] ris(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        for(int i = n - 2; i >= 0; i--) {
            for(int j = n - 1; j > i; j--) {
                if(nums[j] < nums[i]) dp[i] = Math.max(dp[j] + 1, dp[i]);
            }
        }
        return dp;
    }
    //[end]

    //leetcode 673
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] len = new int[n];
        int[] count = new int[n];

        int maxLen = 0, maxCount = 0;

        for(int i= 0; i< n; i++) {
            len[i] = 1;
            count[i] = 1;
            for(int j = i - 1; j >= 0; j--) {
                if(nums[j] < nums[i]) {
                    if(len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1;
                        count[i] = count[j];
                    }else if(len[j] + 1 == len[i]) {
                        count[i] += count[j];
                    }
                }
            }
            if(len[i] > maxLen) {
                maxLen = len[i];
                maxCount = count[i];
            }else if(len[i] == maxLen) {
                maxCount += count[i];
            }
        }
        return maxCount;
    }

    //Leetcode 673 extension- also print paths 
    public static void printNoOfLIS(int[] nums) {
        int n = nums.length;
        int[] len = new int[n];

        int maxLen = 0;

        for(int i= 0; i< n; i++) {
            len[i] = 1;
            for(int j = i - 1; j >= 0; j--) {
                if(nums[j] < nums[i]) {
                    len[i] = Math.max(len[i], len[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, len[i]);
        }

        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for(int i = 0; i<= maxLen; i++) list.add(new ArrayList<>());

        for(int i = 0; i< n; i++) 
            list.get(len[i]).add(i);
        
        for(Integer i: list.get(maxLen)) {
            printAllLIS(list, nums, i, maxLen, "");
        }
    }

    public static void printAllLIS(ArrayList<ArrayList<Integer>> list, int[] nums, int idx, int len, String ans) {
        if(len == 1) {
            System.out.print(ans + nums[idx]);
            System.out.println();
            return;
        }

        for(Integer i : list.get(len - 1)) {
            if(i < idx && nums[i] < nums[idx]) printAllLIS(list, nums, i, len - 1, ans + nums[i] + ", ");
        }
    }
}
