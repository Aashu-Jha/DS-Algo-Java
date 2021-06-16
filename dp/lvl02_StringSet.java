import java.util.Arrays;

public class lvl02_StringSet{
    
    public static void main(String[] args) {
        palindromicSubs();
    }

    public static void palindromicSubs() {
        String str = "abbcdbbc";
        int n = str.length(); 
        // int[][] dp = new int[n][n];
        String[][] dp = new String[n][n];
        for(String[] ar: dp) Arrays.fill(ar, "");

        System.out.println(palindromicSubsDP(str, 0, n - 1, dp));
    }

    //[start] Leetcode 516
    public static int palindromicSubs(String str, int li, int ri, int[][] dp) {
        if(li >= ri) {
            return (li == ri) ? 1 : 0;
        }

        if(dp[li][ri] != -1) return dp[li][ri];

        if(str.charAt(li) == str.charAt(ri)) 
            return dp[li][ri] = palindromicSubs(str, li + 1, ri - 1, dp) + 2;
        
            return dp[li][ri] = Math.max(palindromicSubs(str, li + 1, ri, dp), palindromicSubs(str, li, ri - 1, dp));
        
    }

    public static int palindromicSubsDP(String str, int LI, int RI, int[][] dp) {
        for(int gap = 0; gap < dp.length; gap++) {
            for(int li = 0, ri = gap; li < dp.length && ri < dp.length; li++, ri++ ) {
                if(li == ri) {
                    dp[li][ri] = 1;
                    continue;
                }
        
                if(str.charAt(li) == str.charAt(ri)) 
                    dp[li][ri] = dp[li + 1][ri - 1] + 2 ;
                else
                    dp[li][ri] = Math.max(dp[li + 1][ri] , dp[li][ri - 1]);
            }
        }
        return dp[LI][RI];
        
    }

    public static String palindromicSubsDP(String str, int LI, int RI, String[][] dp) {
        for(int gap = 0; gap < dp.length; gap++) {
            for(int li = 0, ri = gap; li < dp.length && ri < dp.length; li++, ri++ ) {
                if(li == ri) {
                    dp[li][ri] = str.charAt(li) + "";
                    continue;
                }
        
                if(str.charAt(li) == str.charAt(ri)) 
                    dp[li][ri] = str.charAt(li) + dp[li + 1][ri - 1] + str.charAt(ri);
                else
                    dp[li][ri] = (dp[li + 1][ri].length() >= dp[li][ri - 1].length()) ? dp[li + 1][ri] : dp[li][ri - 1];
            }
        }
        return dp[LI][RI];
        
    }
    //[end]

    //[start] 115
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        
        int[][] dp = new int[n + 1][m + 1];
        for(int[] a: dp) Arrays.fill(a, -1);
        
        return numDistinctDP(s, t, dp, n, m);
    }

    private int numDistinct(String s, String t, int[][] dp, int si, int ti) {
        if(ti == 0) {
            return dp[si][ti] = 1;
        }
        
        if(si < ti) {
            return dp[si][ti] = 0;
        }
        
        if(dp[si][ti] != -1) return dp[si][ti]; 
        
        int count = 0;
        if(s.charAt(si - 1) == t.charAt(ti - 1))
            count = numDistinct(s, t, dp, si - 1, ti - 1) + numDistinct(s, t, dp, si - 1, ti);
        else
            count = numDistinct(s, t, dp, si - 1, ti);
        
        
        return dp[si][ti] =  count;
    }
    //[end]

    //[start] Leetcode 1143
    public static void lcs() {
        String text1 = "abcde";
        String text2 = "ace";

        System.out.println(longestCommonSubsequence(text1, text2));
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        
        int[][] dp;
        dp = new int[n + 1][m + 1]; 
        for(int[] a: dp) Arrays.fill(a, -1);
        int ans = lcs(text1, text2, dp, n, m);
        // print2D(dp);
        return ans;
    }
    
    public static int lcs(String s, String t, int[][] dp, int si, int ti) {
        if(si == 0 || ti == 0) {
            return 0;
        }
        
        if(dp[si][ti] != -1) return dp[si][ti]; 
        
        int count = 0;
        if(s.charAt(si - 1) == t.charAt(ti - 1))
            count = lcs(s, t, dp, si - 1, ti - 1) + 1;
        else{
            count = Math.max(lcs(s, t, dp, si - 1, ti),lcs(s, t, dp, si, ti - 1));   
        }
        
        return dp[si][ti] =  count;
    }

    public static int lcsDP(String s, String t, int[][] dp, int SI, int TI) {
        for(int si = 1; si <= SI; si++) {
            for(int ti = 1; ti <= TI; ti++) {
                if(s.charAt(si - 1) == t.charAt(ti - 1))
                    dp[si][ti] = dp[si - 1][ti - 1] + 1;
                else{
                    dp[si][ti]  = Math.max(dp[si - 1][ti],dp[si][ti - 1]);   
                }

            }
        }
        return dp[SI][TI];
    }
    //[end]

    //Leetcode 1035
    public int maxUncrossedLines(int[] nums1, int[] nums2, int SI, int TI, int[][] dp) {
        for(int si = 1; si <= SI; si++) {
            for(int ti = 1; ti <= TI; ti++) {
                if(nums1[si - 1] == nums2[ti - 1])
                    dp[si][ti] = dp[si - 1][ti - 1] + 1;
                else{
                    dp[si][ti]  = Math.max(dp[si - 1][ti],dp[si][ti - 1]);   
                }

            }
        }
        return dp[SI][TI];   
    }

}
