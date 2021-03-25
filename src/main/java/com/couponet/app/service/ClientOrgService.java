package com.couponet.app.service;

import com.couponet.app.model.ClientOrg;
import com.couponet.app.repo.ClientOrgRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientOrgService {

    private final ClientOrgRepo clientOrgRepo;

    @Autowired
    public ClientOrgService(ClientOrgRepo clientOrgRepo) {
        this.clientOrgRepo = clientOrgRepo;
    }

    public List<ClientOrg> findAllClientOrgs(){
        return clientOrgRepo.findAll();
    }

    public ClientOrg findClientOrgById(int clientOrgId){
        ClientOrg tempClientOrg = null;
        Optional<ClientOrg> foundClientOrg = clientOrgRepo.findById(clientOrgId);
        if (foundClientOrg.isPresent()){
            tempClientOrg = foundClientOrg.get();
        }
        return tempClientOrg;
    }

    public boolean deleteClientOrgById(int clientOrgId){
        boolean control = false;
        if (clientOrgRepo.existsById(clientOrgId)){
            clientOrgRepo.deleteById(clientOrgId);
            control = true;
        }
        return control;
    }

    public ClientOrg createClientOrg(ClientOrg newClientOrg){
        ClientOrg tempClientOrg = null;
        if (!clientOrgRepo.existsById(newClientOrg.getId())){
            tempClientOrg = clientOrgRepo.save(newClientOrg);
        }
        return tempClientOrg;
    }

    public ClientOrg updateClientOrg(ClientOrg updateClientOrg){
        ClientOrg tempClientOrg = null;
        Optional<ClientOrg> foundClientOrg = clientOrgRepo.findById(updateClientOrg.getId());
        if (foundClientOrg.isPresent()){
            updateClientOrg.setRegistrationDateTime(foundClientOrg.get().getRegistrationDateTime());
            tempClientOrg = clientOrgRepo.save(updateClientOrg);
        }
        return tempClientOrg;
    }


}
