package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Entity.Enum.EstadoSubcripcion;
import com.panel.OrdexStep.Entity.Suscripcion;
import com.panel.OrdexStep.Repository.SuscripcionRepository;
import com.panel.OrdexStep.security.ServiceEmail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuspcripcionServiceImp implements SuscripcionService{

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private ServiceEmail serviceEmail;


    @Override
    public void NotificacionSuscripcion(LocalDate fechaVencimiento) {
        LocalDate fechaAviso= fechaVencimiento.plusDays(3);

        List<Suscripcion> NotificarCuentas= suscripcionRepository.
                findSubscriptionsToSuspend(fechaAviso,EstadoSubcripcion.ACTIVO);

        NotificarCuentas.forEach(notificar ->{

            List<String> destino =  List.of(notificar.getEmpresa().getCorreoEmpresa());
            Map<String, Object> modelos = new HashMap<>();
            modelos.put("nombre", notificar.getEmpresa().getNombre());
            modelos.put("plan", notificar.getPlan().getNombre());
            modelos.put("fecha", notificar.getFechaFin().toString());

            serviceEmail.sendEmail(destino,"Suspencion de "+ notificar.getPlan().getNombre() +" en tres dias",
                    modelos,"AvisosPlanes/suspencionPlan",true);
            System.out.println("enviado a " + destino);
        });
    }

    @Override
    @Transactional
    public void cancelarSuscripcion(LocalDate fechaVencimiento) {

        List<Suscripcion> expired = suscripcionRepository
                .findSubscriptionsToSuspend(fechaVencimiento, EstadoSubcripcion.ACTIVO);

        expired.forEach(suscripcion -> {
            suscripcion.setEstadoSub(EstadoSubcripcion.SUSPENDIDO);

            List<String>destinos = List.of(suscripcion.getEmpresa().getCorreoEmpresa());
            Map<String, Object> modelos = new HashMap<>();
            modelos.put("nombre", suscripcion.getEmpresa().getRazonSocial());
            modelos.put("plan", suscripcion.getPlan().getNombre());
            modelos.put("fecha", suscripcion.getFechaFin().toString());
            modelos.put("estado",suscripcion.getEstadoSub().name());

            serviceEmail.sendEmail(destinos,"Suscripción cancelada ❌",
                    modelos,"AvisosPlanes/desactivarPlan",true);
        });
        suscripcionRepository.saveAll(expired);


    }

    @Override
    public Suscripcion ObtenerEmpresaAndSuscripciones(Long id) {
        return suscripcionRepository.findFirstByEmpresaIdOrderByFechaFinDesc(id).orElse(null);
    }

    @Override
    public void saveSuscripcion(Suscripcion suscripcion) {
        suscripcionRepository.save(suscripcion);

        List<String> email = List.of(suscripcion.getEmpresa().getCorreoEmpresa());
        Map<String, Object> modelos = new HashMap<>();
        modelos.put("nombre", suscripcion.getEmpresa().getRazonSocial());
        modelos.put("plan", suscripcion.getPlan().getNombre());
        modelos.put("fechaInicio", suscripcion.getFechaInicio().toString());
        modelos.put("precio", suscripcion.getPlan().getPrecio());
        modelos.put("fecha", suscripcion.getFechaFin().toString());

        serviceEmail.sendEmail(email,"Suscripcion a "+ suscripcion.getPlan().getNombre(),
                modelos,"AvisosPlanes/suscripcionPlan",false);


    }

    @Override
    public void verificarSuscripcion(Empresa empresa) {

    }
}
