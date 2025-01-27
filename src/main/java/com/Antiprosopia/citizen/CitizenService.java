package com.Antiprosopia.citizen;

import java.util.List;

public interface CitizenService {
    CitizenDTO createCitizen(CitizenDTO citizenDTO);
    CitizenDTO updateCitizen(Integer citizenId, CitizenDTO citizenDTO);
    CitizenDTO getCitizenById(Integer citizenId);
    List<CitizenDTO> getAllCitizens();
    void deleteCitizen(Integer citizenId);
    Citizen findByAfm(String afm);
}
