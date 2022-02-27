package my.traditionalfood.application.CusFunctions;

public class CusInf {

    private String lname, fname, email, password, confirmp, mobile, address, district, city;

    public CusInf(String lname, String fname, String email, String password, String confirmp, String mobile, String address, String district, String city) {
        this.lname = lname;
        this.fname = fname;
        this.email = email;
        this.password = password;
        this.confirmp = confirmp;
        this.mobile = mobile;
        this.address = address;
        this.district = district;
        this.city = city;
    }

    public CusInf(){

    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmp() {
        return confirmp;
    }

    public void setConfirmp(String confirmp) {
        this.confirmp = confirmp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
