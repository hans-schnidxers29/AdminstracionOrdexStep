package com.panel.OrdexStep.Controllers;

import com.panel.OrdexStep.Entity.Plan;
import com.panel.OrdexStep.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired private PlanService planService;

    @GetMapping
    public List<Plan> listar(){
        return planService.ListaPlanes();
    }
}
