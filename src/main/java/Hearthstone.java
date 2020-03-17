import org.json.*;

import java.util.Scanner;

public class Hearthstone {
    public static void main(String[] args) {
        JSONObject myObject = new JSONObject();

        // Basic strings
        myObject.put("name", "Carlos");
        myObject.put("last_name", "Carlos");

        // Primitive values
        myObject.put("age", Integer.valueOf(21));
        myObject.put("bank_account_balance", new Double(20.2));
        myObject.put("is_developer", Boolean.TRUE);

        // Key with array
        double[] myList = {1.9, 2.9, 3.4, 3.5};
        myObject.put("number_list", myList);

        // Object inside object
        JSONObject subdata = new JSONObject();
        myObject.put("extra_data", subdata);

        // Generate JSON String
        System.out.println(myObject);



        JSONArray myArray = new JSONArray();

        // Push mixed values to the array
        myArray.put(1);
        myArray.put(2);
        myArray.put(3);
        myArray.put(4);
        myArray.put(Boolean.TRUE);
        myArray.put(Boolean.FALSE);
        myArray.put("Some String Value");

        // Generate JSON String
        System.out.println(myArray);

        JSONArray myJson = new JSONArray("[123,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]");

        // Print first value of the array
        System.out.println(myJson.get(0));

        // Print second value of the array
        System.out.println(myJson.get(1));
    }

}
