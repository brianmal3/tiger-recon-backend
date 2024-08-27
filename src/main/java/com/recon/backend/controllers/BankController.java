package com.recon.backend.controllers;

import com.recon.backend.models.Bank;
import com.recon.backend.models.CustomResponse;
import com.recon.backend.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/findAll")
    public ResponseEntity<CustomResponse> findAll() {
        return new ResponseEntity<>(bankService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomResponse> findOne(@RequestParam String bankId) throws Exception {
        var bank = bankService.findOne(bankId);
        if (bank != null) {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByName")
    public ResponseEntity<CustomResponse> findByName(@RequestParam String name) throws Exception {
        var bank = bankService.findByName(name);
        if (bank != null) {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("createSouthAfricanBanks")
    public ResponseEntity<CustomResponse> createSouthAfricanBanks() throws IOException {
        var banks = bankService.createSouthAfricanBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createBank(@RequestBody Bank bank) throws IOException {
        //
        CustomResponse response = bankService.createBank(bank);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}