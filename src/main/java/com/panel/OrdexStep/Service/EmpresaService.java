package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Empresa;

import java.util.List;

public interface EmpresaService {

    List<Empresa>ListaEmpresas();
    Empresa empresaById(Long id);
    void save(Empresa empresa);
    void delete(Long id);
    void Update(Empresa empresa);
}
