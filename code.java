import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
class HuffMan {
    HashMap<Character, String> encoder; //map for encoding
    HashMap<String, Character> decoder; // map for decoding

    public HuffMan(String feeder) {
        HashMap<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < feeder.length(); i++) {
            char ch = feeder.charAt(i);
            if (freqMap.containsKey(ch)) freqMap.put(ch, freqMap.get(ch) + 1);
            else freqMap.put(ch, 1);
        }
        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.add(node);
        }
        while (minHeap.size() != 1) {  //creating the huffman tree
            Node first = minHeap.remove();
            Node second = minHeap.remove();
            Node newNode = new Node('\0', first.cost + second.cost); // \0 -> null
            newNode.left = first;
            newNode.right = second;
            minHeap.add(newNode);
        }
        Node root = minHeap.remove();
        encoder = new HashMap<>();
        decoder = new HashMap<>();
        makeMap(root, "");
    }

    private void makeMap(Node root, String str) {  //mapping a character with a set of bits
        if (root == null) return;

        if (root.left == null && root.right == null) {
            encoder.put(root.data, str);
            decoder.put(str, root.data);
        }
        makeMap(root.left, str + "0");
        makeMap(root.right, str + "1");
    }

    public String encode(String source) { // encode the string
        String ans = "";
        for (int i = 0; i < source.length(); i++) {
            ans = ans + encoder.get(source.charAt(i));
        }
        return ans;
    }

    public String decode(String codedString) {  // decode the string
        String key = "";
        String ans = "";
        for (int i = 0; i < codedString.length(); i++) {
            key = key + codedString.charAt(i);
            if (decoder.containsKey(key)) {
                ans = ans + decoder.get(key);
                key = "";
            }
        }
        return ans;
    }
}
class Node implements Comparable<Node>{
    Character data;
    int cost;
    Node left;
    Node right;
    public Node(Character data, int cost){
        this.data = data;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other){  // for priority queue
        return this.cost-other.cost;
    }
}
public class huffManAlgo {

    public static void main(String[] args){
        String str = "abbccdda"; // para of on the basis of which it gives every character a set of bits
        HuffMan hm = new HuffMan(str);
        String coded = hm.encode("aabbccdba"); // string to be encoded
        System.out.println(hm.decode(coded)); //getting string back using the coded string

    }
}
