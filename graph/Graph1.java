import java.util.ArrayList;

public class Graph1 {
    public static class Edge {
        int v = 0;
        int w = 0;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    static int N = 7;

    @SuppressWarnings("unchecked")
    // public static ArrayList<Edge>[] graph = new ArrayList[N];
    static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
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

    public static int findEdge(int u, int v) {
        int idx = -1;
        for(int i = 0; i< graph[u].size(); i++) {
            Edge edge = graph[u].get(i);
            if(edge.v == v) {
                idx = i;
                break;
            }
        }
        return idx;
    }
    
    public static void removeEdge(int u, int v) {
        int idx1 = findEdge(u, v);
        int idx2 = findEdge(v, u);
        
        graph[u].remove(idx1);
        graph[v].remove(idx2);
    }

    public static void removeVtx(int u) {
        for(int i =graph[u].size() - 1; i >= 0; i--) {
            Edge edge = graph[u].get(i);
            removeEdge(u, edge.v);
        }
    }

    static boolean[] vis = new boolean[N];
    public static boolean hasPath(int src, int des) {
        if(src == des) {
            return true;
        }

        vis[src] = true;
        boolean res = false;
        for(Edge edge: graph[src]) {
            int rv = edge.v;
            if(!vis[rv]) {
                res = res || hasPath(rv, des);
            }
        }
        return res;
    }

    public static int printAllPaths(int src, int des, String ans) {
        if(src == des) {
            System.out.println(ans + src);
            return 1;
        }

        vis[src] = true;
        int count = 0;
        for(Edge edge: graph[src]) {
            int rv = edge.v;
            if(!vis[rv]) {
                count += printAllPaths(rv, des, ans + src + ", ");
                vis[rv] = false;
            }
        }
        return count;
    }

    public static int heavyPath(int src , int des, int currHeavy, String ans) {
        if(src == des) {
            
            return currHeavy;
        }

        vis[src] = true;
        int count = -(int) 1e8;
        for(Edge edge: graph[src]) {
            int rv = edge.v;
            int rw = edge.w;
            if(!vis[rv]) {
                count = Math.max(count ,heavyPath(rv, des, currHeavy + rw, ans + src + ", "));
                vis[rv] = false;
            }
        }
        return count;
    }

    public static void constructGraph() {
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();

        addEdge(0, 1, 10);
        addEdge(0, 3, 10);
        addEdge(1, 2, 10);
        addEdge(2, 3, 40);
        addEdge(3, 4, 2);
        addEdge(4, 5, 2);
        addEdge(4, 6, 8);
        addEdge(5, 6, 3);
    }

    public static void main(String[] args) {
        constructGraph();
        // System.out.println(printAllPaths(0, 6, ""));
        // System.out.println(heavyPath(0, 6, 0, ""));
        System.out.println(hasPath(0, 6));
    }

    

}
