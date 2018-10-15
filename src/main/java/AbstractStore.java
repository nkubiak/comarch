import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by nkubiak on 15.10.18.
 */
public abstract class AbstractStore {
    protected String type;
    protected int key;
    protected String identifier;
    protected HashMap<String, String> simpleAttributes;

    public abstract JSONObject toJSON();
}
