package bubtjobs.com.icare.Model;

import java.util.Comparator;

/**
 * Created by Murtuza on 4/26/2016.
 */
public class Lat_Lon {
    private  String dif;
    private String lat;
    private String lon;
    private String hospitalName;

    public Lat_Lon(){}
    public Lat_Lon(String dif, String lat, String lon, String hospitalName) {
        this.dif = dif;
        this.lat = lat;
        this.lon = lon;
        this.hospitalName = hospitalName;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public static Comparator<Lat_Lon> StuNameComparator = new Comparator<Lat_Lon>() {

        public int compare(Lat_Lon s1, Lat_Lon s2) {
            String dif1 = s1.getDif();
            String dif2 = s2.getDif();

            //ascending order
            return dif1.compareTo(dif2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
