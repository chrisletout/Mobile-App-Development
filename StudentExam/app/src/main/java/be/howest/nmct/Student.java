package be.howest.nmct;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Christophe on 09-02-15.
 */
public class Student implements Comparable {
    public String getEmailStudent() {
        return emailStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }
    public Student(){

    }
    private void voegScoreToe(String sModuleNaam, double score){
        ModulePunt m = new ModulePunt(sModuleNaam, score);
        scoresStudent.put(sModuleNaam,m);
    }
    private void getTotaleScoreStudent(){
        /*
        Bepaal de totale som van alle studiepunten van de deelgenomen studiepunten
        o  Overloop alle scores. Vermenigvuldig elke score met het gewicht van zijn module.
        Het gewicht van de module is het aantal studiepunten van deze module gedeeld
        door de totale som van alle aantal studiepunten. Tel alle verkregen producten op
        om het totaal percentage te verkrijgen.
        */


    }
    public int compareTo(Object o) {
//        Animal a = (Animal) o;
//        return this.year_discovered - o.year_discovered;
        return 0;
    }
    public static List<Double> getScoresModule(List<Student> studenten){

    }
    public static double getGemiddeldeScoreModule(List<Student> studenten, String moduleNaam){

    }
    public static void sorteerStudenten(List<Student> studenten){

    }
    @Override
    public String toString() {
        return super.toString();
    }

    String emailStudent;
    HashMap<String, ModulePunt> scoresStudent;
}
