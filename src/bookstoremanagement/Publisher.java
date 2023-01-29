package bookstoremanagement;

import java.io.Serializable;

public class Publisher implements Serializable, Comparable<Publisher> {
    public static final long serialVersionUID = -3040096452457271695L;
    private String id;
    private String name;
    private String phone;

    public Publisher() {
        this.id = "";
        this.name = "";
        this.phone = "";
    }
    
    public Publisher(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Publisher{" + "id=" + id + ", name=" + name + ", phone=" + phone + '}';
    }

    @Override
    public int compareTo(Publisher o) {
        return this.name.compareTo(o.getName());
    }
    
    
}