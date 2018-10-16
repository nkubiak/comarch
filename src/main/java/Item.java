import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by nkubiak on 15.10.18.
 */
public class Item {
    private String type;
    private int key;
    private String identifier;
    private HashMap<String, String> simpleAttributes;

    public Item(String type, int key, String identifier, HashMap<String, String> simpleAttributes) {
        this.type = type;
        this.key = key;
        this.identifier = identifier;
        this.simpleAttributes = simpleAttributes;
    }

    public JSONObject toJSONobject(){
        JSONObject result = new JSONObject();
        result.put("type", type);
        result.put("key", key);
        result.put("identifier", identifier);
        result.put("simpleAttributes", simpleAttributes);

        return result;
    }
}
