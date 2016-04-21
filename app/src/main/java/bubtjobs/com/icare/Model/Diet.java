package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/20/2016.
 */
public class Diet {
    private String dietId;
    private String dietType;
    private String menu;
    private String dietTime;
    private String dietDate;

    public Diet(){}

    public Diet(String dietId, String dietType, String menu, String dietTime, String dietDate) {
        this.dietId = dietId;
        this.dietType = dietType;
        this.menu = menu;
        this.dietTime = dietTime;
        this.dietDate = dietDate;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDietTime() {
        return dietTime;
    }

    public void setDietTime(String dietTime) {
        this.dietTime = dietTime;
    }

    public String getDietDate() {
        return dietDate;
    }

    public void setDietDate(String dietDate) {
        this.dietDate = dietDate;
    }
}
