/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dblp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import uit.qass.model.Publication;

/**
 *
 * @author ThuanHung
 */
public class PublicationUtil {
    public static List<List<Publication>> groupByYear(List<Publication> list) {
        int tempYear = list.get(0).getYear();
        List<List<Publication>> result = new ArrayList<List<Publication>>();
        List<Publication> l = new ArrayList<Publication>();

        for (int i = 0; i < list.size(); i++) {
            Publication publication = list.get(i);
            if (publication.getYear() == tempYear) {
                l.add(publication);
            } else {
                tempYear = publication.getYear();
                result.add(l);
                l = new ArrayList<Publication>();
                l.add(publication);
            }
            if (i == list.size() - 1) {
                result.add(l);
            }
        }
        return result;
    }
}
