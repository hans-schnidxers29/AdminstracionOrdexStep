package com.panel.OrdexStep.Controllers;

import com.panel.OrdexStep.DTOS.SuscripcionDTO;
import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Entity.Enum.EstadoPagoSuscripcion;
import com.panel.OrdexStep.Entity.Enum.EstadoSubcripcion;
import com.panel.OrdexStep.Entity.Enum.TipoSuscripcion;
import com.panel.OrdexStep.Entity.Plan;
import com.panel.OrdexStep.Entity.Suscripcion;
import com.panel.OrdexStep.Service.EmpresaService;
import com.panel.OrdexStep.Service.PlanService;
import com.panel.OrdexStep.Service.SuscripcionService;
import com.panel.OrdexStep.security.ComponentTask;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suscripcion")
@RequiredArgsConstructor
public class SuscripcionController {

    private final ComponentTask componentTask;

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private PlanService planService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public ResponseEntity<?> suscripcion(){
        componentTask.SuscripcionVerificar();
        return ResponseEntity.ok("Tarea ejecutada manualmente");
    }


    @PostMapping("/crear")
    public ResponseEntity<?>CrearSuscripcion(@RequestBody SuscripcionDTO dto){
        try{
            Empresa empresa = empresaService.empresaById(dto.getEmpresaId());
            Plan plan = planService.PlanById(dto.getPlanId());

            empresa.setEstado(dto.getActivo());
            empresa.setPlan(plan);
            empresaService.save(empresa);

            Suscripcion nuevaSub = new Suscripcion();
            nuevaSub.setEmpresa(empresa);
            nuevaSub.setPlan(plan);
            nuevaSub.setFechaInicio(dto.getFechaInicio());
            
            TipoSuscripcion tipo = TipoSuscripcion.valueOf(dto.getTipoSuscripcion());
            nuevaSub.setTipoSuscripcion(tipo);

            if (tipo.equals(TipoSuscripcion.valueOf("PRUEBAS"))){
                nuevaSub.setFechaFin(dto.getFechaFin());
            }else {
                nuevaSub.setFechaFin(tipo.calcularFechaVencimiento(dto.getFechaInicio()));
            }
            
            BigDecimal monto = plan.getPrecio().multiply(new BigDecimal(tipo.getMeses()));
            nuevaSub.setMontoPagado(monto);
            
            nuevaSub.setEstadoSub(EstadoSubcripcion.ACTIVO);
            nuevaSub.setEstadoPago(EstadoPagoSuscripcion.PAGADO);
            suscripcionService.saveSuscripcion(nuevaSub);

            return ResponseEntity.ok("Suscripción procesada exitosamente");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());

        }
    }
}
