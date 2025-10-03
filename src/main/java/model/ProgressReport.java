package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "progress_reports")
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;
    private String achievements;
    private String areasForImprovement;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Version // For optimistic locking
    private long version;

    // Constructors, Getters, and Setters
}