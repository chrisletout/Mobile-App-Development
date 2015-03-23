package be.howest.nmct;

/**
 * Created by Christophe on 09-02-15.
 */

//package be.howest.jonas.madl1;

/**
 * Created by Jonas on 9/02/2015.
 */
public class TestStopAfstandInfo {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        StopAfstandInfo sai = new StopAfstandInfo();
        sai.setSnelheid(100);
        sai.setReactieTijd(1.5f);
        sai.setWegType(StopAfstandInfo.WegType.NAT);
        System.out.println(sai);
    }
}
