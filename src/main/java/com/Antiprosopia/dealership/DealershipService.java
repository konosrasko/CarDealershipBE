package com.Antiprosopia.dealership;

import java.util.List;

public interface DealershipService {
    DealershipDTO createDealership(DealershipDTO dealershipDTO);
    DealershipDTO updateDealership(Integer dealershipId, DealershipDTO dealershipDTO);
    DealershipDTO getDealershipById(Integer dealershipId);
    List<DealershipDTO> getAllDealerships();
    void deleteDealership(Integer dealershipId);
    Dealership findByAfm(String afm);
}
