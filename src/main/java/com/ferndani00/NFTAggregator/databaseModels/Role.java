package com.ferndani00.NFTAggregator.databaseModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
