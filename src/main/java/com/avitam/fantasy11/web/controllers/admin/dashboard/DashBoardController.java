package com.avitam.fantasy11.web.controllers.admin.dashboard;


import com.avitam.fantasy11.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
public class DashBoardController {

    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MatchesRepository matchesRepository;

    @GetMapping
    public String getDashBoardDatas(Model model) {

        List<Deposits> allDeposits = depositsRepository.findAll();
        List<Deposits> pendingDeposits = depositsRepository.findByDepositStatus("Pending");
        List<Deposits> approvedDeposits = depositsRepository.findByDepositStatus("Approved");

        int totalDeposits = allDeposits.size();
        int pendingSize = pendingDeposits.size();
        int approvedSize = approvedDeposits.size();

        model.addAttribute("totalDeposits", totalDeposits);
        model.addAttribute("pendingDeposits", pendingSize);
        model.addAttribute("approvedDeposits", approvedSize);

        List<Matches> liveMatches=matchesRepository.findByEvent("Live");

        return "dashboard/dashboards";
    }

}