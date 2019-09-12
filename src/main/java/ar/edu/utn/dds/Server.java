package ar.edu.utn.dds;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Server {


    //"Persistencia" de la aplicacion
    static List<Auto> automotores = new ArrayList<>();

    public static void main(String[] args) {
        enableDebugScreen();
        port(4567);
        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }

        Auto auto1 = new Auto("HGG123", "Astra", "Chevrolet",    2005);
        Auto auto2 = new Auto("TAA123","Palio",   "Fiat",   2011);
        Auto auto3 = new Auto("GDC123", "Siena", "Fiat",    2016);

        automotores.add(auto1);
        automotores.add(auto2);
        automotores.add(auto3);


        // Ejemplo de acceso: http://localhost:4567/auto
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        get("/auto", Server::listaAutos, engine);
        post("/auto", Server::guardarAuto);
        delete("/auto/:patente", Server::borrarAuto);
        get("/auto/:patente", Server::obtenerAuto, engine);
        post("/auto/:patente", Server::guardarAuto);

    }

    public static Auto buscarAuto(String patente) throws AutoNotFoundException {
        Optional<Auto> autoABuscar = automotores.stream().filter(auto -> auto.getPatente().equals(patente)).findFirst();
        if (autoABuscar.isPresent()) {
            return autoABuscar.get();
        }
        throw new AutoNotFoundException(patente);
    }

    public static ModelAndView listaAutos(Request request, Response response) {

        List<Auto> autos = null;
        if (request.queryParams("marca") == null || request.queryParams("marca").isEmpty()) {
            autos = automotores;

        } else {
            autos = automotores.stream().filter(a ->
                    a.getMarca().equals(request.queryParams("marca"))
            ).collect(Collectors.toList());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("autos", autos);
        return new ModelAndView(map, "autos.html");
    }

    public static ModelAndView obtenerAuto(Request request, Response response) throws AutoNotFoundException {
        Auto auto = buscarAuto(request.params("patente"));
        Map<String, Object> map = new HashMap<>();
        map.put("auto", auto);
        return new ModelAndView(map, "auto.html");
    }

    public static Object guardarAuto(Request request, Response response)  {


        Auto auto = null;
        try {
            auto = buscarAuto(request.queryParams("patente"));
            auto.setPatente(request.queryParams("patente"));
            auto.setAnio(Integer.parseInt(request.queryParams("anio")));
            auto.setModelo(request.queryParams("modelo"));
            auto.setMarca(request.queryParams("marca"));
            response.status(200);
        } catch (AutoNotFoundException e) {
            auto = new Auto();
            auto.setPatente(request.queryParams("patente"));
            auto.setAnio(Integer.parseInt(request.queryParams("anio")));
            auto.setModelo(request.queryParams("modelo"));
            auto.setMarca(request.queryParams("marca"));
            automotores.add(auto);
            response.status(201);
        }
        response.redirect("/auto");

        return null;
    }

    public static Object borrarAuto(Request request, Response response) throws Exception {

        automotores = automotores.stream().filter(
                auto -> !auto.getPatente().equals(request.params("patente"))
        ).collect(Collectors.toList());

        response.status(202);
        return "";
    }


}
