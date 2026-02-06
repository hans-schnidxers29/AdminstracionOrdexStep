package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Entity.Enum.EstadoSubcripcion;
import com.panel.OrdexStep.Entity.Suscripcion;
import com.panel.OrdexStep.Repository.SuscripcionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuspcripcionServiceImp implements SuscripcionService{

    @Autowired
    private SuscripcionRepository suscripcionRepository;


    @Override
    public void NotificacionSuscripcion(LocalDate fechaVencimiento) {
        LocalDate fechaAviso= fechaVencimiento.plusDays(3);

        List<Suscripcion> NotificarCuentas= suscripcionRepository.
                findSubscriptionsToSuspend(fechaAviso,EstadoSubcripcion.ACTIVO);

        NotificarCuentas.forEach(notificar ->{
                System.out.println("Enviando correo a: " + notificar.getEmpresa().getNombre());
        });
    }

    @Override
    @Transactional
    public void cancelarSuscripcion(LocalDate fechaVencimiento) {

        List<Suscripcion> expired = suscripcionRepository
                .findSubscriptionsToSuspend(fechaVencimiento, EstadoSubcripcion.ACTIVO);

        expired.forEach(suscripcion -> suscripcion.
                setEstadoSub(EstadoSubcripcion.SUSPENDIDO));

        suscripcionRepository.saveAll(expired);

    }

    @Override
    public Suscripcion ObtenerEmpresaAndSuscripciones(Long id) {
        return suscripcionRepository.findFirstByEmpresaIdOrderByFechaFinDesc(id).orElse(null);
    }

    @Override
    public void saveSuscripcion(Suscripcion suscripcion) {
        suscripcionRepository.save(suscripcion);
    }

    @Override
    public void verificarSuscripcion(Empresa empresa) {

    }
}
