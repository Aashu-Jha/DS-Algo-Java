import java.util.Arrays;
import java.util.HashSet;

public class Backtracking3 {
    
    public static void main(String[] args) {
        solve();
    }

    public static  void solve() {
        solveBox();
    }

    static String s1 = "send";
    static String s2 = "more";
    static String s3 = "money";
    static int[] mapping = new int[26];  //used to map value from (0,9);
    static boolean[] numUsed = new boolean[10]; //to check for duplication in mapping array;

    //Pepcoding - Crypthemetic
    //The main story is that * we will take out every string unique chars and then convert it into oneString 
    // * We will pass the string and idx
    // * A array mapping we map all the value(0,9) and at the breaking point compare take out the value 
    //   of all string of mapped value and compare x + y == z
    public static void crypto() {
        Arrays.fill(mapping, -1);
        String str = s1 + s2 + s3;

        int uniqueSet = 0;
        for(int i = 0; i< str.length(); i++) {
            char c = str.charAt(i);

            uniqueSet |= (1 << (c - 'a'));
        }

        StringBuilder newStr = new StringBuilder();
        for(int i = 0; i < 26; i++) {
            int mask = (1 << i);
            if((mask & uniqueSet) != 0) {
                newStr.append((char) (i + 'a'));
            }
        }

        System.out.println(cryptoSolver(newStr.toString(), 0));
    }

    // it's main work is to put num in mapping array and when idx == str.length() 
    // take out every string IntegerValue and compare if x + y == z; 
    private static int cryptoSolver(String str, int idx) {
        if(idx == str.length()) {
            int x = stringToNumber(s1);
            int y = stringToNumber(s2);
            int z = stringToNumber(s3);

            if (x + y == z)
            {
                System.out.println(" " + x + "\n+" + y + "\n------\n" + z);
                System.out.println();
                return 1;
            }
            return 0;
        }

        char ch = str.charAt(idx);
        int count = 0;

        for(int num = 0; num <= 9; num++) {

            if((ch == s1.charAt(0) || ch == s2.charAt(0) ||ch == s3.charAt(0)) && num == 0) continue;
            if(!numUsed[num]) {
                numUsed[num] = !numUsed[num];
                mapping[ch - 'a'] = num;
                count += cryptoSolver(str, idx + 1);
                mapping[ch - 'a'] = -1;
                numUsed[num] = !numUsed[num];
            }

        }
        return count;
    }

    //To convert the string into a number  send into number with mapping array;
    private static int stringToNumber(String s12) {
        int res = 0;

        for(int i = 0; i < s12.length(); i++) {
            res = res * 10 + mapping[s12.charAt(i) - 'a'];
        }

        return res;
    }
    //end - crypthemetic

    //start - crossWord Puzzle
    static char[][] box = {{'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
                            {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
                            {'+', '-', '-', '-', '-', '-', '-', '-', '+', '+'},
                            {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
                            {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
                            {'+', '-', '-', '-', '-', '-', '-', '+', '+', '+'},
                            {'+', '-', '+', '+', '+', '-', '+', '+', '+', '+'},
                            {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
                            {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
                            {'+', '+', '+', '+', '+', '+', '+', '+', '+', '+'}};

    public static void solveBox() {
        String[] words = {"agra", "norway", "england", "gwalior"};
        crossWordPuzzle(box, words, 0);
    }
    public static boolean crossWordPuzzle(char[][] arr, String[] word, int vidx) {
        if(vidx == word.length) {
            print2D(arr);
            return true;
        }

        for(int r = 0; r< arr.length; r++) {
            for(int c = 0; c < arr[0].length; c++){
                if(arr[r][c] == '-' || word[vidx].charAt(0) == arr[r][c]){
                    if(canPlaceHorizontal(arr, word[vidx], r, c)) {
                        boolean[] charLoc = placeH(arr, word[vidx], r, c);
                        if(crossWordPuzzle(arr, word, vidx + 1)) return true;
                        unPlaceH(arr, word[vidx], r, c, charLoc);
                    }
                    if(canPlaceVertical(arr, word[vidx], r, c)) {
                        boolean[] charLoc = placeV(arr, word[vidx], r, c);
                        if(crossWordPuzzle(arr, word, vidx + 1)) return true;
                        unPlaceV(arr, word[vidx], r, c, charLoc);
                    }
                }
            }
        }
        return false;
    }

    public static void print2D(char[][] arr) {
        for(int i = 0; i< arr.length; i++) {
            for(int j = 0; j< arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean canPlaceVertical(char[][] arr, String word, int r, int c){
		int i = 0 ;
		for(; i < word.length(); i++){
			if(r + i >= arr.length){
				return false;
			}
			if(arr[r + i][c] == '-' || arr[r + i][c] == word.charAt(i)){
				continue;
			}else{
				return false;
			}
		}
		
		if(r + i == arr.length || arr[r + i][c] == '+') {
			return true;
		}else {
			return false;
		}
	}

	public static boolean canPlaceHorizontal(char[][] arr, String word, int r, int c){
		int i = 0;

        for(; i < word.length(); i++) {
            if(c + i >= arr.length) return false;
            if(arr[r][c + i] == '-' || arr[r][c + i] == word.charAt(i)) continue;
            else{
                return false;
            }
        }

        if(c + i == arr.length || arr[r][c + i] == '+') return true;
        else return false;
	}

    public static boolean[] placeH(char[][] arr, String word, int r, int c) {
        boolean[] charLoc = new boolean[word.length()];

        for(int i = 0; i< word.length(); i++) {
            if(arr[r][c + i] == '-') {
                charLoc[i] = true;
                arr[r][c + i] = word.charAt(i);
            }
        }
        return charLoc;
    }

    public static void unPlaceH(char[][] arr, String word, int r, int c, boolean[] charLoc) {
        for(int i = 0; i < word.length(); i++) {
            if(charLoc[i]) {
                arr[r][c + i] = '-'; 
            }
        }
    }

    

    public static boolean[] placeV(char[][] arr, String word, int r, int c) {
        boolean[] charLoc = new boolean[word.length()];

        for(int i = 0; i< word.length(); i++) {
            if(arr[r + i][c] == '-') {
                charLoc[i] = true;
                arr[r + i][c] = word.charAt(i);
            }
        }
        return charLoc;
    }

    public static void unPlaceV(char[][] arr, String word, int r, int c, boolean[] charLoc) {
        for(int i = 0; i < word.length(); i++) {
            if(charLoc[i]) {
                arr[r + i][c] = '-'; 
            }
        }
    }
    //[end] Pepcoding - CrossWord Puzzle

    //[start] Pepcoding - WordBreak 1
    public static void wordBreak(String str, String ans, HashSet<String> dict){
		if(str.length() == 0){
		    System.out.println(ans);
		    return;
		}
		
		for(int i = 0; i< str.length(); i++){
		    String left = str.substring(0, i+ 1);
		    String right = str.substring(i + 1);
		    if(dict.contains(left)){
		        wordBreak(right, ans + left + " ", dict);
		    }
		}
		
	}
    //[end]
}
