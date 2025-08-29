package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.Status;
import com.example.absen.repository.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    public Optional<Status> getStatusById(String idstatus) {
        return statusRepository.findById(idstatus);
    }

    public Status updateStatus(String idstatus, Status statusDetails) {
        Optional<Status> statusData = statusRepository.findById(idstatus);
        if (statusData.isPresent()) {
            Status updatedStatus = statusData.get();
            updatedStatus.setNamastatus(statusDetails.getNamastatus());
            return statusRepository.save(updatedStatus);
        }
        return null;
    }

    public void deleteStatus(String idstatus) {
        statusRepository.deleteById(idstatus);
    }
}