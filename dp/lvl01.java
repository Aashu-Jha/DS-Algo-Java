import java.util.Arrays;
import java.util.LinkedList;

public class lvl01 {
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        // mazePath();
        // diceCount();
        // minCostClimbingStairs();
        printFriendsPairings();
        // maxCostGoldMine();
    }

    public static void diceCount() {
        int[] dp = new int[11];
        // diceCountDP(10, dp);
        System.out.println(diceCountDPOpt(10));
        // print1D(dp);
    }

    public static void mazePath() {
        int n = 5;
        int m = 5;


        System.out.println(mazePathJumpMemo(0, 0, n - 1, m - 1, new int[n][m]));
        System.out.println(mazePathJumpDP(0, 0, n - 1, m - 1, new int[n][m]));
    }

    public static int mazePathMemo(int sr, int sc, int dr, int dc, int[][] dp) {
        if(sr == dr && sc == dc)
            return dp[sr][sc] = 1;

        if(dp[sr][sc] != 0) return dp[sr][sc];

        int count = 0;

        if(sc + 1 <= dc)
            count += mazePathMemo(sr, sc + 1, dr, dc, dp);
        
        if(sr + 1 <= dr)
            count += mazePathMemo(sr + 1, sc , dr, dc, dp);
        
        if(sr + 1 <= dr && sc + 1 <= dc)
            count += mazePathMemo(sr + 1, sc + 1, dr, dc, dp);
            
        return dp[sr][sc] = count;
    }

    public static int mazePathDP(int sr, int sc, int dr, int dc, int[][] dp) {
        for(sr = dr ; sr >= 0; sr--) {
            for(sc = dc; sc >= 0; sc--) {
                if(sr == dr && sc == dc) {
                    dp[sr][sc] = 1;
                    continue;
                }
            int count = 0;

            if(sc + 1 <= dc)
                count += dp[sr][sc + 1]; //mazePathMemo(sr, sc + 1, dr, dc, dp);
            
            if(sr + 1 <= dr)
                count += dp[sr + 1][sc]; //mazePathMemo(sr + 1, sc , dr, dc, dp);
            
            if(sr + 1 <= dr && sc + 1 <= dc)
                count += dp[sr + 1][sc + 1]; //mazePathMemo(sr + 1, sc + 1, dr, dc, dp);
                
            dp[sr][sc] = count;
            }
        }
        return dp[0][0];
    }

    public static int mazePathJumpMemo(int sr, int sc, int dr, int dc, int[][] dp) {
        if(sr == dr && sc == dc)
            return dp[sr][sc] = 1;

        if(dp[sr][sc] != 0) return dp[sr][sc];

        int count = 0;
        int jump = 0;

        for(jump = 1; sc + jump <= dc; jump++)
            count += mazePathJumpMemo(sr, sc + jump, dr, dc, dp);
        
        for(jump = 1; sr + jump <= dr; jump++)
            count += mazePathJumpMemo(sr + jump, sc , dr, dc, dp);
        
        for(jump = 1; sc + jump <= dc && sr + jump <= dr; jump++)
            count += mazePathJumpMemo(sr + jump, sc + jump, dr, dc, dp);
            
        return dp[sr][sc] = count;
    }

    public static int mazePathJumpDP(int SR, int SC, int dr, int dc, int[][] dp) {
        for(int sr = dr; sr >= SR; sr--) {
            for(int sc = dc ; sc >= SC; sc--) {
                if(sr == dr && sc == dc){
                    dp[sr][sc] = 1;
                    continue;
                }

            // if(dp[sr][sc] != 0) return dp[sr][sc];

            int count = 0;
            int jump = 0;

            for(jump = 1; sc + jump <= dc; jump++)
                count += dp[sr][sc + jump]; //mazePathJumpMemo(sr, sc + jump, dr, dc, dp);
            
            for(jump = 1; sr + jump <= dr; jump++)
                count += dp[sr + jump][sc]; //mazePathJumpMemo(sr + jump, sc , dr, dc, dp);
            
            for(jump = 1; sc + jump <= dc && sr + jump <= dr; jump++)
                count += dp[sr + jump][sc + jump]; //mazePathJumpMemo(sr + jump, sc + jump, dr, dc, dp);
                
            dp[sr][sc] = count;
            }
        }
        return dp[SR][SC];
    }

    public static int diceCountMemo(int tar, int[] dp) {
        if(tar == 0) {
            return dp[tar] = 1;
        }

        if(dp[tar] != 0) return dp[tar];

        int count = 0;
        for(int step = 1; step <= 6 && tar - step >= 0; step++) {
            // if(tar - step >= 0) {
                count += diceCountMemo(tar - step, dp);
            // }
        }
        return dp[tar] = count;
    }

    public static int diceCountDP(int Tar, int[] dp) {
        for(int tar = 0; tar <= Tar; tar++) {
            if(tar == 0) {
                dp[tar] = 1;
                continue;
            }
    
            // if(dp[tar] != 0) return dp[tar];
    
            int count = 0;
            for(int step = 1; step <= 6; step++) {
                if(tar - step >= 0) {
                    count += dp[tar - step]; // diceCountMemo(tar - step, dp);
                }
            }
            dp[tar] = count;
        }
        return dp[Tar];
    }

    public static int diceCountDPOpt(int Tar) {
        LinkedList<Integer> dp = new LinkedList<>();
        for(int tar = 0; tar <= Tar; tar++) {
            if(tar <= 1) {
                dp.add(1);
                continue;
            }
            
           if(tar <= 6) {
               dp.addLast(dp.getLast() * 2);
           }else{
               dp.addLast(dp.getLast() * 2 - dp.removeFirst());
           }
        }
        return dp.getLast();
    }

    public static int targetSum(int tar, int[] arr, int[] dp) {
        if(tar == 0) return dp[tar] = 1;

        if(dp[tar] != 0) return dp[tar];

        int count = 0;
        for(int i = 0; i< arr.length; i++) {
            int num = arr[i];
            if(tar - num >= 0) {
                count += targetSum(tar - num, arr, dp);
            }
        }

        return dp[tar] = count;
    }

    public static void minCostClimbingStairs() {
        int[] dp = new int[8];
        int[] cost = new int[]{1, 3, 2, 21, 32, 11, 22, 13};
        int minVal = Math.min(minCostClimbingStairsMemo(cost, 0, dp), minCostClimbingStairsMemo(cost, 1, dp));
        print1D(dp);
        System.out.println(minVal);
    }

    public static int minCostClimbingStairsMemo(int[] cost, int i, int[] dp) {
        if(i >= cost.length - 2) return dp[i] = cost[i];

        if(dp[i] != 0) return dp[i];

        return dp[i] = cost[i] + Math.min(minCostClimbingStairsMemo(cost, i + 1, dp), minCostClimbingStairsMemo(cost, i + 2, dp));
    }

    public int minCostClimbingStairsDPOpt(int[] cost) {
        int n = cost.length;
        int a = cost[n - 1];
        int b = cost[n - 2];
        
        for(int i = cost.length - 3; i >= 0; i--) {
            int minAll = cost[i] + Math.min(a, b);
            a = b;
            b = minAll;
        }
        
        return Math.min(a,b);
         
    }

    public static long countFriendsPairings(int n) 
    { 
        long[] dp = new long[n + 1];
        return countFriendsPairingsMemo(n, dp);
    }

    static long mod = (int) 1e9 + 7;
    public static long countFriendsPairingsMemo(int n, long[] dp){
        if(n <= 1) {
            return dp[n] = 1;
        }

        if(dp[n] != 0) return dp[n];

        long single = countFriendsPairingsMemo(n - 1, dp);
        long doublee = countFriendsPairingsMemo(n - 2, dp) * ( n - 1);

        return dp[n] = (single % mod + doublee % mod) % mod;
    }

    public static void printFriendsPairings() {
        System.out.println(printFriendsPairings("heel", ""));
    }

    public static long printFriendsPairings(String friends, String ans) {
        if(friends.length() <= 0) {
            System.out.println(ans);
            return 1;
        }

        long count = 0;
        char c = friends.charAt(0);
        String ros = friends.substring(1);
        count += printFriendsPairings(ros, ans + c + " ");
        for(int i = 1; i< friends.length(); i++) {
            count += printFriendsPairings(friends.substring(1, i) + friends.substring(i + 1), ans + c + "," + friends.charAt(i) + " ");
        }
        return count;
    }
    
    public static int maxCostGoldMineMemo(int r , int c, int[][] mine, int[][] dp, int[][] dir) {
        if(c == mine[0].length - 1) {
            return dp[r][c] = mine[r][c];
        }

        if(dp[r][c] != -1) return dp[r][c];

        int count = 0;
        for(int d = 0; d < 3; d++) {
            int x = r + dir[d][0];
            int y = c + dir[d][1];
            if(x >= 0 && x < mine.length && y < mine[0].length)
                count = Math.max(count, maxCostGoldMineMemo(x, y, mine, dp, dir));
        }

        return dp[r][c] = count + mine[r][c];
    }

    public static int maxCostGoldMineDP(int R , int C, int[][] mine, int[][] dp, int[][] dir) {
        for(int c = mine[0].length - 1; c >= C; c--) {
            for(int r = mine.length - 1; r >= R; r--) {
                if(c == mine[0].length - 1) {
                    dp[r][c] = mine[r][c];
                    continue;
                }
                // if(dp[r][c] != -1) return dp[r][c];
        
                int count = 0;
                for(int d = 0; d < 3; d++) {
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];
                    if(x >= 0 && x < mine.length && y < mine[0].length)
                        count = Math.max(count, dp[x][y]); //maxCostGoldMineMemo(x, y, mine, dp, dir)
                }
        
                dp[r][c] = count + mine[r][c];
            }
        }

        int maxx = 0;
        for(int i = 0; i< mine.length; i++) {
            maxx = Math.max(maxx, dp[i][0]);
        }
        return dp[R][C] = maxx;
    }

    public static void maxCostGoldMine() {
        int[][] mine = new int[][] {{10,1,5,3},{14,3,7,9},{12,7,2,3},{1,16,4,1}};
        int[][] dp = new int[4][4];
        for(int[] ar: dp) Arrays.fill(ar, -1);
        int[][] dir = new int[][] {{1,1},{0,1},{-1,1}};


        System.out.println(maxCostGoldMineDP(0, 0, mine, dp, dir));
        // int maxx = 0;
        // for(int i = 0; i< mine.length; i++) {
        //     maxx = Math.max(maxx, maxCostGoldMineMemo(i,0,mine,dp,dir));
        // }
        // System.out.println(maxx);
    }

    public static long countFriendsPairingsDP(int N, long[] dp ) {
        for(int n = 0; n <= N ; n++) {
            if(n <= 1) {
                dp[n] = 1;
                continue;
            }

        // if(dp[n] != 0) return dp[n];

        long single = dp[n - 1]; // countFriendsPairingsMemo(n - 1, dp);
        long doublee = dp[n - 2] * (n - 1); // countFriendsPairingsMemo(n - 2, dp) * ( n - 1);

        dp[n] = (single % mod + doublee % mod) % mod;
        }
        return dp[N];
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
}
