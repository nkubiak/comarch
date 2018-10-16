/**
 * Created by nkubiak on 15.10.18.
 */

import java.util.ArrayList;


public class main {
    private static ArrayList<String> files;
    private static String out;
    private static Store store;

    public static void main(String[] args){
        files = Tools.loadFileList();
        out = Tools.getOutputPath();
        System.out.println(files);
        System.out.println(out);

        store = new Store(files);
        Tools.writeOutput(store.getJSONstore(), out);

    }
}
