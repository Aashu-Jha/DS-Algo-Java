import java.util.Arrays;

public class last_class {
    public static void main(String[] args) {
        boolean ans = validPalindrome("abcdeca", 2);
        System.out.println(ans);
    }

    public static boolean validPalindrome(String s, int k) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int[] a: dp) Arrays.fill(a, -1);
        return (s.length() - palindromicSubs(s, 0, n - 1, dp)) <= k; 
    }

    public static int palindromicSubs(String str, int li, int ri, int[][] dp) {
        if(li >= ri) {
            return (li == ri) ? 1 : 0;
        }

        if(dp[li][ri] != -1) return dp[li][ri];

        if(str.charAt(li) == str.charAt(ri)) 
            return dp[li][ri] = palindromicSubs(str, li + 1, ri - 1, dp) + 2;
        
            return dp[li][ri] = Math.max(palindromicSubs(str, li + 1, ri, dp), palindromicSubs(str, li, ri - 1, dp));
        
    }
}
