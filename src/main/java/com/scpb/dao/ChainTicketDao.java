package com.scpb.dao;

import java.util.List;

import com.scpb.entity.ChainTicket;
import org.apache.ibatis.annotations.Param;

public interface ChainTicketDao {

	void insertChainTicket(ChainTicket chainTicket);
	
	ChainTicket selectChainTicketById(String id);

    void updateCTState(ChainTicket chainTicket);

    void updateCTStateById(@Param("state") int state, @Param("id") String id);

    //查找某企业的所有链票
    List<ChainTicket> selectChainTicketsByEnterpriseId(String ownerId);
    
    void updateOwnerById(@Param("ownerId") String ownerId, @Param("id") String id);
    
    //查询核心企业的所有成员企业开具的未审核的链票
    List<ChainTicket> selectUncheckedCTsByCEId(String coreEnterpriseId);
}
