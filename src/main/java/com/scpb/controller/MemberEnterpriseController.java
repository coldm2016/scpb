package com.scpb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.scpb.entity.TradeInformation;
import com.scpb.service.ChainTicketService;
import com.scpb.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scpb.entity.ChainTicket;
import com.scpb.entity.Enterprise;
import com.scpb.service.EnterpriseService;
import com.scpb.service.MemberEnterpriseService;

@Controller
@RequestMapping("/memberEnterprise")
public class MemberEnterpriseController {
    @Resource(name = "chainTicketService")
    private ChainTicketService chainTicketService;

    @Resource(name = "tradeService")
    private TradeService tradeService;

    @Resource(name = "memberEnterpriseService")
    private MemberEnterpriseService memberEnterpriseService;

    public void setChainTicketService(ChainTicketService chainTicketService) {
        this.chainTicketService = chainTicketService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping(value = "/goDrawCT", method = RequestMethod.GET)
    public String goMemberEnterpriseDrawCT(){
        return "memberEnterprise/drawCT";
    }
    @RequestMapping("/drawCT")
    public ModelAndView drawCT(String drawEnterprise, String applicant, String amount,
                               String deadline, String tradeRemark) {
    	//生成处于审核状态的链票，statue为0
        ChainTicket chainTicket = new ChainTicket(amount, deadline, drawEnterprise, drawEnterprise);
        chainTicketService.addChainTicket(chainTicket);
        //修改开具链票后生成的新的额度
        double limit = Double.valueOf(memberEnterpriseService.getLimitById(drawEnterprise));
        String newLimit = Double.toString(limit-Double.valueOf(amount));
        memberEnterpriseService.modifyLimitById(newLimit,drawEnterprise);
        //生成新的交易记录，状态为0--审核中
        TradeInformation tradeInformation = new TradeInformation(drawEnterprise,applicant,amount,
                tradeRemark,chainTicket.getId(),chainTicket.getId(),"0");
        tradeService.addPartTradeInformation(tradeInformation);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject(chainTicket);
        mav.setViewName("memberEnterprise/drawCTSuccess");
        return mav;
    }
    
}
