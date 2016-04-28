package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/28/2016.
 */
public class Health_Tips {
    private String title;
    private String des;

    public Health_Tips(String title, String des) {
        this.title = title;
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
