package ru.itis.javalab.plagiarism.app.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "tasks")
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
//    private List<Report> reports;

}
