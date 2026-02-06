package com.panel.OrdexStep.Controllers;


import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Entity.Plan;
import com.panel.OrdexStep.Entity.Suscripcion;
import com.panel.OrdexStep.Service.EmpresaService;
import com.panel.OrdexStep.Service.PlanService;
import com.panel.OrdexStep.Service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PlanService planService;

    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping
    public List<Empresa> listar() {
        return empresaService.ListaEmpresas();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empresa> actualizar(@PathVariable Long id, @RequestBody Map<String,Object> updates) {

        // 1. Buscar la empresa o lanzar 404
        Empresa empresa = empresaService.empresaById(id);
        if (empresa == null) return ResponseEntity.noContent().build();

        // 2. Procesar el Plan (si existe en el body)
        if (updates.get("plan") instanceof Map<?, ?> planMap) {
            Object idRaw = planMap.get("id");
            if (idRaw != null) {
                Long planId = Long.valueOf(idRaw.toString());
                Plan plan = planService.PlanById(planId);
                if (plan != null) {
                    empresa.setPlan(plan);
                }
            }
        }

        // 3. Procesar el Estado (si existe en el body)
        if (updates.containsKey("estado")) {
            Object estadoRaw = updates.get("estado");
            if (estadoRaw instanceof Boolean) {
                empresa.setEstado((Boolean) estadoRaw);
            }
        }

        empresaService.save(empresa);
        return ResponseEntity.ok(empresa);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetalleSuscripcion(@PathVariable Long id) {
        Empresa empresa = empresaService.empresaById(id);

        // Buscamos la suscripción activa más reciente
        Suscripcion sub = suscripcionService.ObtenerEmpresaAndSuscripciones(id);

        Map<String, Object> response = new HashMap<>();
        response.put("nombre", empresa.getNombre());
        response.put("nit", empresa.getNit());

        if (sub != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), sub.getFechaFin());
            response.put("plan", sub.getPlan().getNombre());
            response.put("diasRestantes", dias);
            response.put("estado", sub.getEstadoSub());
        } else {
            response.put("diasRestantes", -1); // Sin suscripción activa
        }

        return ResponseEntity.ok(response);
    }
}