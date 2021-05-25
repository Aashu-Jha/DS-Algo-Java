import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;

public class questions {

    //Leetcode 994
    LinkedList<Integer> list;
    public int orangesRotting(int[][] grid) {
        int[][] dirs = new int[][]{{0,-1},{-1,0},{0,1},{1,0}};
        list = new LinkedList<Integer>();
        int n = grid.length;
        int m = grid[0].length;
        
        
        int rottenOranges = fillTwos(grid);
        
        if(rottenOranges == 0) return 0;
        
        int lvl = 0;
        while(!list.isEmpty()) {
             int size = list.size();
            while(size--> 0) {
                int rn = list.removeFirst();
                
                int r = rn / m;
                int c = rn % m;
                
                for(int d = 0; d < dirs.length; d++) {
                    int nr = r + dirs[d][0];
                    int nc = c + dirs[d][1];
                    
                    if(nr >= 0 && nc >= 0 && nr < n && nc < m && grid[nr][nc] == 1) {
                        rottenOranges--;
                        grid[nr][nc] = 2;
                        list.addLast(nr * m + nc);
                        
                        if(rottenOranges == 0) return lvl + 1;
                    }
                }
            }
            lvl++;
        }
        
        return -1;
    }
    
    public int fillTwos(int[][] grid) {
        int m = grid[0].length;
        int rottenOranges = 0;
        for(int i = 0; i< grid.length; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 2) list.addLast(i * m + j);
                else if(grid[i][j] == 1) rottenOranges++;
            }
        }
        return rottenOranges;
    }
    //[end]

    //Lintcode 286 Walls And Gates
    public void wallsAndGates(int[][] grid) {
        int[][] dirs = new int[][]{{0,-1},{-1,0},{0,1},{1,0}};
        list = new LinkedList<Integer>();

        int n = grid.length;
        int m = grid[0].length;

        int nofINF = fillTwos(grid);

        if(nofINF == 0) return;

        int lvl = 0;
        while(!list.isEmpty()) {
            int size = list.size();
            while(size--> 0) {
                int rn = list.removeFirst();
                
                int r = rn / m;
                int c = rn % m;
                
                for(int d = 0; d < dirs.length; d++) {
                    int nr = r + dirs[d][0];
                    int nc = c + dirs[d][1];
                    
                    if(nr >= 0 && nc >= 0 && nr < n && nc < m && grid[nr][nc] == Integer.MAX_VALUE) {
                        nofINF--;
                        grid[nr][nc] = lvl + 1;
                        list.addLast(nr * m + nc);
                        
                        if(nofINF == 0) return;
                    }
                }
            }
            lvl++;
        }
    }
    //[end]

    //Leetcode 207 
    public boolean canFinish(int N, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[N]; //create graph
        
        for(int i = 0; i < N; i++ ) { // initialize graph indexes
            graph[i] = new ArrayList<>();
        }
        
        for(int[] arr: prerequisites) { // fill graph
            graph[arr[0]].add(arr[1]);
        }
        
        int[] indegree = new int[N];
        Queue<Integer> que = new LinkedList<>();

        for(int i = 0; i< N; i++) { // fill Indegree
            for(int e: graph[i]) indegree[e]++;
        }
        
        for(int i = 0; i< N; i++) { // fill queue
            if(indegree[i] == 0) que.add(i);
        }

        return kahns(indegree, que, graph,N);
    }
    

    public static boolean kahns(int[] indegree, Queue<Integer> que, ArrayList<Integer>[] graph, int N) {
        int lvl = 0;
        int count = 0;
        
        while(!que.isEmpty()) {
            int size = que.size();
            while(size--> 0) {
                int rn = que.poll();
                
                count++;
                for(int i : graph[rn]) {
                    if(--indegree[i] == 0) que.add(i);
                }
                
            }
            lvl++;
        }

        return count == N;
    }
    //[end sub-part]
     
    //new method
    public static boolean isCyclePresent_DFSTopo(int src, int[] vis) {
        vis[src] = 0;
        boolean res = false;
        for(Edge e: graph[src]) {
            if(vis[e.v] == -1) {
                if(isCyclePresent_DFSTopo(e.v, vis)) return false;
            }else if(vis[e.v] == 0) 
                return true;
        }
        vis[src] = 1;
        return true;
    }
    //[end]

    //Leetcode 329
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length; 
        int n = matrix[0].length;
        
        int[][] dir = {{0,-1},{-1,0},{0,1},{1,0}};
        int[][] indegree = createIndegree(matrix, m, n, dir);
        
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 0; i< m; i++) {
            for(int j = 0; j < n; j++) {
                if(indegree[i][j] == 0) list.addLast(i * n + j);
            }
        }
        
        int lvl = 0;
        while(!list.isEmpty()) {
            int size = list.size();
            while(size--> 0) {
                int rv = list.removeFirst();
                
                int r = rv / n;
                int c = rv % n;
                
                for(int d = 0; d < 4; d++) {
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];
                    
                    if(x >= 0 && y >= 0 && x < m && y < n && matrix[x][y] > matrix[r][c]) {
                        if(--indegree[x][y] == 0) list.addLast(x * n + y);
                    }
                }
            }
            lvl++;
        }
        return lvl;
    }
    
    
    public int[][] createIndegree(int[][] matrix, int m, int n, int[][] dir) {
        int[][] indegree= new int[m][n];
        
        for(int i = 0; i< m; i++) {
            for(int j = 0; j< n; j++) {
                for(int d = 0; d < 4; d++) {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    
                    if(r >= 0 && c >= 0 && r < m && c < n) {
                        if(matrix[r][c] > matrix[i][j]) {
                            indegree[r][c] += 1;
                        }
                    }
                }
            }
        }
        return indegree;
    }
    //[end]

    //Leetcode- 1168
    public static int minCostSupplyToWater(int n, int[] wells, int[][] pipes) {
        ArrayList<int[]> PIPES = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            PIPES.add(new int[]{0, i+1, wells[i]});
        }

        for(int[] p: pipes) PIPES.add(p);

        Collections.sort(PIPES, (a,b) -> { return a[2] - b[2]; } );

        return unionFind(n, PIPES);
    }

    int[] par;
    public int findPar(int u) {
        return par[u] == u ? u : (par[u] = findPar(par[u]));
    }

    public int unionFind(int N, ArrayList<int[]> Edges) {
        par = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            par[i] = i;
        }
        int cost = 0;
        for (int[] edge : Edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            int p1 = findPar(u);
            int p2 = findPar(v);

            if (p1 != p2) {
                par[p1] = p2;
                cost += w;
            } 
        }

        return cost;
    }
    //[end]

    //Leetcode 200
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        par = new int[m * n];
        for(int i = 0; i< m * n; i++) par[i] = i;
        
        int count = 0;
        for(int i = 0; i< m; i++){
            for(int j = 0; j< n; j++) {
                if(grid[i][j] == '1') {
                    count++;
                    int p1 = findPar(i * n + j);
                    
                    if(j + 1 < n && grid[i][j + 1] == '1') {
                        int p2 = findPar(i * n + (j + 1));
                        if(p1 != p2) {
                            par[p2] = p1;
                            count--;
                        }
                    }
                    
                    if(i + 1 < m && grid[i + 1][j] == '1') {
                        int p2 = findPar((i + 1) * n + j);
                        if(p1 != p2) {
                            par[p2] = p1;
                            count--;
                        }
                    }
                }
            }
        }
        
        return count;
    }
    //[end]
}
 