package ca.purpleowl.example.timezones.service;

import ca.purpleowl.example.timezones.jpa.model.PatientEntity;
import ca.purpleowl.example.timezones.jpa.repository.PatientRepository;
import ca.purpleowl.example.timezones.rest.model.PatientAsset;
import ca.purpleowl.example.timezones.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService extends AbstractService<PatientEntity, PatientAsset> {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientAsset> retrieveAllPatients() {
        return patientRepository.findAll()
                                .stream()
                                .map(this::entityToAsset)
                                .collect(Collectors.toList());
    }

    public PatientAsset retrievePatientById(Long id) throws NotFoundException {
        return patientRepository.findById(id)
                                .map(this::entityToAsset)
                                .orElseThrow(() -> new NotFoundException("No PatientEntity found by provided ID!"));
    }

    public PatientAsset savePatient(PatientAsset asset) {
        PatientEntity entity;
        if(asset.getId() != null) {
            entity = fillEntityFieldsFromAsset(patientRepository.findById(asset.getId()).orElse(new PatientEntity()), asset);
        } else {
            entity = assetToEntity(asset);
        }

        //FIXME This may not handle disconnected entities properly...?  May be fine... may not be disconnected anyways.
        patientRepository.save(entity);

        return entityToAsset(entity);
    }

    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    private PatientEntity fillEntityFieldsFromAsset(PatientEntity entity, PatientAsset asset) {
        //We don't fill ID here... that gets filled on read if the entity exists... otherwise it's gonna be new.
        entity.setFirstName(asset.getFirstName());
        entity.setLastName(asset.getLastName());
        entity.setDateOfBirth(asset.getDateOfBirth());
        //Don't forget we have to drop down to UTC and then drop timezone.
        entity.setLastVisit(asset.getLastVisit().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());

        return entity;
    }

    @Override
    PatientAsset entityToAsset(PatientEntity entity) {
        return new PatientAsset(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth(),
                //This step to convert is necessary... this is where you can optionally apply the user's timezone to
                //respect their lens of perspective on time.  Alternately, the caller can use the provided Time Zone
                //information to make their own choice.  As a third option, you could accept an optional Time Zone
                //parameter to format the time to the user's wishes.
                entity.getLastVisit().atZone(ZoneId.systemDefault())
        );
    }

    @Override
    PatientEntity assetToEntity(PatientAsset asset) {
        return new PatientEntity(
                //FIXME I don't think the ID should be set here, either... need a custom constructor on PatientEntity.
                asset.getId(),
                asset.getFirstName(),
                asset.getLastName(),
                asset.getDateOfBirth(),
                //Here, we convert to UTC where the server lives, then drop the timezone.  We're in timezone agnostic
                //land.  Drop it like it's hot.  Leave the time travelling at the door.
                asset.getLastVisit().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}
