package com.devflow.portal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalController {

    private String leaveStatus = "No Request";
    private String salaryStatus = "Pending";
    private String leaveDates = "N/A";
    private String salaryAmount = "$5,000"; // Added current salary

    @GetMapping("/")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "admin123".equals(password)) return "redirect:/admin";
        if ("emp1".equals(username) && "emp123".equals(password)) return "redirect:/employee";
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("leave", leaveStatus);
        model.addAttribute("salary", salaryStatus);
        model.addAttribute("dates", leaveDates);
        model.addAttribute("amount", salaryAmount);
        return "admin";
    }

    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("leave", leaveStatus);
        model.addAttribute("salary", salaryStatus);
        model.addAttribute("dates", leaveDates);
        model.addAttribute("amount", salaryAmount);
        return "employee";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@RequestParam String startDate, @RequestParam String endDate) {
        leaveStatus = "Pending Approval";
        leaveDates = startDate + " to " + endDate; // Stores the calendar dates
        return "redirect:/employee";
    }

    @PostMapping("/approve-leave")
    public String approveLeave() {
        leaveStatus = "Approved";
        return "redirect:/admin";
    }

    @PostMapping("/pay-salary")
    public String paySalary() {
        salaryStatus = "Paid";
        return "redirect:/admin";
    }
}