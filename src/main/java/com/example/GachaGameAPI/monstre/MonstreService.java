package com.example.GachaGameAPI.monstre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonstreService {

    @Autowired
    private MonstreRepository monstreRepository;

    public List<Monstre> getAllMonstres() {
        return monstreRepository.findAll();
    }

    public Monstre getMonstreById(String id) {
        return monstreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monstre introuvable"));
    }

    public Monstre createMonstre(Monstre monstre) {
        return monstreRepository.save(monstre);
    }

    public Monstre updateMonstre(String id, Monstre monstre) {
        monstre.setId(id);
        return monstreRepository.save(monstre);
    }

    public void deleteMonstre(String id) {
        monstreRepository.deleteById(id);
    }
}
