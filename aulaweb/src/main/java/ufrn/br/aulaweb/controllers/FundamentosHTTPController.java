package ufrn.br.aulaweb.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FundamentosHTTPController {

    List<String> tarefas = new ArrayList<>();

    @RequestMapping("/**") //captura qualquer rota
    public void manipular(HttpServletRequest request, HttpServletResponse response) throws IOException {



        //extraindo dados start Line HTTP
        System.out.println("Metodo " + request.getMethod());// vê se é GET, POST...
        System.out.println("URI: " + request.getRequestURI()); // mostra o /caminho

        // definindo cabeçalhos (headers) de resposta




        /* |tirar para o test 3|*/ response.setContentType("text/plain"); // json, txt e etc ( previne que o navegador tente adivinhar o que estamos mandando |evita vulnerabilidades de MIME Sniffing)
        response.setCharacterEncoding("UTF-8"); //pra os caracteres n ficarem esquisitos

        //abrindo duto de rede para o corpo (body)
        PrintWriter writer = response.getWriter(); // atua diretamente na conexão TCP.
        //writer.println("requisiçãp processada com sucesso no servidor!"); // Quando chamamos println, estamos enviando pacotes de rede de volta ao roteador do cliente.

        //----------------------------------------------------------------------
        //teste 1: iterar sobre o cabeçalho (funcionou)

        writer.println("espelho de headers");

        // pega os nomes do header
        java.util.Enumeration<String> nomesDosHeaders = request.getHeaderNames();

        while(nomesDosHeaders.hasMoreElements()){

            String nome = nomesDosHeaders.nextElement(); //pega o nome
            String valor = request.getHeader(nome); //pega o valor
            writer.println(nome + ": " + valor);


        }

        // teste 2 setar status como erro (não funcionou?)

        response.setStatus(404);

        writer.println("hello word");
        // test 3
        //writer.println("<h1>Olá Mundo</h1><script>alert('Fui hackeado!');</script>");


        response.getWriter();




        writer.flush(); // esvaziamento do buffer de memória do servidor.
        writer.close();

    }


    @RequestMapping("/tarefas")
    public void gerenciarRotas(HttpServletResponse response, HttpServletRequest request) throws IOException{
        String metodo = request.getMethod();

        if(metodo.equalsIgnoreCase("GET")){
            processarGet(response);
        }else if(metodo.equalsIgnoreCase("POST")){
            processarPost(request, response);
        }else{
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);// status 405 (Rejeita o tipo do metodo usado nesse caso)
        }

    }



    public void processarGet(HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html><body><ul>");
        for (String s : tarefas){ //vai mostrar todas as tarefas
            writer.println( "<li>" + s + "</li>"); //mostra e pula linha
        }

        writer.println("</ul> </body> </html>");
    }


    public void processarPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String titulo = request.getParameter("titulo"); //pegando paramentro que vem do view HTML

        if(titulo == null || titulo.trim().isEmpty()){ // ver ser não ta em branco
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // erro 400 (má requisição)
            return; //abotarr abortarrr!
        }

        tarefas.add(titulo); // coloca titulo em tarefas


        response.setStatus(HttpServletResponse.SC_SEE_OTHER); // status 303(redirecionamento)

        response.setHeader("Location", "/tarefas"); // cliente sai da localização dele para tarefas


    }



}