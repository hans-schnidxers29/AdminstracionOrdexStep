package com.panel.OrdexStep.Repository;

import com.panel.OrdexStep.Entity.Enum.EstadoSubcripcion;
import com.panel.OrdexStep.Entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion,Long> {

    // Opción B: Buscar todos los vencidos (más seguro si falló el proceso ayer)
    @Query("SELECT s FROM Suscripcion s WHERE s.fechaFin <= :today AND s.estadoSub = :estado")
    List<Suscripcion>findSubscriptionsToSuspend(LocalDate today, EstadoSubcripcion estado);

    Optional<Suscripcion> findFirstByEmpresaIdOrderByFechaFinDesc(Long empresaId);

}

