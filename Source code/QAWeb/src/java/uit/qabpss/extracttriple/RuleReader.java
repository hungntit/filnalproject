
package uit.qabpss.extracttriple;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoang nguyen
 */
public class RuleReader {

    public static final String PATH = "uit/qabpss/util/resources/rules/rules.txt";

    public static String[] loadRules() {
        List<String> lst = new ArrayList<String>();
        int i = 0;
        URL url =  Thread.currentThread().getContextClassLoader().getResource(PATH);
        BufferedReader input = null;
        try {
            //use buffering, reading one line at a time
            input = new BufferedReader(new FileReader(new File(url.getPath())));
            String line = null;
            while ((line = input.readLine()) != null) {
                lst.add(line);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(RuleReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(RuleReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst.toArray(new String[i]);
    }
}
