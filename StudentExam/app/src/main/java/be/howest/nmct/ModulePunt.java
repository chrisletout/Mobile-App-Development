package be.howest.nmct;

/**
 * Created by Christophe on 09-02-15.
 */
public class ModulePunt {
    String moduleNaam;
    int aantalStudiePunten;
    double score;

    public String getModuleNaam() {
        return moduleNaam;
    }

    public void setModuleNaam(String moduleNaam) {
        this.moduleNaam = moduleNaam;
    }

    public int getAantalStudiePunten() {
        return aantalStudiePunten;
    }

    public void setAantalStudiePunten(int aantalStudiePunten) {
        this.aantalStudiePunten = aantalStudiePunten;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public ModulePunt(){

    }
    public ModulePunt(String moduleNaam, double score){
        this.moduleNaam = moduleNaam;
        this.score = score;
    }
    public int hashCode(){
        return 0;
    }
    public boolean equals(Object o){
        return true;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
