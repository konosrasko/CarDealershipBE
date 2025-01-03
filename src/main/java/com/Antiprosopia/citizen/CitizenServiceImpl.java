package com.Antiprosopia.citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Override
    public CitizenDTO createCitizen(CitizenDTO citizenDTO) {
        Citizen citizen = new Citizen();
        citizen.setAfm(citizenDTO.getAfm());
        citizen.setFirstName(citizenDTO.getFirstName());
        citizen.setLastName(citizenDTO.getLastName());
        citizen.setEmail(citizenDTO.getEmail());
        citizen.setPassword(citizenDTO.getPassword());
        Citizen savedCitizen = citizenRepository.save(citizen);
        return mapToDTO(savedCitizen);
    }

    @Override
    public CitizenDTO updateCitizen(Integer citizenId, CitizenDTO citizenDTO) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
        citizen.setAfm(citizenDTO.getAfm());
        citizen.setFirstName(citizenDTO.getFirstName());
        citizen.setLastName(citizenDTO.getLastName());
        citizen.setEmail(citizenDTO.getEmail());
        citizen.setPassword(citizenDTO.getPassword());
        Citizen updatedCitizen = citizenRepository.save(citizen);
        return mapToDTO(updatedCitizen);
    }

    @Override
    public CitizenDTO getCitizenById(Integer citizenId) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
        return mapToDTO(citizen);
    }

    @Override
    public List<CitizenDTO> getAllCitizens() {
        return citizenRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCitizen(Integer citizenId) {
        citizenRepository.deleteById(citizenId);
    }

    private CitizenDTO mapToDTO(Citizen citizen) {
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCitizenId(citizen.getCitizenId());
        citizenDTO.setAfm(citizen.getAfm());
        citizenDTO.setFirstName(citizen.getFirstName());
        citizenDTO.setLastName(citizen.getLastName());
        citizenDTO.setEmail(citizen.getEmail());
        citizenDTO.setPassword(citizen.getPassword());
        return citizenDTO;
    }
}
