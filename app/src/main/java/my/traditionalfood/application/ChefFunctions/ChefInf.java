package my.traditionalfood.application.ChefFunctions;

public class ChefInf {

    private String lname, fname, email, password, confirmp, mobile, address, district, city;

    public ChefInf(String lname, String fname, String email, String password, String confirmp, String mobile, String address, String district, String city) {
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

    public ChefInf() {

    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmp() {
        return confirmp;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }
}
