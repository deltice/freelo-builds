import java.sql.*;
import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class DatabaseSearcher
{
    
    private String recommendedPath; //the path to be recommended
    private int[] items; //user's items
    private int length; //number of user's items
    
    public DatabaseSearcher (int myChamp, int[] myItems, int[] enemy1, 
                             int[] enemy2, int[] enemy3, int[] enemy4, int[] enemy5) throws FileNotFoundException, IOException {
        int length = 0; //length of non-zero elements in myItems
        //defensive copy
        for (int k = 0; k < 6; k++) {
            items[k] = myItems[k];
        }
        int[] theirItems = new int[30];
        for (int k = 0; k < 6; k++) {
            theirItems[k] = enemy1[k];
            theirItems[k + 6] = enemy2[k];
            theirItems[k + 12] = enemy3[k];
            theirItems[k + 18] = enemy4[k];
            theirItems[k + 24] = enemy5[k];
        }
       
        //calculate length of non-zero elements
        for (int i = 0; i < 6; i++) {
            if (myItems[i] != 0)
                length = i + 1;
        }
        this.length = length;
        //sort non-zero elements in myItems
        Arrays.sort(myItems, 0, length);
        
        //calculate the hash
        String hash = situationHash(myChamp, theirItems);
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //reads from main.db
            //must be in same folder
            c = DriverManager.getConnection("jdbc:sqlite:main.db");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            
            stmt = c.createStatement();
            //table named DATA
            //first column named Hash
            ResultSet rs = stmt.executeQuery( "SELECT * FROM DATA2 WHERE Hash = \"" + hash + "\";" );
            //assume columes are situation(string), followed by 100 buildpaths ranked in order
            int counter = 0;
            while (rs.next()) {
                //counter = columns
                while (counter < 50) {
                    counter++;
                    String count = Integer.toString(counter);
                    String buildPath = rs.getString(count);
                    if (match(myItems, buildPath, length)) {
                        recommendedPath = buildPath; break;
                    }
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public String findNextItem() {
        int[] fullPath = new int[6];
        StringTokenizer st = new StringTokenizer(recommendedPath);
        //buildPath must have 6 items
        for (int k = 0; k < 6; k++) {
            int i = Integer.parseInt(st.nextToken());
            fullPath[k] = i;
        }
        int itemNumber = fullPath[length];
        JSONParser parser = new JSONParser();
        String itemName = "";
        try {
            //reads from a JSON text file of item attributes
            Object obj = parser.parse(new FileReader("ItemRef2.txt"));
            JSONObject json = (JSONObject) obj;
            JSONObject data = (JSONObject) json.get("data");
            JSONObject item = (JSONObject) data.get(itemNumber);
            itemName = (String) item.get("name");
        }catch (FileNotFoundException e) {
        }
        catch (IOException f) {
        }
        catch (ParseException g) {
        }
        return itemName;
    }
    
    //calculates the hash value, same as the hash function for encoding
    public static String situationHash(int Champion, int[] enemyItems) {
        //must make exception if 0, means no item
        /*StringBuilder b = new StringBuilder(Integer.toString(Champion));
        for (int i : enemyItems) {
            b.append(Integer.toString(i));
        }
        return b.toString();*/
        
        long totalAD = 0; //total ad
        long totalAP = 0; //total ap
        long totalAR = 0; //total armor
        long totalMR = 0; //total magic resist

        JSONParser parser = new JSONParser();
        try {
            //reads from a JSON text file of item attributes
            Object obj = parser.parse(new FileReader("ItemRef2.txt"));
            JSONObject json = (JSONObject) obj;
            JSONObject data = (JSONObject) json.get("data");
            for (int k = 0; k < enemyItems.length; k++) {
            JSONObject item = (JSONObject) data.get(Integer.toString(enemyItems[k]));
            JSONObject stats = (JSONObject) item.get("stats");
            Long AP = (Long) stats.get("FlatMagicDamageMod");
            if (AP != null)
                totalAP = totalAP + AP;
            Long AD = (Long) stats.get("FlatAttackSpeedMod");
            if (AD != null)
                totalAD = totalAD + AD;
            Long AR = (Long) stats.get("FlatArmorMod");
            if (AR != null)
                totalAR = totalAR + AR;
            Long MR = (Long) stats.get("FlatSpellBlockMod");
            if (MR != null)
                totalMR = totalMR + MR;
            }
        }catch (FileNotFoundException e) {
        }
        catch (IOException f) {
        }
        catch (ParseException g) {
        }

        long hash = (Champion * 10000) + ((totalMR - 1462)/1285 + 5) + (((totalAR = 1500)/1332 + 5) * 10)
            + (((totalAP - 176)/174 + 5) * 100) + (((totalAD - 440)/492 + 5) * 1000);
        return Long.toString(hash);
        // here is the code to read in 
    }
        
    public static boolean match(int[] myItems, String buildPath, int length) {
        //1000 - int to sort in descending order

        int[] fullPath = new int[6];
        StringTokenizer st = new StringTokenizer(buildPath);
        //buildPath must have 6 items
        for (int k = 0; k < 6; k++) {
            int i = Integer.parseInt(st.nextToken());
            fullPath[k] = i;
        }
        Arrays.sort(fullPath, 0, length);
        /*for (int k = 0; k < 6; k++) {
            System.out.println(myItems[k] + "   " + fullPath[k]);
        }*/
        for (int k = 0; k < length; k++) { 
            if(myItems[k] != fullPath[k]) {
                return false;
            }
        }
        return true;
            
    }
}
    