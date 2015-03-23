package be.howest.nmct;

import java.util.Scanner;

/**
 * Created by Christophe on 09-02-15.
 */
public class RunBMI {
    public static void main(String[] args) {
        BMIInfo bmi = new BMIInfo();
        bmi.setHeight(1.75f);
        bmi.setMass(85);
        System.out.println(bmi);

        Scanner ob=new Scanner(System.in);
        System.out.println("Enter your height: ");
        bmi.setHeight(ob.nextFloat());
        System.out.println("Enter your mass: ");
        bmi.setMass(ob.nextInt());
        System.out.println(bmi);
    }
}
