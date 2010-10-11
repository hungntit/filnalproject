/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.analysisandprocess;

/**
 *
 * @author Hoang-PC
 */
public class Test {
    public static void main(String args[]){
        EntityReg reg = new EntityReg();
        System.out.println(reg.identifiedRecog("Philip K. Chan"));
        System.out.println(reg.identifiedRecog("Is Cross-Platform Security Possible?"));
        System.out.println(reg.identifiedRecog("ACM"));
        System.out.println(reg.identifiedRecog("Marcus Thint"));
        System.out.println(reg.identifiedRecog("Book"));
    }
}
