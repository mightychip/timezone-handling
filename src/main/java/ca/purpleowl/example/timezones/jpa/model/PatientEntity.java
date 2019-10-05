package ca.purpleowl.example.timezones.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    //ZonedDate objects don't exist and logically probably shouldn't.  In most cases, when considering time at the
    //granularity of a calendar date, Time Zone is not relevant.
    @Column
    private LocalDate dateOfBirth;

    //Notice we use LocalDateTime here.  We store in UTC (the no-time-zone time zone), but ultimately store in the
    //Time Zone local to the application.  It is easier to ensure this is always UTC.
    @Column
    private LocalDateTime lastVisit;
}
