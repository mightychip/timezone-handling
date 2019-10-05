package ca.purpleowl.example.timezones.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PatientAsset {
    private Long id;
    private String firstName;
    private String lastName;
    //This one stays Local because:
    //  a) There is no ZonedDate
    //  b) There is no logic in applying a timezone to Dates in most cases.
    private LocalDate dateOfBirth;

    //This is where we care about Timezone.  Now this becomes relevant data, but only where it meets the lens of the
    //user's perspective.  Until a user looks at the time, Time Zone is irrelevant.
    private ZonedDateTime lastVisit;
}
