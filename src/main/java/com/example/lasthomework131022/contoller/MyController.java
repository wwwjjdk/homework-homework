package com.example.lasthomework131022.contoller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class MyController {
    //@RolesAllowed (java ee) & @Secured(security) взаимозаменяемые

    @PreAuthorize("hasRole('ROLE_DELETE') or hasRole('ROLE_WRITE')")
    @GetMapping("/by-delete")
    public String delete() {
        return "role_delete or role_write";
    }

    @RolesAllowed("ROLE_WRITE")
    @GetMapping("/by-write")
    public String write() {
        return "role_write";
    }

    @Secured("ROLE_READ")
    @GetMapping("/by-read")
    public String read() {
        return "role_read";
    }

    @GetMapping("/by-deleteQuery/{name}")
    @PreAuthorize("#name == authentication.name")
    public String deleteQuery(@PathVariable String name){
        return "deleteQuery" + name;
    }
    //эндпоинты

    @GetMapping("/by-deletePath")
    public String byDeletePath(){
        return "by-deletePath";
    }

   @GetMapping("/by-writeOrDeletePath")
    public String byWriteOrDeletePath(){
        return "by-writeOrDeletePath";
    }

    @GetMapping("/by-readPath")
    public String byReadPath(){
        return "by-read";
    }
    @GetMapping("/")
    public String byHelloPath(){
        return "byHello";
    }
}
