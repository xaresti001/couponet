package com.couponet.app.viewController;

import com.couponet.app.model.Assigment;
import com.couponet.app.model.Coupon;
import com.couponet.app.model.CouponForm;
import com.couponet.app.model.User;
import com.couponet.app.service.AssigmentService;
import com.couponet.app.service.CouponService;
import com.couponet.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        User loggedUser = userService.getLoggedUser();
        if (loggedUser == null){
            modelMap.addAttribute("newUser", new User());
            modelMap.addAttribute("registrationError", registrationError);
            return "login";
        }
        else{
            return "redirect:/panel/home";
        }

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
                            @RequestParam(name = "assigmentDeleted", required = false, defaultValue = "false") boolean assigmentDeleted,
                            @RequestParam(name = "couponDeleted", required = false, defaultValue = "false") boolean couponDeleted,
                            @RequestParam(name = "errorCreatingAssigment", required = false, defaultValue = "false") boolean errorCreatingAssigment,
                            @RequestParam(name = "assigmentCreated", required = false, defaultValue = "false") boolean assigmentCreated,
                            @RequestParam(name = "errorDeletingCoupon", required = false, defaultValue = "false") boolean errorDeletingCoupon,
                            @RequestParam(name = "errorDeletingAssigment", required = false, defaultValue = "false") boolean errorDeletingAssigment,
                            @RequestParam(name = "couponCreated", required = false, defaultValue = "false") boolean couponCreated,
                            @RequestParam(name = "clientSearch", required = false, defaultValue = "0") int clientId){
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null){
            modelMap.addAttribute("newUser", new User());
            modelMap.addAttribute("newCoupon", new CouponForm());
            modelMap.addAttribute("couponList", couponService.findAllCouponsByOwnerId(loggedUser.getId()));

            if (clientId == 0){
                modelMap.addAttribute("assigmentList", assigmentService.findAssigmentsByOwnerId(loggedUser.getId()));
            }
            else{
                modelMap.addAttribute("assigmentList", assigmentService.findAssigmentsByClientId(clientId));
            }

            // Error messages
            modelMap.addAttribute("assigmentDeleted", assigmentDeleted);
            modelMap.addAttribute("couponDeleted", couponDeleted);
            modelMap.addAttribute("errorCreatingAssigment", errorCreatingAssigment);
            modelMap.addAttribute("assigmentCreated", assigmentCreated);
            modelMap.addAttribute("errorDeletingCoupon", errorDeletingCoupon);
            modelMap.addAttribute("errorDeletingAssigment", errorDeletingAssigment);
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
        User loggedUser = userService.getLoggedUser();
        Coupon coupon = couponService.findCouponById(couponId);
        if (loggedUser != null && coupon != null){
            couponService.deleteCouponById(couponId);
            modelMap.clear();
            return "redirect:/panel/home?couponDeleted=true";
        }
        else{
            return "redirect:/panel/home?errorDeletingCoupon=true";
        }
    }

    @RequestMapping(value = "/panel/home/coupon/create", method = RequestMethod.POST)
    public String createCoupon(@ModelAttribute(name = "newCoupon") CouponForm couponForm,
                               ModelMap modelMap){
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null){
            Coupon newCoupon = new Coupon(couponForm.getPrice(),
                    couponForm.getStock(),
                    couponForm.getMaxPerUser(),
                    loggedUser);
            couponService.createCoupon(newCoupon);
            modelMap.clear();
            return "redirect:/panel/home?couponCreated=true";
        }
        else{
            return "redirect:/?notAllowed";
        }
    }

    @RequestMapping(value = "/panel/home/assigment/delete", method = RequestMethod.POST)
    public String deleteAssigment(@RequestParam(name = "assigmentId") int assigmentId,
                                  ModelMap modelMap){
        User loggedUser = userService.getLoggedUser();
        Assigment assigment = assigmentService.findAssigmentById(assigmentId);
        if (loggedUser != null && assigment != null){
            assigmentService.deleteAssigmentById(assigmentId);
            modelMap.clear();
            return "redirect:/panel/home?assigmentDeleted=true";
        }
        else{
            return "redirect:/panel/home?errorDeletingAssigment=true";
        }
    }

    @RequestMapping(value = "/panel/home/assigment/create", method = RequestMethod.POST)
    public String createAssigment(@RequestParam(name = "clientId") int clientId,
                                  @RequestParam(name = "couponId") int couponId,
                                  ModelMap modelMap){
        User loggedUser = userService.getLoggedUser();
        Coupon coupon = couponService.findCouponById(couponId);
        if (loggedUser != null && coupon != null){
            Assigment newAssigment = assigmentService.createAssigment(new Assigment(clientId, coupon));
            if (newAssigment != null){
                modelMap.clear();
                return "redirect:/panel/home?assigmentCreated=true";
            }else{
                return "redirect:/panel/home?errorCreatingAssigment=true";
            }
        }
        else{
            return "redirect:/?notAllowed";
        }
    }



}
