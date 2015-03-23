package be.howest.nmct;

/**
 * Created by Christophe on 09-02-15.
 */
public class BMIInfo {
    float height = 1.70f;
    int mass = 70;

    public void setBmiIndex(float bmiIndex) {
        this.bmiIndex = bmiIndex;
    }

    float bmiIndex = 0;
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }
    public BMIInfo(){

    }
    public BMIInfo(float height, int mass){
        this.height = height;
        this.mass = mass;
    }
    public void recalculate(){
        //gewicht / (lengte * lengte)
//        bmiIndex = mass/(height*height);
        setBmiIndex(getMass()/(getHeight()*getHeight()));

    }
    @Override
    public String toString() {
        recalculate();
        return "Je BMI iS: " + bmiIndex+" je category is: "+ Category.getCategory(bmiIndex);
    }
    public enum Category {
        GROOTONDERWICHT(0,15),
        ERNSTIGODERWICHT(15,16),
        ONDERWICHT(16, 18.50f),
        NORMAAL(18.50f,25),
        OVERGEWICHT(25,30),
        MATIG(30,35),
        ERNSTIG(35,40),
        ZEERGROOT(40, Float.POSITIVE_INFINITY)
        ;
        float lowerBoundary, upperBoundary;

        public float getLowerBoundary() {
            return lowerBoundary;
        }

        public float getUpperBoundary() {
            return upperBoundary;
        }

        public boolean isInBoundary(float f){
            if(f > lowerBoundary && f <= upperBoundary)
                return true;
            else
                return false;
        }
        public static Category getCategory(float index) {
            for(Category category : Category.values()) {
                if(category.isInBoundary(index)) return category;
            }
            return null;
        }
        Category(float lowerBoundary, float upperBoundary){
            this.lowerBoundary = lowerBoundary;
            this.upperBoundary = upperBoundary;
       }
    }

}
