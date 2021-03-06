package com.scpb.service;

import java.util.List;

import com.scpb.entity.ChainTicket;

public interface ChainTicketService {

    ChainTicket getChainTicketById(String id);

    void addChainTicket(ChainTicket chainTicket);

    void changeCTState(ChainTicket chainTicket);

    void modifyCTStateById(int state, String id);
    
    List<ChainTicket> getChainTicketsByEnterpriseId(String ownerId);
    
    void modifyCTOwnerIdById(String secondParty, String receiveCT);

	List<ChainTicket> getUncheckedCTsByCEId(String coreEnterpriseId);

	void setStateByChainTicketId(String chainTicketId, int state);

}
