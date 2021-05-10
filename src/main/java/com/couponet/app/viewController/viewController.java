package com.couponet.app.viewController;

import com.couponet.app.model.Coupon;
import com.couponet.app.model.CouponForm;
import com.couponet.app.model.User;
import com.couponet.app.service.AssigmentService;
import com.couponet.app.service.CouponService;
import com.couponet.app.service.UserService;
import jdk.internal.icu.text.NormalizerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class viewController {

    private final UserService userService;
    private final CouponService couponService;
    private final AssigmentService assigmentService;

    @Autowired
    public viewController(UserService userService, CouponService couponService, AssigmentService assigmentService) {
        this.userService = userService;
        this.couponService = couponService;
        this.assigmentService = assigmentService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewHomePage(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLogin(@RequestParam(name = "registrationError", required = false, defaultValue = "false") boolean registrationError,
                            ModelMap modelMap){
        modelMap.addAttribute("newUser", new User());
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String receiveRegister(@ModelAttribute(name = "newUser") User user,
                                  ModelMap modelMap){

        user.setRole(User.Role.MANAGER);

        User savedUser = userService.createUser(user);
        if (savedUser != null){
            modelMap.clear();
            return "redirect:/panel/home";
        }
        else{
            return "redirect:/register?registrationError=true";
        }
    }

    @RequestMapping(value = "/panel/home", method = RequestMethod.GET)
    public String viewPanel(ModelMap modelMap,
                            @RequestParam(name = "errorDeletingCoupon", required = false, defaultValue = "false") boolean errorDeletingCoupon,
                            @RequestParam(name = "couponCreated", required = false, defaultValue = "false") boolean couponCreated){
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null){
            modelMap.addAttribute("newUser", new User());
            modelMap.addAttribute("newCoupon", new CouponForm());
            modelMap.addAttribute("couponList", couponService.findAllCouponsByOwnerId(loggedUser.getId()));

            // Error messages
            modelMap.addAttribute("errorDeletingCoupon", errorDeletingCoupon);
            modelMap.addAttribute("couponCreated", couponCreated);
            return "panel";
        }
        else{
            return "redirect:/?notAllowed";
        }

    }

    @RequestMapping(value = "/panel/home/coupon/delete", method = RequestMethod.POST)
    public String deleteCoupon(@RequestParam(name = "couponId") int couponId,
                               ModelMap modelMap){
        Coupon coupon = couponService.findCouponById(couponId);
        if (coupon != null){
            couponService.deleteCouponById(couponId);
            return "redirect:/panel/home";
        }
        else{
            return "redirect:/panel/home?errorDeletingCoupon=true";
        }
    }

    @RequestMapping(value = "/panel/home/coupon/create", method = RequestMethod.POST)
    public String createCoupon(@ModelAttribute(name = "newCoupon") CouponForm couponForm){
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null){
            Coupon newCoupon = new Coupon(couponForm.getPrice(),
                    couponForm.getStock(),
                    couponForm.getMaxPerUser(),
                    loggedUser);
            couponService.createCoupon(newCoupon);
            return "redirect:/panel/home?couponCreated=true";
        }
        else{
            return "redirect:/?notAlloed";
        }
    }

    @RequestMapping(value = "/panel/home/client/create", method = RequestMethod.POST)
    public String createClient(@ModelAttribute(name = "newUser") User newClient,
                               ModelMap modelMap){
        User foundUser = userService.findUserById(newClient.getId());
        if (foundUser == null){
            User savedUser = userService.createUser(newClient);
            return "redirect:/panel/home/client?clientId?=" + savedUser.getId();
        }
        else{
            return "redirect:/panel/home/client?clientId?=" + foundUser.getId();
        }
    }

    @RequestMapping(value = "/panel/home/client", method = RequestMethod.GET)
    public String viewClient(@RequestParam(name = "clientId") int clientId,
                             ModelMap modelMap){
        User client = userService.findUserById(clientId);
        if (client != null){
            modelMap.addAttribute("client", client);

        }

    }




}
