import java.io.File;
import java.lang.Object;
import java.lang.String;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];

        //TODO
        //List<String> list = new ArrayList<String>();
       // Map tokenMap = new HashMap<String, int>(); //One possible approach is to use Maps in java <Token, Index>
        //Map tokenMap = new HashMap();
        Map<String, Integer> tokenMap = new HashMap<String, Integer>();
       // tokenMap.put("Test",1);
        Scanner in = new Scanner(new File(inputFileName));
        while (in.hasNextLine()) {

            //1.	Divide each sentence into a list of words using delimiters provided in the “delimiters” variable

            StringTokenizer st = new StringTokenizer(in.nextLine(), "_().,");
            while (st.hasMoreTokens()) {
                String tokeenAtom = new String(st.nextToken());

                tokeenAtom = tokeenAtom.toLowerCase().trim().toString(); //2.	Make all the tokens lowercase and remove any tailing and leading spaces
                System.out.println(tokeenAtom);
                int flag = 0; // flag for checking existence token in stopWordsArray
                    for (int i = 0; i < stopWordsArray.length; i++) { //3.	Ignore all common words provided in the “stopWordsArray” variable

                        if (stopWordsArray[i].contains(tokeenAtom)) {
                        flag++;
                        }
                    }
                if (flag == 0) {
                    //4.	Keep track of word frequencies. To make the application more interesting, you have to process only the titles with certain indexes
                    Integer[] myIndexes = getIndexes(); //you have to process only the titles with certain indexes
                    String keyForInsert = new String();
                    int indexOfKey = 0; //counter of repeats
                    for (String key : tokenMap.keySet()) {
                        if (key.hashCode() == tokeenAtom.hashCode()) {
                            int indexIncrease = tokenMap.get(key);
                            tokenMap.put(key, indexIncrease++);
                            indexOfKey++;
                        }
                        ;
                    }
                    if (indexOfKey == 0) {   //New Token injection;
                        tokenMap.put(tokeenAtom, 1);
                    }
                }
            }
        }
            // 5.	Sort the list by frequency in a descending order



        //6.	Return the top 20 items from the sorted list as a String Array.
        int counter20 = 0;
        for (String key : tokenMap.keySet()) {
            {
                if (counter20 < 20) {
                    ret[counter20] = key;
                    System.out.println(key + "   " + tokenMap.get(key).toString());
                }
                counter20++;
            }
            //        String[] array = list.toArray(new String[0]);
        }
            return ret;

    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
