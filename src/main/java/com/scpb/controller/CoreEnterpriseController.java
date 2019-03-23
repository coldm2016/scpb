package com.scpb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.scpb.entity.TradeInformation;
import com.scpb.service.ChainTicketService;
import com.scpb.service.CoreEnterpriseService;
import com.scpb.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scpb.entity.ChainTicket;
import com.scpb.entity.Enterprise;
import com.scpb.service.EnterpriseService;

@Controller
@RequestMapping("/coreEnterprise")
public class CoreEnterpriseController {
    @Resource(name = "chainTicketService")
    private ChainTicketService chainTicketService;

    @Resource(name = "tradeService")
    private TradeService tradeService;

    @Resource(name = "coreEnterpriseService")
    private CoreEnterpriseService coreEnterpriseService;

    public void setChainTicketService(ChainTicketService chainTicketService) {
        this.chainTicketService = chainTicketService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/coreEnterpriseDrawSuccess")
    public ModelAndView drawCT(String applicant, String amount,
                               HttpSession session,String deadline, String tradeRemark) {

        String drawEnterprise = (String) session.getAttribute("id");
        ChainTicket chainTicket = new ChainTicket(1,amount, deadline, drawEnterprise, drawEnterprise);
        chainTicketService.addChainTicket(chainTicket);
        double limit = Double.valueOf(coreEnterpriseService.getLimitById(drawEnterprise));
        String newLimit = Double.toString(limit-Double.valueOf(amount));
        coreEnterpriseService.modifyLimitById(newLimit,drawEnterprise);

        ChainTicket receiveCT = chainTicket;
        ChainTicket remainCT = new ChainTicket(3,"0", chainTicket.getDeadline(),
                drawEnterprise, drawEnterprise);
        TradeInformation tradeInformation = new TradeInformation(drawEnterprise,applicant,amount,
                tradeRemark,chainTicket.getId(),receiveCT.getId(),remainCT.getId());
        tradeService.addTradeInformation(tradeInformation);
        tradeService.modifyTradeInfStateById(tradeInformation.getId(),2);

        ModelAndView mav = new ModelAndView();
        mav.addObject(chainTicket);
        mav.setViewName("coreEnterpriseDrawCTSuccess");
        return mav;
    }
    
	@RequestMapping("/allocateLimit")
	 public ModelAndView allocate(String id, String limit) {
		coreEnterpriseService.modifyLimitById(limit, id);
		String result = "额度设置成功";
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("Limit");
		return mav;
	}
	
	@RequestMapping("/queryLimit")
	 public ModelAndView queryLimit(String id) {
		String result = coreEnterpriseService.getLimitById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("Limit");
		return mav;
	}
}
