package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImp implements EmpresaService{

    @Autowired private EmpresaRepository empresaRepository;

    @Override
    public List<Empresa> ListaEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public Empresa empresaById(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Empresa empresa) {
        Empresa empresaEditar = empresaById(empresa.getId());
        if (empresaEditar != null) {
            empresaEditar.setEstado(empresa.getEstado());
            empresaEditar.setPlan(empresa.getPlan());
            empresaRepository.save(empresaEditar);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void Update(Empresa empresa) {
        empresaRepository.save(empresa);
    }
}
