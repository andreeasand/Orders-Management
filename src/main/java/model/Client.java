package model;

public class Client {

    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;



    public Client(int i, String n, String m, String a, String p)
    {
        id=i;
        name=n;
        email=m;
        address=a;
        phone=p;

    }

    public Client( String n, String m, String a, String p)
    {
        name=n;
        email=m;
        address=a;
        phone=p;

    }


    public Client() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String mail) {
        this.email = mail;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString()
    {
        return "Client: "+id+"  "+name+"  "+email+"   "+address+"   "+phone+" \n";

    }


}
