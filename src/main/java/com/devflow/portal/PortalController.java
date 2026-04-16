package com.devflow.portal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 1. Move the class outside and make it public so Thymeleaf can "see" it
class Employee {
    public String name = "John Doe";
    public String initials = "JD";
    public String role = "Senior DevOps Engineer";
    public String salary = "$5,500";
    public String leaveStatus = "No Request";
    public String salaryStatus = "Pending";
    public String leaveDates = "N/A";
    public String leaveReason = "N/A";

    // Standard getters (Thymeleaf needs these to read the variables)
    public String getName() { return name; }
    public String getInitials() { return initials; }
    public String getRole() { return role; }
    public String getSalary() { return salary; }
    public String getLeaveStatus() { return leaveStatus; }
    public String getSalaryStatus() { return salaryStatus; }
    public String getLeaveDates() { return leaveDates; }
    public String getLeaveReason() { return leaveReason; }
}

@Controller
public class PortalController {

    private Employee emp1 = new Employee();
    private static List<String> logs = new ArrayList<>();

    private void addLog(String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        logs.add("[" + time + "] " + message);
        if (logs.size() > 6) logs.remove(0);
    }

    @GetMapping("/")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            addLog("Admin session started.");
            return "redirect:/admin";
        }
        if ("emp1".equals(username) && "emp123".equals(password)) {
            addLog("Employee " + emp1.initials + " logged in.");
            return "redirect:/employee";
        }
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("emp", emp1);
        model.addAttribute("logs", logs);
        try {
            model.addAttribute("containerId", InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            model.addAttribute("containerId", "unknown-host");
        }
        return "admin";
    }

    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("emp", emp1);
        return "employee";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String reason) {
        emp1.leaveStatus = "Pending Approval";
        emp1.leaveDates = startDate + " to " + endDate;
        emp1.leaveReason = reason;
        addLog("Leave request submitted by " + emp1.initials);
        return "redirect:/employee";
    }

    @PostMapping("/approve-leave")
    public String approveLeave() {
        emp1.leaveStatus = "Approved";
        addLog("Leave approved for " + emp1.initials);
        return "redirect:/admin";
    }

    @PostMapping("/reject-leave")
    public String rejectLeave() {
        emp1.leaveStatus = "Rejected";
        addLog("Leave rejected for " + emp1.initials);
        return "redirect:/admin";
    }

    @PostMapping("/pay-salary")
    public String paySalary() {
        emp1.salaryStatus = "Paid";
        addLog("Salary paid to " + emp1.initials);
        return "redirect:/admin";
    }
}