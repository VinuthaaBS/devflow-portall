package com.devflow.portal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String leaveReason = "N/A";
    private String salaryAmount = "$5,500";
    private String employeeRole = "Lead DevOps Engineer";

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
        addCommonAttributes(model);
        model.addAttribute("uptime", "99.98%");
        model.addAttribute("lastBuild", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return "admin";
    }

    @GetMapping("/employee")
    public String employee(Model model) {
        addCommonAttributes(model);
        return "employee";
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("leave", leaveStatus);
        model.addAttribute("salary", salaryStatus);
        model.addAttribute("dates", leaveDates);
        model.addAttribute("reason", leaveReason);
        model.addAttribute("amount", salaryAmount);
        model.addAttribute("role", employeeRole);
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String reason) {
        leaveStatus = "Pending Approval";
        leaveDates = startDate + " to " + endDate;
        leaveReason = reason;
        return "redirect:/employee";
    }

    @PostMapping("/approve-leave")
    public String approveLeave() {
        leaveStatus = "Approved";
        return "redirect:/admin";
    }

    @PostMapping("/reject-leave")
    public String rejectLeave() {
        leaveStatus = "Rejected";
        return "redirect:/admin";
    }

    @PostMapping("/pay-salary")
    public String paySalary() {
        salaryStatus = "Paid";
        return "redirect:/admin";
    }
}