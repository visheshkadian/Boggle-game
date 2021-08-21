package Boggle;

public class TreiST {
    private static int R = 26;
    private Node root;

    public TreiST(String[] dictionary) {

        for (int i = 0; i < dictionary.length; i++)
            put(dictionary[i], i);

    }

    private static class Node {
        private int val=-1;
        private Node[] next = new Node[R];
    }

    private void put(String word, int val) {
       root= put(root, word, val, 0);
    }

    private Node put(Node x, String key, int val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c - 65] = put(x.next[c-65], key, val, d + 1);
        return x;
    }


    public boolean prefix(String word) {
        boolean x = prefix(root, word, 0);
        return x;
    }

    private boolean prefix(Node x, String word, int d) {
        if (x == null) return false;
        if (d == word.length()) return true;
        char c = word.charAt(d);
        return prefix(x.next[c - 65], word, d + 1);
    }

    public boolean isWord(String word) {
        Node x=get(root,word,0);
        if(x==null) return false;
        if(x.val >=0) return true;
        else return false;
    }

    private Node get(Node x,String key, int d) {
        if(x==null) return null;
        if(d==key.length()) return x;
        char c=key.charAt(d);
        return get(x.next[c-65],key,d+1);
    }

   public static void main(String args[]) {
      /*  String[] words;
        In in=new In("dictionary-common.txt");

        words=in.readAllStrings();

        TreiST tree = new TreiST(words);**/
       System.out.println(String.valueOf(3).length());
   }


}
