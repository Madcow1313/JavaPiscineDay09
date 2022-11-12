package edu.school21.sockets.models;

public class User {
    Long id;
    String name;
    String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
