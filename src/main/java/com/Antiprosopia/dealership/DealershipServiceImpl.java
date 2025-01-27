package com.Antiprosopia.dealership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealershipServiceImpl implements DealershipService {

    @Autowired
    private DealershipRepository dealershipRepository;

    @Override
    public DealershipDTO createDealership(DealershipDTO dealershipDTO) {
        Dealership dealership = new Dealership();
        dealership.setAfm(dealershipDTO.getAfm());
        dealership.setName(dealershipDTO.getName());
        dealership.setOwner(dealershipDTO.getOwner());
        dealership.setPassword(dealershipDTO.getPassword());
        Dealership savedDealership = dealershipRepository.save(dealership);
        return mapToDTO(savedDealership);
    }

    @Override
    public DealershipDTO updateDealership(Integer dealershipId, DealershipDTO dealershipDTO) {
        Dealership dealership = dealershipRepository.findById(dealershipId)
                .orElseThrow(() -> new RuntimeException("Dealership not found"));
        dealership.setAfm(dealershipDTO.getAfm());
        dealership.setName(dealershipDTO.getName());
        dealership.setOwner(dealershipDTO.getOwner());
        dealership.setPassword(dealershipDTO.getPassword());
        Dealership updatedDealership = dealershipRepository.save(dealership);
        return mapToDTO(updatedDealership);
    }

    @Override
    public DealershipDTO getDealershipById(Integer dealershipId) {
        Dealership dealership = dealershipRepository.findById(dealershipId)
                .orElseThrow(() -> new RuntimeException("Dealership not found"));
        return mapToDTO(dealership);
    }

    @Override
    public List<DealershipDTO> getAllDealerships() {
        return dealershipRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDealership(Integer dealershipId) {
        dealershipRepository.deleteById(dealershipId);
    }

    @Override
    public Dealership findByAfm(String afm) {
        var dealership = dealershipRepository.findByAfm(afm);
        return dealership.orElse(null);
    }

    private DealershipDTO mapToDTO(Dealership dealership) {
        DealershipDTO dealershipDTO = new DealershipDTO();
        dealershipDTO.setDealershipId(dealership.getDealershipId());
        dealershipDTO.setAfm(dealership.getAfm());
        dealershipDTO.setName(dealership.getName());
        dealershipDTO.setOwner(dealership.getOwner());
        return dealershipDTO;
    }
}
