package com.example.helptest.model;


import com.example.helptest.auth.ApplicationUser;
import com.example.helptest.security.ApplicationUserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "active")
    private int active;

    @ManyToOne()
    @JoinColumn(name = "role", referencedColumnName = "role")
    private ApplicationUserRole role;

    @Column(name = "creator")
    private String creator;

    @Column(name = "access_token")
    private String access_token;

    @Column(name = "temp_token")
    private String temp_token;

    private boolean deleted = false;

//    @OneToMany(targetEntity = EatingPlace.class, mappedBy = "waiter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<EatingPlace> tables;

    public User() {
    }

    public User(String username,
                String password,
                String name,
                String lastname,
                int active,
                ApplicationUserRole role,
                String creator,
                String access_token,
                String temp_token,
                boolean deleted) {
        //this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.active = active;
        this.role = role;
        this.creator = creator;
        this.temp_token = temp_token;
        this.access_token = access_token;
        this.deleted = deleted;
    }

    public User(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    public void setRole(ApplicationUserRole role) {
        this.role = role;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String token) {
        this.access_token = token;
    }

    public String getTemp_token() {
        return temp_token;
    }

    public void setTemp_token(String temp_token) {
        this.temp_token = temp_token;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", active=" + active +
                ", creator='" + creator + '\'' +
                ", access_token='" + access_token + '\'' +
                ", temp_token='" + temp_token + '\'' +
                '}';
    }

    public ApplicationUser getApplicationUser() {
        return new ApplicationUser(this);
    }

//    public Set<EatingPlace> getTables() {
//        return tables;
//    }
//
//    public void setTables(Set<EatingPlace> tables) {
//        this.tables = tables;
//    }
}
