/**
 * Created by nkubiak on 15.10.18.
 */

import java.util.ArrayList;


public class main {
    private static ArrayList<String> files;

    public static void main(String[] args){
        files = Tools.loadFileList();
        System.out.println(files);

    }
}
