import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class GraphDirected {
    
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        constructGraph();

        // topologicalOrder_DFS();
        kahnsAlgo();
    }

    //Topological Sort
    public static void dfs_topo(int src,boolean[] vis, ArrayList<Integer> que){
        vis[src] = true;
        for(Edge edge: graph[src]) {
            if(!vis[edge.v]) {
                dfs_topo(edge.v, vis, que);
            }
        }
        que.add(src);
    }

    public static void topologicalOrder_DFS()
    {
        boolean[] vis = new boolean[N];
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < N; i++)
        {
            if (!vis[i])
                dfs_topo(i, vis, ans);
        }

        System.out.println(ans);
    }


    public static void kahnsAlgo() {
        int[] indegree = new int[N];
        Queue<Integer> que = new LinkedList<>();

        //fill indegree
        for(int i = 0; i< N; i++) {
            for(Edge e: graph[i]) indegree[e.v]++;
        }

        //fill que : only indegree[i] == 0
        for(int i = 0; i< N; i++) {
            int val = indegree[i];
            if(val == 0) que.add(i);
        }

        System.out.println(kahns(indegree, que));
    }
    

    public static boolean kahns(int[] indegree, Queue<Integer> que) {
        ArrayList<Integer> ans = new ArrayList<>();
        int lvl = 0;

        while(!que.isEmpty()) {
            int size = que.size();
            while(size--> 0) {
                int rn = que.poll();
                
                ans.add(rn);
                for(Edge e: graph[rn]) {
                    if(--indegree[e.v] == 0)
                        que.add(e.v);
                }
            }
            lvl++;
        }

        return ans.size() == N;
    }

    public static class Edge {
        int v = 0;
        int w = 0;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    static int N = 11;

    @SuppressWarnings("unchecked")
    // public static ArrayList<Edge>[] graph = new ArrayList[N];
    static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
    }

    public static void display() {
        for(int i = 0; i< graph.length; i++) {
            if(graph[i].isEmpty()) continue;
            System.out.print(i + "-> ");
            for(Edge edge : graph[i]) {
                System.out.print(edge.v + "/" + edge.w + ", ");
            }
            System.out.println();
        }
    }

    public static void constructGraph() {
        for(int i = 0; i< N; i++) 
            graph[i] = new ArrayList<>();

        addEdge(5, 0, 10);
        addEdge(4, 0, 10);
        addEdge(5, 1, 10);
        addEdge(1, 2, 10);
        addEdge(2, 3, 10);
        addEdge(0, 6, 10);
        addEdge(6, 7, 10);
        addEdge(7, 3, 10);
        addEdge(4, 8, 10);
        addEdge(8, 9, 10);
        addEdge(9, 10, 10);
        addEdge(10, 3, 10);
    }


}
