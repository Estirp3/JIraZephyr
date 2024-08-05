package Apis;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;

public class testReport {

    //revisa el json del cucumber
    // ingresar ruta del reporte en propierties
    public static void reporte(String carpeta, String url, String username, String token, String KeyProyecto,
            String version, String ciclo, String nuevaCarpeta, String ruta, String nameArchivo) throws IOException {
        DateTimeFormatter fechaHrs = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime ahora = LocalDateTime.now();

        String fechaHora = fechaHrs.format(ahora);

        Varios.leectorProperties lector = new Varios.leectorProperties();
        RestApis apis = new RestApis();
        SubirArchivo sa = new SubirArchivo();
        creandoEvidencia cread = new creandoEvidencia();

        String idProyecto = apis.idProyecto(url, KeyProyecto, username, token);
        String idCaso = "";
        String idVersion = "";
        String idCiclo = "";
        int idCarpeta = 0;

        idVersion = apis.idVersion(url, idProyecto, version, username, token);
        idCiclo = apis.idCiclo(url, idProyecto, idVersion, ciclo, username, token);

        if (nuevaCarpeta.equals("no")) {
            idCarpeta = apis.idCarpeta(url, idProyecto, idVersion, idCiclo, carpeta, username, token);
        } else {
            idCarpeta = apis.crearCarpeta(url, idProyecto, idVersion, idCiclo, carpeta + fechaHrs.format(ahora),
                    "Ejecucion Pruebas Automatizadas" + ciclo, username, token);
        }

        String reporte = ruta + nameArchivo;//"/Users/" + ruta;//ruta del reporte
        JSONArray arr = new JSONArray(lector.lectorData(reporte));// saca todo el texto del reporte

        //valores caso
        String nombreCaso = "";
        String estadoCaso = "";
        String keyCaso = "";

        //valores pasos
        String estadoPaso = "";
        String nombrePaso = "";
        String errorPaso = "";
        String tipoPaso = "";

        //valores ejecucion
        String idEjecucion = "";
        String estadoEjecucion = "";

        //Lista de valores de pasos
        String ListaTipoPaso[] = new String[999];
        String ListaNombrePaso[] = new String[999];
        String ListaEstadoPaso[] = new String[999];
        String ListaErrorPaso[] = new String[999];

        for (int x = 0; x < arr.length(); x++) {
            for (int i = 0; i < arr.getJSONObject(x).getJSONArray("elements").length(); i++) {
                nombreCaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getString("name");
                if (arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getString("type").equals("scenario")) {
                    keyCaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("tags").getJSONObject(0).getString("name").substring(1);

                    for (int z = 0; z < arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").length(); z++) {
                        estadoPaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").getJSONObject(z).getJSONObject("result").getString("status");
                        nombrePaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").getJSONObject(z).getString("name");
                        tipoPaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").getJSONObject(z).getString("keyword");

                        ListaNombrePaso[z] = nombrePaso;
                        ListaEstadoPaso[z] = estadoPaso;
                        ListaTipoPaso[z] = tipoPaso;

                        if (estadoPaso.equals("failed")) {
                            errorPaso = arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").getJSONObject(z).getJSONObject("result").getString("error_message");
                        } else {
                            errorPaso = "N/A";
                        }

                        if (z == arr.getJSONObject(x).getJSONArray("elements").getJSONObject(i).getJSONArray("steps").length() - 1) {
                            estadoCaso = estadoPaso;
                        }
                        ListaErrorPaso[z] = errorPaso;
                    }

                    /**
                     * Llamar caso de prueba con api *
                     */
                    if (nombreCaso.length() > 0) {
                        idCaso = apis.idCaso(url, keyCaso, username, token);

                        /**
                         * api creacion ejecucion*
                         */
                        idEjecucion = apis.crearEjecucion(url, idProyecto, idVersion, idCiclo, idCarpeta, idCaso, username, token);
                        creandoEvidencia.evidenciaWord(keyCaso, nombreCaso, ListaNombrePaso);

                        /**
                         * se comprime reporte en archivo.zip*
                         */
                        File evidencia = new File("./Ev/Evidencia_" + keyCaso + ".doc");
                        if (evidencia.exists()) {
                            sa.agregarEvidencia(url, idEjecucion, keyCaso, username, token);
                        } else {
                            System.out.println("No existe nada favor verificar ....");
                        }

                    }

                    ListaTipoPaso = new String[999];
                    ListaNombrePaso = new String[999];
                    ListaEstadoPaso = new String[999];
                    ListaErrorPaso = new String[999];
                }
            }
        }
    }

}
