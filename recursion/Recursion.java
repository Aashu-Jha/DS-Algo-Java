import java.util.Scanner;

class Recursion {
    public static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) {
        solution();
    }

    public static void display(int[] arr ) {
        for(int n: arr) 
            System.out.print(n + " ");
    }

    public static void display_2D(int[][] arr ) {
        for(int i = 0; i< arr.length; i++){
            for(int j = 0; j< arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
        }      
    }

    public static void solution() {
        int n = scn.nextInt();
        int m = scn.nextInt();
        int[][] nums = new int[n][m];

        for(int i = 0; i< n; i++){
            for(int j = 0; j< m; j++){
                nums[i][j] = scn.nextInt();
            }
        }
        int d1 = scn.nextInt();
        int d2 = scn.nextInt();

        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1, 0}};
        int ans = hurdlePaths(nums, 0, 0, 0, dirs, d1, d2);
        System.out.println(ans);
        
    }

    //faith: call in every direction until we reach the destination respecting the boundaries.
    public static int getMazePaths(int sr, int sc, int dr, int dc, String psf,int[][] dirs, boolean[][] vis) {
        if(sr == dr && sc == dc) {
            System.out.println(psf);
            return 1;
        }
        int count = 0;
        
        for(int d = 0; d < dirs.length; d++) {
            int r = sr + dirs[d][0];
            int c = sc + dirs[d][1];
            if(r >= 0 && c >= 0 && r <= dr && c <= dc && !vis[sr][sc]){
                vis[sr][sc] = true;
                count += getMazePaths(r, c, dr, dc, psf + r +""+ c + " ",dirs, vis);
                vis[sr][sc] = false;
            }
                
        }

        return count;
    }

    //faith: call in every direction and mark true until we reach the middle
    public static void getMiddleCell(int[][] nums, int sr, int sc , int mid,String psf) {
        if(sr == mid && sc == mid ) {
            System.out.println(psf + "mid");
            return;
        }

        if(nums[sr][sc] == 0) return;

        int max_jump = nums[sr][sc];
        nums[sr][sc] = 0;

        if(sc + max_jump < nums.length) {
            getMiddleCell(nums,  sr, sc + max_jump, mid,psf + "(" + sr + ", " + sc + ") -> ");
        }
        if(sr + max_jump < nums.length ) {
            getMiddleCell(nums,  sr + max_jump, sc, mid, psf + "(" + sr + ", " + sc + ") -> ");
        }
        if(sc - max_jump >= 0) {
            getMiddleCell(nums,  sr, sc - max_jump, mid,psf + "(" + sr + ", " + sc + ") -> ");
        }
        if(sr - max_jump >= 0) {
            getMiddleCell(nums,  sr - max_jump, sc, mid, psf + "(" + sr + ", " + sc + ") -> ");
        }
        
        nums[sr][sc] = max_jump;

    }
       
    //faith: just perform dfs but we need to take max of the output from the calls
    public static int hurdlePaths(int[][] nums, int sr, int sc, int count, int[][] dirs, int d, int e) {
        if(sr == d && sc == e ) {
            return Math.max(count, 0);
        }

        int coun = 0;
        int num = nums[sr][sc];

        nums[sr][sc] = -1;
        for(int i = 0; i< dirs.length; i++) {
            int r = sr + dirs[i][0];
            int c = sc + dirs[i][1];

            if(r >= 0 && c >= 0 && r < nums.length && c < nums[0].length && nums[r][c] == 1) {
                coun = Math.max(coun, hurdlePaths(nums, r, c, count + 1, dirs, d, e));
            }
        }

        nums[sr][sc] = num;
        return coun;
 
    }
    

}


