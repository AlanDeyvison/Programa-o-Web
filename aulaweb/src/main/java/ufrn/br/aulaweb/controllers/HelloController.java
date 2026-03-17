package ufrn.br.aulaweb.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController // retorna json e outros no localhost
public class HelloController {


    @RequestMapping("/hello")
    public void doGEt(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().println("hello word");
    }

}
