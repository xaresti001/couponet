package com.couponet.app.controller;

import com.couponet.app.model.Assigment;
import com.couponet.app.model.ClientOrg;
import com.couponet.app.model.Coupon;
import com.couponet.app.model.User;
import com.couponet.app.service.AssigmentService;
import com.couponet.app.service.ClientOrgService;
import com.couponet.app.service.CouponService;
import com.couponet.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class viewController {

    private final AssigmentService assigmentService;
    private final UserService userService;
    private final ClientOrgService clientOrgService;
    private final CouponService couponService;

    @Autowired
    public viewController(AssigmentService assigmentService, UserService userService, ClientOrgService clientOrgService, CouponService couponService) {
        this.assigmentService = assigmentService;
        this.userService = userService;
        this.clientOrgService = clientOrgService;
        this.couponService = couponService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    // NULL object may be obtained... This must be fixed
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public String userList(ModelMap modelMap){
        List<User> allUsers = userService.findAllUsers();
        modelMap.clear();
        modelMap.addAttribute("allUsers", allUsers);
        return "userList";
    }

    // NULL object may be obtained... This must be fixed
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(@ModelAttribute("userId") int userId, ModelMap modelMap){
        User user = userService.findUserById(userId);
        List<Assigment> userAssigments = assigmentService.findAssigmentsByUserId(userId);
        modelMap.clear();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("userAssigments", userAssigments);
        return "user";
    }

    // NULL object may be obtained... This must be fixed
    @RequestMapping(value = "/org/all", method = RequestMethod.GET)
    public String orgList(ModelMap modelMap){
        List<ClientOrg> allClientOrgs = clientOrgService.findAllClientOrgs();
        modelMap.clear();
        modelMap.addAttribute("allClientOrgs", allClientOrgs);
        return "orgList";
    }

    // NULL object may be obtained... This must be fixed
    @RequestMapping(value = "/org", method = RequestMethod.GET)
    public String org (@ModelAttribute("clientOrgId") int clientOrgId, ModelMap modelMap){
        ClientOrg clientOrg = clientOrgService.findClientOrgById(clientOrgId);
        List<Coupon> clientOrgCoupons = couponService.findCouponsByClientOrgId(clientOrgId);
        modelMap.clear();
        modelMap.addAttribute("clientOrg", clientOrg);
        modelMap.addAttribute("clientOrgCoupons", clientOrgCoupons);
        return "org";
    }

    @RequestMapping(value = "/org/register", method = RequestMethod.GET)
    public String registerOrg(ModelMap modelMap){
        modelMap.addAttribute("orgForm", new ClientOrg()); // Send empty object
        return "registerOrg";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String registerUser(ModelMap modelMap){
        modelMap.addAttribute("userForm", new User()); // Send empty object
        return "registerUser";
    }

    // NULL object may be obtained... This must be fixed
    @RequestMapping(value = "/user/register/send", method = RequestMethod.POST)
    public String sendRegisterUser(@ModelAttribute User user, ModelMap modelMap){
        userService.createUser(user);
        modelMap.clear(); // Compulsory view purge
        return "redirect:/user/register";
    }



}
