package be.howest.nmct;

/**
 * Created by Christophe on 09-02-15.
 */

/**
 * Created by Jonas on 9/02/2015.
 */
//package be.howest.jonas.madl1;

/**
 * Created by Jonas on 9/02/2015.
 */
public class StopAfstandInfo {
    private int snelheid;
    private float reactieTijd;
    //    private float stopAfstand;
    private WegType wegType;

    public enum WegType {
        DROOG(8.0f), NAT(8.0f);

        private float remafstand;
        WegType(float remafstand){
            this.remafstand = remafstand;
        }

        public float getRemafstand() {
            return remafstand;
        }
    }

    public int getSnelheid() {
        return snelheid;
    }

    public void setSnelheid(int snelheid) {
        this.snelheid = snelheid;
    }

    public void setReactieTijd(float reactieTijd) {
        this.reactieTijd = reactieTijd;
    }

    public void setWegType(WegType wegType) {
        this.wegType = wegType;
    }

    public float getReactieTijd() {
        return reactieTijd;
    }

    public WegType getWegType() {
        return wegType;
    }

    /**
     * Example berekening remvertraging
     * @return
     */
    public int getRemvertraging() {
        return (getWegType()==WegType.DROOG) ? 8 : 5;
    }

    public StopAfstandInfo() {
        this(100, 0.5f, 0, WegType.DROOG);
    }

    public StopAfstandInfo(int snelheid, float reactieTijd, float stopAfstand, WegType wegType) {
        this.snelheid = snelheid;
        this.reactieTijd = reactieTijd;
//        this.stopAfstand = stopAfstand;
        this.wegType = wegType;
    }

    /**
     * geeft stopafstand terug op basis van de formule
     * @return
     */
    public float getStopafstand() {
        float snelheidinMeter = getSnelheid() * 1000 / 3600;
        return getSnelheid() * getReactieTijd() + (float)Math.pow(snelheidinMeter,2) / (2 * getRemvertraging());
    }

    @Override
    public String toString() {
        return "De stopafstand is: " + getStopafstand();
    }
}
