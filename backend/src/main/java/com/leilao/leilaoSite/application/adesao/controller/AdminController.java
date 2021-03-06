package com.leilao.leilaoSite.application.adesao.controller;

import java.util.List;

import com.leilao.leilaoSite.application.adesao.service.AdminService;
import com.leilao.leilaoSite.domain.leilao.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminServiceImpl;

    @GetMapping
    public ResponseEntity<List<UserModel>> pegarTodosOsAdmins() {
        List<UserModel> userModels = adminServiceImpl.getAllAdmins();
        if(!userModels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(userModels);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserModel> pegarAdminPeloId(@PathVariable String username) {
        UserModel userModel = adminServiceImpl.getAdminByUsername(username);
        if(userModel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/salvar/{username}")
    public ResponseEntity<UserModel> salvarAdmin(@PathVariable String username) {
        UserModel userModel = adminServiceImpl.saveAdminByUsername(username);
        if(userModel != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/salvar")
    public ResponseEntity<UserModel> salvarAdmin(@RequestBody UserModel userModel) {
        UserModel userSalvo = adminServiceImpl.saveAdmin(userModel);
        if(userSalvo != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
        } 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
