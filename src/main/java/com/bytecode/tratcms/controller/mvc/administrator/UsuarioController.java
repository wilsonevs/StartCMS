package com.bytecode.tratcms.controller.mvc.administrator;

import com.bytecode.tratcms.data.model.MUsuario;
import com.bytecode.tratcms.data.repository.GrupoRepository;
import com.bytecode.tratcms.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public ModelAndView getHome(
            @RequestParam(defaultValue = "all", required = false) String view_name,
            @RequestParam(defaultValue = "0", required = false) int id,
            SpringDataWebProperties.Pageable pageable
    ){
        ModelAndView modelAndView = new ModelAndView("administrator/usuario");
        switch (view_name){
            case "all":
                modelAndView.addObject("usuarios", usuarioRepository.findAll(pageable));
                break;
            case "new":
                modelAndView.addObject("usuario", new MUsuario());
                modelAndView.addObject("grupos", grupoRepository.findAll(pageable));
                modelAndView.addObject("update", false);
                break;
            case "update":
                modelAndView.addObject("usuario", usuarioRepository.findById(id));
                modelAndView.addObject("grupos", grupoRepository.findAll(pageable));
                modelAndView.addObject("update", true);
                break;
        }
        return modelAndView;
    }

    @PostMapping
    public String newAndUpdate(
            @ModelAttribute MUsuario MUsuario
    ){
        if (MUsuario.getIdUsuario() > 0){
            usuarioRepository.update(MUsuario);
        } else {
            MUsuario.setContrasena(passwordEncoder.encode(MUsuario.getContrasena()));
            usuarioRepository.save(MUsuario);
        }
        return "redirect:/admin/usuario";
    }
}
