package bubtjobs.com.icare.Model;

/**
 * Created by Mobile App Develop on 20-4-16.
 */
public class Profile_Add {
    private String name;
    private String relation;
    private String age;
    private String height;
    private String weight;
    private String major_dis;
    private String blood;

    public Profile_Add(String name, String relation, String age, String height, String weight, String major_dis, String blood) {
        this.name = name;
        this.relation = relation;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.major_dis = major_dis;
        this.blood = blood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMajor_dis() {
        return major_dis;
    }

    public void setMajor_dis(String major_dis) {
        this.major_dis = major_dis;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
