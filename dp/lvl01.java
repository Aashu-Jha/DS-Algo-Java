import java.util.LinkedList;

public class lvl01 {
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        // mazePath();
        diceCount();
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
