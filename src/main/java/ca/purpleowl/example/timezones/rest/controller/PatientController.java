package ca.purpleowl.example.timezones.rest.controller;

import ca.purpleowl.example.timezones.rest.model.PatientAsset;
import ca.purpleowl.example.timezones.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("patient")
@RestController
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List getPatients() {
        return patientService.retrieveAllPatients();
    }

    @PutMapping
    public PatientAsset savePatient(@RequestBody PatientAsset asset) {
        return patientService.savePatient(asset);
    }
}
