import java.util.LinkedList;
import java.util.List;

public class QuestionGT {
    public static class Node{
        int data = 0;
        ArrayList<Node> childs;

        Node(int data){
            this.data = data;
            childs = new ArrayList<Node>();
        }
    }

    public static Node constructTree(int[] arr){
        Stack<Node> st = new Stack<>();
        for(int i=0;i<arr.length-1;i++){
            int ele = arr[i];
            if(ele != -1) st.push(new Node(ele));
            else{
                Node node = st.pop();
                st.peek().childs.add(node);
            }
        }

        return st.pop();
    }

    public static void display(Node node){
        
        StringBuilder sb = new StringBuilder();
        sb.append(node.data + " -> ");
        
        for(Node child : node.childs) sb.append(child.data + " ");
        System.out.println(sb);

        for(Node child : node.childs) display(child);
    }

    public static int height(Node node){ // height in terms of edges.
        int h = -1;
        for(Node child : node.childs) h = Math.max(h,height(child));

        return h + 1;
    }

    public static int size(Node node){
        int s = 0;
        for(Node child : node.childs) s += size(child);

        return s + 1;
    }

    public static boolean find(Node node,int data){
        if(node.data == data) return true;
        
        for(Node child : node.childs) if(find(child,data)) return true;
        
        return false;
    }

    public static boolean rootToNodePath(Node node,int data,ArrayList<Node> list){

        if(node.data == data){
            list.add(node);
            return true;
        }

        boolean res = false;
        for(Node child : node.childs){
            res = res || rootToNodePath(child,data,list);
        }

        if(res) list.add(node);
        return res;

    }

    public static Node LCA(Node node,int d1,int d2){
        ArrayList<Node> l1 = new ArrayList<>();
        ArrayList<Node> l2 = new ArrayList<>();

        rootToNodePath(node,d1,l1);
        rootToNodePath(node,d2,l2);

        Node LCA = null;
        int i = l1.size()-1;
        int j = l2.size()-1;

        while(i>=0 && j>=0){
            if(l1.get(i) != l2.get(j)) break;

            LCA = l1.get(i);
            i--;
            j--;
        }

        return LCA;
    }

    public int burningTree(Node root, int tar, List<List<Integer>> ans) {
        if(root.val == data) {
            kDown(root, null, 0, ans);
            return 1;
        }

        int time = -1;
        Node blockNode = null;
        for(Node child: root.childs) {
            time = burningTree(child, tar, ans);
            if(time != -1) {
                blockNode = child;
                break;
            }
        }

        if(time != -1) {
            kDown(root, blockNode, time, ans);
            time++;
        }

        return time;
    }

    public void kDown(Node root, Node blockNode, int time, List<List<Integer>> ans) {
        if(root == blockNode) return;

        if(ans.size() == time) 
            ans.add(new ArrayList<>());

        ans.get(time).add(root.val);
        for(Node child: root.childs) {
            kDown(child, blockNode, time + 1, ans);
        }
    } 
    //[end]


    public void lineWiseOrder(Node root) {
        LinkedList<Node> ll = new LinkedList<>();
        ll.addLast(root);

        int lvl = 0;
        while(ll.size() > 0) { 
            int size = ll.size();
            System.out.print(lvl + ": ");
            while(size--> 0) {
                Node rn = ll.removeFirst();
                System.out.print(rn.data + ", ");

                for(Node child: rn.childs) {
                    if(child != null) {
                        ll.addLast(child);
                    }
                }
            }
            lvl++;
            System.out.println();
        }
    } 

    // mirror
    public void isMirror(Node node1, Node node2) {
        if(node1.childs.size() != node2.childs.size()) return false;
        if(node1.data != node2.data) return false;

        for(int i = 0; j = node2.childs.size() - 1; j >= 0; i++,j--){
            Node n1 = node1.childs.get(i);
            Node n2 = node2.childs.get(i);
            if(!isMirror(n1, n2)) {
                return false;
            }
        }
        return true;
    }

    //flatten tree
    public Node flatten(Node root) {
        
        int n = root.childs.size();
        Node lchild = root.childs.get(n - 1);
        Node gtail = flatten(lchild);

        for(int i = n - 2; i >= 0; i--) {
            Node ntail = flatten(root.childs.get(i));
            ntail.childs.add(root.childs.get(i + 1));
            root.childs.remove(i + 1);
        }
        return gtail;
    }
}
