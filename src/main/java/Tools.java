
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by nkubiak on 15.10.18.
 */
public abstract class Tools {
    public static ArrayList<String> loadFileList(){
        ArrayList<String> result = new ArrayList<String>();
        try {
            Object obj = new JSONParser().parse(new FileReader("config"));
            JSONObject filelist = (JSONObject)obj;
            JSONArray array = (JSONArray) filelist.get("filelist");
            if (array == null) throw new ParseException(0);

            for (Object o: array) {
                result.add(o.toString());
            }
        }
        catch (IOException e){
            System.out.println("Nie znaleziono pliku konfiguracyjnego.");
        }
        catch (ParseException e)
        {
            System.out.println("Błąd parsowania.");
        }

        return result;
    }
}
