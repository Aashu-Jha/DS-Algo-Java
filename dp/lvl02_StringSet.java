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

    //Leetcode 1458
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int N = nums1.length;
        int M = nums2.length;
        
        int[][] dp = new int[N + 1][M + 1];
        
        for(int n = 0; n <= N; n++) {
            for(int m = 0; m <= M; m++) {
                if(n == 0 || m == 0) {
                    dp[n][m] = -(int) 1e7;
                    continue;
                }
                int product = nums1[n - 1] * nums2[m - 1];
                int sumProduct = dp[n - 1][m - 1] + product;
                int a = dp[n - 1][m];
                int b= dp[n][m - 1];
                
                dp[n][m] = Math.max(Math.max(product, sumProduct), Math.max(a, b));
            }
        }
        return dp[N][M];
    }
    
    //Leetcode 72
    public int minDistance(String word1, String word2) {
        if(word1.length() == 0 && word2.length() == 0) return 0;
        int n = word1.length();
        int m = word2.length();
        
        int[][] dp = new int[n + 1][m + 1];
        for(int[] a: dp) Arrays.fill(a, -1);
        return minDistance_memo(word1, word2, n, m, dp);
    }
    
    public int minDistance_memo(String word1, String word2, int n, int m, int[][] dp) {
        if(n == 0 || m == 0) {
            if(n == 0 && m == 0) 
                return dp[n][m] = 0;
            return dp[n][m] = Math.abs(n - m);
        }
        
        if(dp[n][m] != -1) return dp[n][m];
        
        if(word1.charAt(n - 1) == word2.charAt(m - 1)) {
            return dp[n][m] = minDistance_memo(word1, word2, n - 1, m - 1, dp);
        }else{
            int insert = minDistance_memo(word1, word2, n , m - 1, dp);
            int delete = minDistance_memo(word1, word2, n - 1, m, dp);
            int replace = minDistance_memo(word1, word2, n - 1, m - 1, dp);
            
            return dp[n][m] = Math.min(insert, Math.min(delete, replace)) + 1;
        }
    }
    
    public int minDistance_DP(String word1, String word2, int N, int M, int[][] dp) {
        for(int n = 0; n <= N; n++) {
            for(int m = 0; m <= M; m++) {
                if(n == 0 || m == 0) {
                    if(n == 0 && m == 0) {
                        dp[n][m] = 0;
                        continue;
                    }
                    dp[n][m] = Math.abs(n - m);
                    continue;
                }

                if(word1.charAt(n - 1) == word2.charAt(m - 1)) {
                    dp[n][m] = dp[n - 1][m - 1]; // minDistance_memo(word1, word2, n - 1, m - 1, dp);
                }else{
                    int insert = dp[n][m - 1]; // minDistance_memo(word1, word2, n , m - 1, dp);
                    int delete = dp[n - 1][m]; // minDistance_memo(word1, word2, n - 1, m, dp);
                    int replace = dp[n - 1][m - 1]; // minDistance_memo(word1, word2, n - 1, m - 1, dp);

                    dp[n][m] = Math.min(insert, Math.min(delete, replace)) + 1;
                }
            }
        }
        return dp[N][M];
        
    }
    //[end]

    //Leetcode 44
    public boolean isMatch(String s, String p) {
        int n = s.length();
        p = shorten(p);
        int m = p.length();
        
        int[][] dp = new int[n + 1][m + 1];
        for(int[] a: dp) Arrays.fill(a, -1);
        return (isMatchMemo(s, p, n, m, dp) == 1);
        
    }
    
    public String shorten(String p) {
        if(p.length() == 0) return "";
        
        StringBuilder sb = new StringBuilder();
        sb.append(p.charAt(0));
        for(int i = 1; i< p.length(); i++) {
            char c = p.charAt(i);
            if(c == '*' && c == p.charAt(i - 1)) {
                
            }
            else
                sb.append(c);
        }
        return sb.toString();
    }
    
    public int isMatchMemo(String s, String p, int n, int m, int[][] dp) {
        if(n == 0 || m == 0) {
            if(n == 0 && m == 0) return dp[n][m] = 1;
            else if(m == 1 && p.charAt(m - 1) == '*') return dp[n][m] = 1;
            return dp[n][m] = 0;
        }
        
        if(dp[n][m] != -1) return dp[n][m];
        
        char c = p.charAt(m - 1);
        if(s.charAt(n - 1) == c || c == '?') return dp[n][m] = isMatchMemo(s, p, n - 1, m - 1, dp);
        else{
            if(c == '*'){
                boolean res = (isMatchMemo(s, p, n - 1, m,dp) == 1) || (isMatchMemo(s, p, n, m - 1,dp) == 1);
                return dp[n][m] = (res) ? 1 : 0;
            }
            else
                return dp[n][m] = 0;
        }
    }
    //[end]

    //[start] GFG countPalindromicSubsequence
    int M = 1000000007;
    long countPS(String str)
    {
        int n = str.length();
        long[][] dp = new long[n][n];
        for(long[] a: dp) Arrays.fill(a, -1);
        
        return memo(str, 0, n - 1, dp);
         
    }
    
    long memo(String s, int i, int j, long[][] dp) {
        if(i >= j) {
            return dp[i][j] = (i == j) ? 1 : 0;
        }
        
        if(dp[i][j] != -1) return dp[i][j];
        
        long x = memo(s, i + 1, j, dp);
        long y = memo(s, i, j - 1, dp);
        long z = memo(s, i + 1, j - 1, dp);
        
        return dp[i][j] = (((s.charAt(i) != s.charAt(j)) ? x + y - z + M : x + y + 1)) % M;
    }
    //[end]

    

}
