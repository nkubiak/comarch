import au.com.bytecode.opencsv.CSVReader;
import org.json.simple.JSONArray;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nkubiak on 16.10.18.
 */
public class Store {
    private ArrayList<String> filelist;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Store(ArrayList<String> filelist){
        this.filelist = filelist;

        for (String s : filelist){
            String extension = s.substring(s.length() - 3);
            if (extension.equals("xml")) getItemsFromXml(s);
            else if (extension.equals("csv")) getItemsFromCsv(s);
        }
    }

    private void getItemsFromCsv(String path){
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line = reader.readNext();
            line = reader.readNext();
            while (line != null){
                HashMap<String, String> simpleAttributes = new HashMap<String, String>();
                String concatenatedLine = "";
                for (String l : line) {
                    concatenatedLine += l; // Lib ma jakiegoś buga i rozdziela autorów Matrixa (przecinek)
                }
                String[] itemElements = concatenatedLine.split(";");
                System.out.println(concatenatedLine);
                if(itemElements.length == 5) {
                    simpleAttributes.put("Author", itemElements[3]);
                    simpleAttributes.put("Title", itemElements[4]);
                    items.add(new Item(itemElements[2], Integer.parseInt(itemElements[0]), itemElements[1], simpleAttributes));
                }
                line = reader.readNext();
            }
        }
        catch (IOException e){
            System.out.println("Nie znaleziono pliku: " + path);
        }
    }
    private void getItemsFromXml(String path){
        System.out.println("XML: " + path);
    }

    public JSONArray getJSONstore(){
        JSONArray result = new JSONArray();
        for (Item i: items) result.add(i.toJSONobject());
        return result;

    }
}
