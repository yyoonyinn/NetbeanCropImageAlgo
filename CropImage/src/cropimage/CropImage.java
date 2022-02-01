/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cropimage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author macintoshhd
 */
public class CropImage {
    
    //static Map<String,Set<Integer>> DBmap = new HashMap<>();
    static HashMap<String,Set<Integer>> MockDBmap = new HashMap<>();
    static Set<Integer> set1 = new HashSet<>();
    static ArrayList <String> total = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void GetfromDB()
    {
        set1.add(1); 
        set1.add(2); 
        set1.add(3);
        set1.add(2);
        MockDBmap.put("pie", set1);
        MockDBmap.put("pasta pie", set1);
        MockDBmap.put("pasta pie  apple", set1);
        MockDBmap.put("Raspberries", set1);
        String x = "Raspberries pie";
        MockDBmap.put(x.toLowerCase(), set1);
        MockDBmap.put("blueberries", set1);
        MockDBmap.put("strawberry", set1);
        MockDBmap.put("basketball", set1);
        MockDBmap.put("blackberry", set1);
        MockDBmap.put("white pepper powder", set1);
        MockDBmap.put("Egg", set1);
        MockDBmap.put("basketball", set1);
        MockDBmap.put("citrus pectin", set1);
    }
       
        public static boolean isNumber(String data){
//        try {
//            Double.parseDouble(data);
//            return true;
//
//        }catch(Exception e){
//            return false;
//        }
        String regex = "^[0-9]+$"; //a number
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        if(!m.matches()) return false; //is String
        else return  true; //is Number

    }

    public static boolean onlySpecialCharacters(String str)
    {

        // Regex to check if a string contains
        // only special characters
        String regex = "[^a-zA-Z0-9]+";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // then print No
        if (str == null) {
            return true;
        }

        // Find match between given string
        // & regular expression
        Matcher m = p.matcher(str);

        // Print Yes If the string matches
        // with the Regex
        if (m.matches())
            return true;
        else
            return false;
    }

    public static String clean_ingredient(String ingredient){
        String datas[] = ingredient.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for(String data: datas){
            if(!isNumber(data)) sb.append(data+" ");
        }
        return sb.toString();
    }

    public static int levenshtein(String token1, String token2) {

        int[] distances = new int[token1.length() + 1];

        for (int t1 = 1; t1 <= token1.length(); t1++) {
            if (token1.charAt(t1 - 1) == token2.charAt(0)) {
                distances[t1] = calcMin(distances[t1 - 1], t1 - 1, t1);
            } else {
                distances[t1] = calcMin(distances[t1 - 1], t1 - 1, t1) + 1;
            }
        }

        int dist = 0;
        for (int t2 = 1; t2 < token2.length(); t2++) {
            dist = t2 + 1;
            for (int t1 = 1; t1 <= token1.length(); t1++) {
                int tempDist;
                if (token1.charAt(t1 - 1) == token2.charAt(t2)) {
                    tempDist = calcMin(dist, distances[t1 - 1], distances[t1]);
                } else {
                    tempDist = calcMin(dist, distances[t1 - 1], distances[t1]) + 1;
                }
                distances[t1 - 1] = dist;
                dist = tempDist;
            }
            distances[token1.length()] = dist;
        }
        return dist;
    }

    static int calcMin(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        } else if (b <= a && b <= c) {
            return b;
        } else {
            return c;
        }
    }
    
    public static void printAllCombinations(List<List<String>> lists, String result, int n)
    {
        // base case
        if (lists == null || lists.size() == 0) {
            return;
        }

        // if we have traversed each list
        if (n == lists.size())
        {
            // print phrase after removing trailing space
            System.out.println(result.substring(1));
            return;
        }

        // get the size of the current list
        int m = lists.get(n).size();
        String matchedingredient = null;
        // do for each word in the current list
        for (int i = 0; i < m; i++)
        {
            // append current word to output
            String out = (result + " " + lists.get(n).get(i)).trim();
            if(MockDBmap.containsKey(out)) 
            {
                matchedingredient = out;
                total.add(out);
                System.out.println("concat and match = " + out);
                break;
            }

            // recur for the next list
            printAllCombinations(lists, out, n + 1);
        }
    }
    
    
    //Main
        public static void main(String[] args) {
        GetfromDB();
        String Result = "";
        ArrayList<Integer> cateArr = null;
        String sb = "ingredients: Raspberies pie, Sirawberies, Blarckbenies,pie,BDebermes, sugar, geling agent: cirus pecin,aoiy, white pepper powder, baskebol";
        String InputData[] = sb.toString().toLowerCase().replaceAll("(\\t|\\r?\\n)+", " ")
        .replace(" or ",",").replace(" and ",",").replace(" contain ",",")
        .replace(" contain ",",").replace(" contains ",",").replace(" containing ",",")
        .replace(" including ",",").replace(" traces of ",",").replace(" other ",",")
        .replace(" with ",",").replace(" of ",",").replace("-","").replace(" a ","")
        .replace(" an ","").replace(" the ","")
        .split("[,(.:%&;)/]+",-1);
        for(int i=0; i < InputData.length; i++){System.out.println("print input"+ (i+1) +" : " +InputData[i]);}
            

                    // To reduce Redundant words
        TreeSet<String> InputData_set = new TreeSet<>(Arrays.asList(InputData));

        HashSet<Integer> catResult = new HashSet<>();
        HashSet<String> ingredient_set = new HashSet<>();

//--------------Createing Dictionary----------------
//        TreeSet<String> DBdict = new TreeSet<>();
//        for(String dictionary: MockDBmap.keySet()){
//            if (!dictionary.contains(" ")) {
//                DBdict.add(dictionary.toLowerCase());
//            } else {
//                String[] strs= dictionary.split("\\s");
//                for(String str: strs) {
//                    DBdict.add(str.toLowerCase());
//                }
//            }
//        }
        System.out.println("--------Using Dict----------");
        TreeSet<String> DBdict = new TreeSet<>();
        String dictionaryFileName = ("dictionary.txt");
        try {
            FileReader fr = new FileReader(dictionaryFileName);
            BufferedReader br = new BufferedReader(fr);	       
            String line = null;
            while ((line = br.readLine()) != null) {          			        					
                    DBdict.add(line.toLowerCase());
                }
                fr.close();
                br.close();
        }catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
//--------------End of creating Dictionary----------------

        Metric<String> levenshteinDistance = new Metric<String>() {
            @Override
            public int distance(String x, String y) {
                return levenshtein(x,y);
            }
        };

        //create BKtree from dict
        MutableBkTree<String> bkTree = new MutableBkTree<>(levenshteinDistance);
        bkTree.addAll(DBdict);
        BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);


        for (int i=0; i<InputData_set.size(); i++){
            String ing_data = InputData_set.toArray()[i].toString().trim();
            System.out.println("print input.trim" +(i+1) +" : " +ing_data);
            if(!isNumber(ing_data) && !onlySpecialCharacters((ing_data)) && !ing_data.matches("^\\s*$")){

                String []ingredient = clean_ingredient(ing_data).trim().split("\\s");

                //String result_bktree = ""; 
                List<List<String>> result_set = new ArrayList<>(); // result set of min distance

                for(String subing: ingredient){
                    //Set<BkTreeSearcher.Match<? extends String>> matchedwords = searcher.search(subing, 4);
                    Set<BkTreeSearcher.Match<? extends String>> matchedwords = searcher.search(subing, subing.length()/2);
//version1 
//                    int min_dist = Integer.MAX_VALUE;
//                    String min_matchedword = null;
//                    for (BkTreeSearcher.Match<? extends String> match : matchedwords){
//                        int match_dist = match.getDistance();
//                        String match_name = match.getMatch();
//
//                        if(match_name != null && match_dist < min_dist){
//                            min_dist = match_dist;
//                            min_matchedword = match_name;
//                        }
//                    }
//                    if(min_matchedword == null)
//                        result_bktree += subing+" ";
//                    else
//                        result_bktree += min_matchedword+" ";
                    //System.out.println(min_dist);
                    //result_bktree += min_matchedword+" ";
                    //if(min_dist <= subing.length()/3) result_bktree += min_matchedword+" ";
                    
                    //else result_bktree += subing+" ";
// End of version1

//version2
                TreeMap <Integer, TreeSet> min_match = new TreeMap<>();

                for (BkTreeSearcher.Match<? extends String> match : matchedwords){
                    int match_dist = match.getDistance();
                    String match_name = match.getMatch();

                    if(min_match.containsKey(match_dist)){
                        min_match.get(match_dist).add(match_name);
                    } else {
                        TreeSet<String> set = new TreeSet<>();
                        set.add(match_name);
                        min_match.put(match_dist, set);

                    }

                }
                List min_word = new ArrayList(min_match.firstEntry().getValue());
                if(min_word != null) result_set.add(min_word);
                }

                printAllCombinations(result_set, "", 0);
                //System.out.println("matched result in DB : " + result_total);
//                System.out.println("print output"+ (i+1) +" : " +result_bktree);
//                String allergicIng = result_bktree.trim();
//                Set<Integer> triggeredCats = MockDBmap.get(allergicIng);
//                if(triggeredCats  != null){
//                    ingredient_set.add(allergicIng);
//                    catResult.addAll(triggeredCats);
                    
//                }

                for(String t : total){
                    System.out.println("print output : " + t);
                    Set<Integer> triggeredCats = MockDBmap.get(t);
                    if(triggeredCats  != null){
                    ingredient_set.add(t);
                    catResult.addAll(triggeredCats);
                }
                    
                }
                
                

            }
        }
        for(int i=0; i<ingredient_set.size(); i++){
            System.out.println("ingredient_list" + ingredient_set);
            System.out.println("count: "+i);
            if(i != ingredient_set.size()-1) Result += ingredient_set.toArray()[i].toString()+", ";
            else Result += ingredient_set.toArray()[i].toString();
        }

        //cateArr = new ArrayList<>(catResult);

    }
}

    

