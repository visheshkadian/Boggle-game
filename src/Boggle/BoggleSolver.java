package Boggle;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;



public class BoggleSolver
{
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
   private TreiST tree;
   private StringBuilder str=new StringBuilder();
   private HashSet<String> words;
    private boolean[] marked;
    public BoggleSolver(String[] dictionary) {
         tree=new TreiST(dictionary);


    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        Bag<Integer>[] bags=drawGraph(board);
        marked=new boolean[board.rows() * board.cols()];
        words=new HashSet<>();
        for(int i=0;i<bags.length;i++) {

           dfs(board,bags,i);

            }
            return words;
        }



    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if(tree.isWord(word))
        {
            if(word.length()<=2) return 0;
            else if(word.length()<=4) return 1;
            else if(word.length()==5) return 2;
            else if(word.length()==6) return 3;
            else if(word.length()==7) return 5;
            else return 11;
        }
        else
            return 0;
    }

    private Bag<Integer>[] drawGraph(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        int cap=rows*cols;
        Bag<Integer>[] bags = (Bag<Integer>[]) new Bag[cap];
        for (int i = 0; i < bags.length; i++) {
            bags[i] = new Bag<Integer>();

            int[] l = {i,i+1,i-1,i + cols, i + cols - 1, i + cols + 1, i - cols - 1, i - cols, i - cols + 1};
            int[] m = {i,i-1,i+cols,i+cols-1,i-cols-1,i-cols};
            int[] n={i,i+1,i+cols,i-cols,i+cols+1,i-cols+1};

            if(i%cols==0)
                addEdges(bags,n,i,cap, board);

             else if(i%cols==cols-1)
                addEdges(bags,m,i,cap, board);
            else
                addEdges(bags,l,i,cap, board);
        }

        return bags;
    }

    private void addEdges(Bag<Integer>[] bags, int[] edges, int v, int cap,BoggleBoard board) {
        int rowv = getRow(v, board.cols());
        int rowe = 0;
        for(int e:edges) {
          rowe = getRow(e, board.cols());
            if(e>=0 && e <cap && Math.abs((Math.abs(rowv) - Math.abs(rowe))) <= 1  )
            {
              bags[v].add(e);
            }
        }
    }
        private void dfs(BoggleBoard board,Bag<Integer>[] bags, int n) {
            boolean isQ = false;
            char letter = board.getLetter(getRow(n,board.cols()),getCol(n, board.cols()));
            marked[n]=true;
            if (letter == 'Q'){
                isQ = true;
            }
            str.append(letter);
            if (isQ)
                str.append('U');
            String word=str.toString();

            if(tree.isWord(word) && word.length()>2) words.add(word);

            for(Integer adj:bags[n]) {
                if(!marked[adj] && tree.prefix(str.toString())) {

                    dfs(board,bags,adj);}
            }
            marked[n]=false;
                if (str.length() > 0 ) {
                    str.deleteCharAt(str.length() - 1);
                    if(isQ && str.length() > 0) str.deleteCharAt(str.length() - 1);
                }



        }
  private int getRow(int n,int col) {
      return (n/col);
  }
   private int getCol(int n,int col) {
        return (n % col);
           }

   public static void main(String[] args) {
        char[][] chars=new char[4][4];
     /*   chars[0][0]='G';
        chars[0][1]='N';
        chars[0][2]='E';
        chars[0][3]='S';
        chars[1][0]='S';
        chars[1][1]='R';
        chars[1][2]='I';
        chars[1][3]='P';
        chars[2][0]='E';
        chars[2][1]='T';
        chars[2][2]='A';
        chars[2][3]='L';
        chars[3][0]='T';
        chars[3][1]='S';
        chars[3][2]='E';
        chars[3][3]='B';**/
       chars[0][0]='S';
       chars[0][1]='N';
       chars[0][2]='R';
       chars[0][3]='T';
       chars[1][0]='O';
       chars[1][1]='I';
       chars[1][2]='E';
       chars[1][3]='L';
       chars[2][0]='E';
       chars[2][1]='Q';
       chars[2][2]='T';
       chars[2][3]='T';
       chars[3][0]='R';
       chars[3][1]='S';
       chars[3][2]='A';
       chars[3][3]='T';


        BoggleBoard board=new BoggleBoard("board-dichlorodiphenyltrichloroethanes.txt");
        System.out.println(board);
        String[] words;
        In in=new In("dictionary-yawl.txt");

        words=in.readAllStrings();

        BoggleSolver solver=new BoggleSolver(words);

        int count=0;
       for(String word: solver.getAllValidWords(board)){
           System.out.println(word);
           System.out.println(solver.scoreOf(word));
           count+=solver.scoreOf(word);}
        System.out.println("Score of this board= "+count);

    }



}






