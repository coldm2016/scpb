package com.scpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scpb.entity.ChainTicket;
import com.scpb.entity.Enterprise;
import com.scpb.service.ChainTicketService;
import com.scpb.service.EnterpriseService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class EnterpriseController {
	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;

	@Resource(name = "chainTicketService")
	private ChainTicketService chainTicketService;

	public void setChainTicketService(ChainTicketService chainTicketService) {
		this.chainTicketService = chainTicketService;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin() {
		return "login";
	}
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signout() {
		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String goRegister() {
		return "register";
	}

	@RequestMapping(value = "/managerInfo", method = RequestMethod.GET)
	public ModelAndView goManagerInfo(HttpSession session) {
		String id = (String) session.getAttribute("id");
		Enterprise enterprise = enterpriseService.getEnterpriseById(id);
		ModelAndView mav = new ModelAndView();
		if (enterprise != null)
			mav.addObject(enterprise);
		mav.setViewName("modifyInfo");
		return mav;
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public ModelAndView login(String id, String pwd, HttpSession session) {
		 Enterprise enterprise = enterpriseService.getEnterpriseById(id);
		// System.out.println(id);
		 ModelAndView mav = new ModelAndView();
		 if (enterprise == null) {
		 mav.addObject("message", "登录失败 !!");
		 mav.setViewName("fail");
		 return mav;
		 } else {
		 if (pwd.equals(enterprise.getPwd())) {
		 // 密码匹配成功
		 mav.addObject("id", enterprise.getId());
		 //判断企业类型
		 if(enterprise.getType()==1)
		 mav.setViewName("coreEnterprise/home");
		 else if(enterprise.getType()==2)
		 mav.setViewName("memberEnterprise/home");
		 else if(enterprise.getType()==3)
		 mav.setViewName("supplier/home");
		 else if(enterprise.getType()==4)
		 mav.setViewName("factor/home");
		 session.setAttribute("id",id);
		 return mav;
		 } else {
		 mav.addObject("message", "password wrong !!");
		 mav.setViewName("login");
		 return mav;
		 }
		
		 }
	}

	@RequestMapping(value = "/registerFin", method = RequestMethod.POST)
	public ModelAndView register(String id, String account, String pwd, String bank, String name, String UCC,
			String LPC, int type) {
		Enterprise enterprise = new Enterprise(id, account, pwd, bank, name, UCC, LPC, type);
		enterpriseService.addEnterprise(enterprise);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("account", account);
		mav.addObject("pwd", pwd);
		mav.addObject("bank", bank);
		mav.addObject("name", name);
		mav.addObject("UCC", UCC);
		mav.addObject("LPC", LPC);
		mav.addObject("type", type);
		mav.setViewName("registerFin");
		return mav;
	}

	@RequestMapping(value = "/chainTickets", method = RequestMethod.GET)
	public ModelAndView queryChainTicketsByEnterpriseId(HttpSession session) {
		String id = (String) session.getAttribute("id");
		// 测试是否输出
//		System.out.println("企业ID:" + id);

		List<ChainTicket> chainTicketList = chainTicketService.getChainTicketsByEnterpriseId(id);
		// 测试是否获取查询结果集
//		System.out.println(Arrays.toString(chainTicketList.toArray()));

		ModelAndView mav = new ModelAndView();
		mav.addObject("chainTicketList", chainTicketList);

		mav.setViewName("chainTickets");
		return mav;
	}

	@RequestMapping(value = "/modifyInfo", method = RequestMethod.GET)
	public ModelAndView modifyInfo(HttpSession session) {
		String id = (String) session.getAttribute("id");
		Enterprise enterprise = enterpriseService.getEnterpriseById(id);
		ModelAndView mav = new ModelAndView();
		if (enterprise != null)
			mav.addObject(enterprise);
		mav.setViewName("modifyInfo");
		return mav;
	}

	@RequestMapping(value = "/execModifyInfo", method = RequestMethod.GET)
	public ModelAndView execmodifyInfo(String id, String account, String pwd, String bank, String name, String UCC,
			String LPC, int type) {
		Enterprise enterprise = new Enterprise(id, account, pwd, bank, name, UCC, LPC, type);
		enterpriseService.updateEnterprise(enterprise);
		ModelAndView mav = new ModelAndView();
		mav.addObject(enterprise);
		mav.setViewName("modifyInfo");
		return mav;
	}
}
