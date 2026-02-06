package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Plan;
import com.panel.OrdexStep.Repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImp implements PlanService{

    @Autowired
    private PlanRepository planRepository;


    @Override
    public Plan PlanById(Long id) {
        return planRepository.findById(id).orElse(null);
    }

    @Override
    public List<Plan> ListaPlanes() {
        return planRepository.findAll();
    }
}
