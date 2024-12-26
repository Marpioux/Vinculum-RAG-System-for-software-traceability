package beans;

import java.sql.Date;

public class OperatoreAgenzia {
    private String name, lastName, cityResidenza, phone, zip, address, email, password, username;
    private Date doB;

    public OperatoreAgenzia() {
    }

    public OperatoreAgenzia(String name, String lastName, String city, String phone, String zip, String address, String email, String pass, String user, Date date) {
        this.name = name;
        this.lastName = lastName;
        this.cityResidenza = city;
        this.phone = phone;
        this.zip = zip;
        this.address = address;
        this.email = email;
        this.password = pass;
        this.username = user;
        this.doB = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCityResidenza(String cityResidenza) {
        this.cityResidenza = cityResidenza;
    }

    public String getCityResidenza() {
        return cityResidenza;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setDoB(Date doB) {
        this.doB = doB;
    }

    public Date getDoB() {
        return doB;
    }
}