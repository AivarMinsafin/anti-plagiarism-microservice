package ru.itis.javalab.plagiarism.app.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"tasks", "reports"})
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "student")
    private List<Task> tasks;
    @OneToMany(mappedBy = "student")
    private List<Report> reports;
//    private List<Report> reports;

}
