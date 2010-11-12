/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.extracttriple;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aa
 */
public class RuleReader {
    public static final String PATH = "src\\java\\uit\\qabpss\\extracttriple\\rules.txt";

    public static String[] loadRules() {
        List<String> lst = new ArrayList<String>();
        int i = 0;
        try {
            //use buffering, reading one line at a time
            BufferedReader input = new BufferedReader(new FileReader(new File(PATH)));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    lst.add(line);
                    i++;
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lst.toArray(new String[i]);
    }
}
