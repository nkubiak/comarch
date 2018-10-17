import au.com.bytecode.opencsv.CSVReader;
import org.json.simple.JSONArray;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        NodeList node_items;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            Element root = doc.getDocumentElement();
            node_items = root.getElementsByTagName("produkt");

            for(int i = 0; i < node_items.getLength(); i++){
                Node node = node_items.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    int key = Integer.parseInt(element.getAttribute("klucz"));
                    String type = element.getAttribute("typ");
                    String identifier = element.getElementsByTagName("identyfikator").item(0).getFirstChild().getNodeValue();
                    NodeList parameters = (NodeList) element.getElementsByTagName("parametry").item(0);

                    HashMap<String, String> simpleAttributes = new HashMap<String, String>();

                    for (int j = 0; j < parameters.getLength(); j++){
                        Node parameter = parameters.item(j);

                        if(parameter.getNodeType() == Node.ELEMENT_NODE) {
                            String k = parameter.getNodeName();
                            String v = parameter.getTextContent();
                            simpleAttributes.put(k, v);
                        }
                    }
                    items.add(new Item(type, key, identifier, simpleAttributes));
                }
            }
        }
        catch (IOException e){
            System.out.println("Nie znaleziono pliku: " + path);
        }
        catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }

    }

    public JSONArray getJSONstore(){
        JSONArray result = new JSONArray();
        for (Item i: items) result.add(i.toJSONobject());
        return result;

    }

    public void printItems(){
        for (Item i: items) System.out.print(i.toString());
    }
}
