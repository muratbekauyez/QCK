package com.example.qck.manual;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManualService {

    private final ManualRepository manualRepository;

    public List<Manual> getAllManuals(){
        return manualRepository.findAll();
    }

    public void saveManual(Manual manual){
        manualRepository.save(manual);
    }


    public Manual getManualById(Long id){
        Optional<Manual> manual = manualRepository.findById(id);
        if(manual.isEmpty()) throw new RuntimeException("Manual not found for id :" + id);
        return manual.get();
    }

    public void updateManual(Manual manual) {
        manualRepository.save(manual);
    }

    public void deleteById(Long id) {
        manualRepository.deleteById(id);
    }
}
