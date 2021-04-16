public class LRUCache {
    private class Node{
        int key = 0;
        int val = 0;
        Node next;
        Node prev;
        
        Node(int key, int val){
            this.key = key;
            this.val = val;
        }
    }
    
    HashMap<Integer, Node> map;
    Node head = null;
    Node tail = null;
    int maxSize = 0;
    int currSize = 0;

    public LRUCache(int capacity) {
        map = new HashMap<>(capacity);
        maxSize = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        
        Node node = map.get(key);
        makeHeadNode(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            makeHeadNode(node);
        }else{
            Node node = new Node(key, value);
            map.put(key,node);
            addFirstNode(node);
            if(currSize > maxSize) {
                map.remove(this.tail.key);
                removeTailNode(node);
            }
        }
    }
    
    public void makeHeadNode(Node node) {
        if(node == this.head) return;
        else{
            removeNode(node);
            addFirstNode(node);
        }
    }
    
    public void removeNode(Node node) {
        if(currSize == 1) return;
        
        if(node == this.tail) {
            removeTailNode(node);
            return;
        }
        
        Node nextN = node.next;
        Node prevN = node.prev;
        
        prevN.next = nextN;
        nextN.prev = prevN;
        node.next = null;
        node.prev = null;
        this.currSize--;
    }
    
    public void removeTailNode(Node node) {
        if(this.currSize == 1) return;
        
        Node temp = this.tail.next;
        temp.prev = null;
        this.tail.next = null;
        this.tail = temp;
        
        this.currSize--;
    }
    
    public void addFirstNode(Node node) {
        if(this.head == null && this.tail == null) {
            this.head = node;
            this.tail = node;
        }else{
            this.head.next = node;
            node.prev = head;
            this.head = node;
        }
        this.currSize++;
    }
}