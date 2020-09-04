package com.webapp.rentalapp.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private Collection<Client> users;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Collection<Client> getUsers() { return users; }

    public void setUsers(Collection<Client> users) { this.users = users; }

}