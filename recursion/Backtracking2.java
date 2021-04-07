import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Backtracking2 extends Backtracking1 {

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

        // queenCombination();
        // solveSudoku();

    }

    // QueenCombination
    public static void queenCombination() {
        // nQueens2D(new boolean[4][4],4, 0, "");
        // nQueens2DOP(4, 0, "");
        // nQueens04(0, 4, "");
        // Nqueen_04_Bits(4, 0, 4, "");
        // System.out.println(queenCombination1D(5, 0, 3, 0, ""));
        // System.out.println(queenPermutation1D(new boolean[5], 0, 3, 0, ""));
    }


    static int[] rowsN = new int[9];
    static int[] colsN = new int[9];
    static int[][] matsN = new int[3][3];

    public static void solveSudoku(char[][] board) {
        int[] loc = new int[81];

        
        int idx = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    loc[idx++] = i * 9 + j;
                } else {
                    int mask = board[i][j] - '0';
                    toggleSudoku(i, j, mask);
                }
            }
        }

        sudokuSolve_03(board,loc, 0, idx);
    }

    // leetcode 44[start]
    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);

        List<Integer> res = new ArrayList<>();
        return permute(nums, 0, res, new boolean[nums.length]);
    }

    // faith: ( celebrity approach)
    public static List<List<Integer>> permute(int[] nums, int idx, List<Integer> res, boolean[] vis) {
        if (idx == nums.length) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(new ArrayList<>(res));
            return ans;
        }

        List<List<Integer>> lis = new ArrayList<>();

        int prev = -11; // use prev as a duplicate checker in same level (Recursion Tree)
        for (int i = 0; i < nums.length; i++) {
            if (prev != -11 && prev == nums[i])
                continue;
            if (!vis[i]) {
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
    // [leetcode 44] end

    // [Leetcode 39] start
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<Integer> lis = new ArrayList<>();
        return combination(candidates, target, 0, lis);
    }

    // faith: ( celebrity approach )call every element in loop until tar != 0 and
    // also not use backward elements of loop
    // in next call.
    public static List<List<Integer>> combination(int[] coins, int tar, int idx, List<Integer> lis) {
        if (tar == 0) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(new ArrayList<>(lis));
            return ans;
        }

        List<List<Integer>> res = new ArrayList<>();

        for (int i = idx; i < coins.length; i++) {
            if (tar - coins[i] >= 0) {
                lis.add(coins[i]);
                res.addAll(combination(coins, tar - coins[i], i, lis));
                lis.remove(lis.size() - 1);
            }
        }

        return res;
    }

    // faith: i'm IN or i'm OUT (Subsequence method)
    // we will select one coin and call that index until target != 0
    // if we won't select the coin, then just pass next index
    public static List<List<Integer>> combinationSubs(int[] coins, int tar, int idx, List<Integer> lis) {
        if (tar == 0 || idx == coins.length) {
            List<List<Integer>> ans = new ArrayList<>();
            if (tar == 0) {
                ans.add(new ArrayList<>(lis));
                return ans;
            }
            return ans;
        }

        List<List<Integer>> res = new ArrayList<>();

        int num = coins[idx];
        if (tar - num >= 0) {
            lis.add(num);
            res.addAll(combination(coins, tar - num, idx, lis));
            lis.remove(lis.size() - 1);
        }
        res.addAll(combination(coins, tar, idx + 1, lis));

        return res;
    }
    // [Leetcode 39] end

    // [Leetcode 216] start
    public static List<List<Integer>> combinationSum3(int k, int n) {

        List<Integer> lis = new ArrayList<>();
        return combinationSum3C(n, 1, k, lis);
    }

    // faith: (celebrity approach) call every element in loop until tar != 0 and
    // also not use backward elements of loop
    // in next call.
    public static List<List<Integer>> combinationSum3C(int tar, int idx, int k, List<Integer> lis) {
        if (tar == 0 || k == 0) {
            List<List<Integer>> ans = new ArrayList<>();
            if (tar == 0 && k == 0) {
                ans.add(new ArrayList<>(lis));
            }
            return ans;
        }

        List<List<Integer>> res = new ArrayList<>();

        for (int i = idx; i <= 9; i++) {
            if (tar - i >= 0 && k > 0) {
                lis.add(i);
                res.addAll(combinationSum3C(tar - i, i + 1, k - 1, lis));
                lis.remove(lis.size() - 1);
            }
        }

        return res;
    }
    // [Leetcode 216] end

    // QueenProblem [start] ===========================================

    // faith: (i'm in, i'm out) combinationsubs
    // tnb = total no of boxes , busf = boxes used so far, qpsf = queens placed so
    // far
    public static int queenCombination1D(int tnb, int busf, int tnq, int qpsf, String asf) {
        if (qpsf == tnq || busf == tnb) {
            if (qpsf == tnq) {
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
        if (qpsf == tnq || busf == tnb.length) {
            if (qpsf == tnq) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        int count = 0;

        if (!tnb[busf]) {
            tnb[busf] = true;
            count += queenPermutation1D(tnb, 0, tnq, qpsf + 1, asf + "b" + busf + "q" + qpsf + "\n");
            tnb[busf] = false;
        }
        count += queenPermutation1D(tnb, busf + 1, tnq, qpsf, asf);

        return count;
    }

    // faith: (celebrity approach) Combination problem
    // tnq = total no of queens
    public static void nQueens2D(boolean[][] vis, int tnq, int idx, String asf) {
        if (tnq <= 0) {
            System.out.println(asf);
            return;
        }

        for (int i = idx; i < 16; i++) {
            int r = i / 4;
            int c = i % 4;

            if (r >= 0 && c >= 0 && r < 4 && c < 4 && isQueenSafe(vis, r, c)) {
                vis[r][c] = true;
                nQueens2D(vis, tnq - 1, i + 1, asf + "(" + r + "," + c + ")\n");
                vis[r][c] = false;
            }
        }
    }

    public static boolean isQueenSafe(boolean[][] vis, int row, int col) {
        int[][] dirs = { { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

        for (int d = 0; d < dirs.length; d++) {
            for (int i = 0; i < 4; i++) {
                int r = row + dirs[d][0] * i;
                int c = col + dirs[d][1] * i;

                if (r >= 0 && c >= 0 && r < 4 && c < 4) {
                    if (vis[r][c])
                        return false;
                } else
                    break;
            }
        }
        return true;
    }

    static boolean[] rows = new boolean[4];
    static boolean[] cols = new boolean[4];
    static boolean[] diag = new boolean[7];
    static boolean[] aDiag = new boolean[7];

    public static void nQueens2DOP(int tnq, int idx, String asf) {
        if (tnq <= 0) {
            System.out.println(asf);
            return;
        }

        for (int i = idx; i < 16; i++) {
            int r = i / 4;
            int c = i % 4;

            int n = 4;
            if (!rows[r] && !cols[c] && !diag[r - c + n - 1] && !aDiag[r + c]) {
                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r + c] = !aDiag[r + c];
                diag[r - c + n - 1] = !diag[r - c + n - 1];

                nQueens2DOP(tnq - 1, i + 1, asf + "(" + r + "," + c + ")\n");

                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r + c] = !aDiag[r + c];
                diag[r - c + n - 1] = !diag[r - c + n - 1];
            }
        }
    }

    // In this, we used row to increament everytime, we mark one value in row
    public static int nQueens04(int row, int tnq, String asf) {
        // if(tnq <= 0 || row == 4){
        if (tnq <= 0) {
            System.out.println(asf);
            return 1;
        }
        // return 0;
        // }

        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row;
            int c = i;
            int n = 4;
            if (!rows[r] && !cols[c] && !diag[r - c + n - 1] && !aDiag[r + c]) {
                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r + c] = !aDiag[r + c];
                diag[r - c + n - 1] = !diag[r - c + n - 1];

                nQueens04(r + 1, tnq - 1, asf + "(" + r + "," + c + ")\n");

                rows[r] = !rows[r];
                cols[c] = !cols[c];
                aDiag[r + c] = !aDiag[r + c];
                diag[r - c + n - 1] = !diag[r - c + n - 1];
            }
        }
        return count;
    }

    // we used bits here- an int has 32 bits, which we use as array for toggling the
    // values inside int.
    static int colN = 0, diagN = 0, adiagN = 0;

    public static int Nqueen_04_Bits(int n, int r, int tnq, String ans) {
        if (tnq == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int c = 0; c < n; c++) {
            if ((colN & (1 << c)) == 0 && (diagN & (1 << (r - c + n - 1))) == 0 && (adiagN & (1 << (r + c))) == 0) {
                toggle(r, c, n);
                count += Nqueen_04_Bits(n, r + 1, tnq - 1, ans + "(" + r + "," + c + ")\n");
                toggle(r, c, n);

            }
        }

        return count;
    }

    public static void toggle(int r, int c, int n) {
        // for queen
        colN ^= (1 << c);
        diagN ^= (1 << (r - c + n - 1));
        adiagN ^= (1 << (r + c));
    }

    // [nQueens] end============================================

    // leetcode [sudoku solver]

    public static boolean sudokuSolve_01(char[][] board, int idx) {
        if (idx == 81)
            return true;

        int r = idx / 9;
        int c = idx % 9;

        if (board[r][c] != '.')
            return sudokuSolve_01(board, idx + 1);

        for (int i = 1; i < 10; i++) {
            if (isSafetoPlaceNum(board, r, c, i)) {
                board[r][c] = (char) (i + '0');
                if (sudokuSolve_01(board, idx + 1))
                    return true;
                board[r][c] = '.';
            }
        }
        return false;
    }

    public static boolean isSafetoPlaceNum(char[][] board, int r, int c, int num) {
        // row
        for (int i = 0; i < 9; i++) {
            if(board[i][c] - '0' == num) return false;
        }

        // col
        for (int i = 0; i < 9; i++) {
            if(board[r][i] - '0' == num) return false;
        }

        // min-arr
        r = (r / 3) * 3;
        c = (c / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[r + i][c + j] - '0' == num) return false;
            }
        }
        return true;
    }

    //optimised of sudokuSolve_01
    /* public static boolean sudokuSolve_02(int[] loc, int idx, int n) {
        if (idx == n) {
            return true;
        }
        int r = loc[idx] / 9;
        int c = loc[idx] % 9;

        for (int i = 1; i < 10; i++) {
            if (isSafetoPlaceNum(r, c, i)) {
                board[r][c] = i;
                if (sudokuSolve_02(loc, idx + 1, n))
                    return true;
                board[r][c] = 0;
            }
        }
        return false;
    } */

    //leetcode -37 
    //faith - (step-up idx)  
    public static boolean sudokuSolve_03(char[][] board, int[] loc, int idx, int n) {
        if(idx == n) {
            return true;
        }

        int num = loc[idx];
        int r = num / 9;
        int c = num % 9;

        for(int i = 1; i<= 9; i++) {
            int mask  = (1 << i);
            if((rowsN[r] & mask) == 0 && (colsN[c] & mask) == 0 && (matsN[r/3][c/3] & mask) == 0) {
                board[r][c] = (char) (i + '0');
                toggleSudoku(r, c, i);
                if(sudokuSolve_03(board, loc, idx + 1, n)) return true;
                toggleSudoku(r, c, i);
                board[r][c] = '.';
                
            }
        }
        return false;
    }

    public static void toggleSudoku(int r, int c, int n) {
        // for queen
        int mask = (1 << n);
        rowsN[r] ^=  mask;
        colsN[c] ^= mask;
        matsN[r / 3][c / 3] ^= mask;
    }
}
