package com.example.helptest.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Permissions  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String permission;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER ,cascade = {CascadeType.ALL})
    private Set<ApplicationUserRole> applicationUserPermissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Set<ApplicationUserRole> getApplicationUserPermissions() {
        return applicationUserPermissions;
    }

    public void setApplicationUserPermissions(Set<ApplicationUserRole> applicationUserPermissions) {
        this.applicationUserPermissions = applicationUserPermissions;
    }


}
