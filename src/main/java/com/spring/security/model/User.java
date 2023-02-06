package com.spring.security.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String student_number;
    @Column(length = 60)
    private String password;
    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;



    @OneToMany(mappedBy = "user")
    private Collection<ProfilePicture> profilePictures;

    @OneToMany(mappedBy = "user")
    private Collection<Post> Post;

    @OneToMany(mappedBy = "user")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Collection<Interaction> interactions;



    public String getEmail() {
        return email;
    }

    public void setEmail(String student_number) {
        this.email = student_number+"@keyaka.ul.ac.za";
    }

    public String getStudent_number() {
        return student_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", student_number='" + student_number + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", profilePictures=" + profilePictures +
                '}';
    }

    public List<ProfilePicture> getProfilePictures() {
        return (List<ProfilePicture>) profilePictures;
    }
}
