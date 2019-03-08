package com.scpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scpb.entity.Enterprise;
import com.scpb.service.EnterpriseService;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;

@Controller
public class EnterpriseController {
	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;
	
	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView login(String id, String pwd) {
		Enterprise enterprise = enterpriseService.getEnterpriseById(id);
 
        ModelAndView mav = new ModelAndView();
        if (enterprise == null) {
        	mav.addObject("message", "登录失败 !!");
        	mav.setViewName("fail");
            return mav;
        } else {
        	if(pwd.equals(enterprise.getPwd())){
        		//密码匹配成功
            	mav.addObject("id", enterprise.getId());
            	mav.addObject("account", enterprise.getAccount());
            	mav.addObject("bank", enterprise.getBank());
            	mav.addObject("name", enterprise.getName());
            	mav.addObject("UCC", enterprise.getUCC());
            	mav.addObject("LPC", enterprise.getLPC());
            	mav.addObject("type", enterprise.getType());
                mav.setViewName("main");
                return mav;
        	}else{
        		mav.addObject("message", "password wrong !!");
        		mav.setViewName("main");
        		return mav;
        	}
        	
        }
    }
	
	@RequestMapping(value = "/registerFin", method = RequestMethod.GET)
    public ModelAndView register(String id,String account,String pwd,
								 String bank,String name,String UCC,String LPC,int type){
		Enterprise enterprise = new Enterprise(id,account,pwd,bank,name,UCC,LPC,type);
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
}
