package org.example.model.user;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
    )
    private Membership membership;

}
