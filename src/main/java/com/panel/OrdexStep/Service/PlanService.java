package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Plan;

import java.util.List;

public interface PlanService {

    Plan PlanById(Long id);
    List<Plan>ListaPlanes();
}
