package Apis;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Set;

public class RestApis {
    /**
     * En esta sección encontramos las appi expustas por jira, cada una se va a buscar desde el archivo
     * Datos.properties y se llaman unas a otras para generar la integracion de los servicios
     **/
    public static String idCaso(String url, String keyCP, String username, String pass) throws IOException {
        String json = "";
        String idCaso = "";
        try {
            String api = url + "/rest/api/2/search?jql=key=" + keyCP + "&fields=id";
            
            String authString = username + ":" + pass;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);
            ClientResponse resp = webResource.accept("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
   
                System.exit(0);
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            JSONArray issues = id.getJSONArray("issues");
            idCaso = issues.getJSONObject(0).getString("id");


        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idCaso;
    }
    public static String idProyecto(String url, String keyProyecto, String username, String pass) throws IOException {
        String json = "";
        String idProyecto = "";
        try {
            String api = url + "/rest/api/2/search?jql=project=" + keyProyecto;

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            URLConnectionClientHandler ch = new URLConnectionClientHandler();

            Client restClient = new Client(ch);
            WebResource webResource = restClient.resource(api);
            ClientResponse resp = webResource.accept("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());

            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            idProyecto = id.getJSONArray("issues").getJSONObject(0).getJSONObject("fields").getJSONObject("project").getString("id");

        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idProyecto;
    }
    public static String idVersion(String url, String iDProyecto, String version, String username, String pass) throws IOException {
        String json = "";
        String idVersion = null;
        try {
            String api = url + "/rest/zapi/latest/util/versionBoard-list?projectId=" + iDProyecto;

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);
            ClientResponse resp = webResource.accept("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            ;
            for (int x = 0; x < id.getJSONArray("unreleasedVersions").length(); x++) {
                if (id.getJSONArray("unreleasedVersions").getJSONObject(x).getString("label").equals(version)) {
                    idVersion = id.getJSONArray("unreleasedVersions").getJSONObject(x).getString("value");
                }
            }
            if (idVersion == null) {
                for (int x = 0; x < id.getJSONArray("releasedVersions").length(); x++) {
                    if (id.getJSONArray("releasedVersions").getJSONObject(x).getString("label").equals(version)) {
                        idVersion = id.getJSONArray("releasedVersions").getJSONObject(x).getString("value");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idVersion;
    }
    public static String idCiclo(String url, String iDProyecto, String idVersion, String ciclo, String username, String pass) throws IOException {
        String json = "";
        String idCiclo = null;
        try {
            String api = url + "/rest/zapi/latest/cycle?projectId=" + iDProyecto + "&versionId=" + idVersion;

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);
            ClientResponse resp = webResource.accept("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            Set<String> keys = id.keySet();
            for (String key : keys) {
                if (!key.equals("recordsCount")) {
                    if (id.getJSONObject(key).getString("name").equals(ciclo)) {
                        idCiclo = key;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idCiclo;
    }

    public static int idCarpeta(String url, String idProyecto, String idVersion, String idCiclo, String nombreCarpeta, String username, String pass) throws IOException {
        String json = "";
        int idCarpeta = 0;
        try {
            String api = url + "/rest/zapi/latest/cycle/" + idCiclo + "/folders?projectId=" + idProyecto + "&versionId=" + idVersion + "&limit=&offset=";

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONArray array = new JSONArray(json);
            for (int x = 0; x < array.length(); x++) {
                if (array.getJSONObject(x).getString("folderName").equals(nombreCarpeta)) {
                    idCarpeta = array.getJSONObject(x).getInt("folderId");
                }
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idCarpeta;
    }

    public static int crearCarpeta(String url, String idProyecto, String idVersion, String idCiclo, String nombreCarpeta, String descripcion, String username, String pass) throws IOException {
        String json = "";
        int idCarpeta = 0;
        try {
            String api = url + "/rest/zapi/latest/folder/create";

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            String input = "{" +
                    "\"cycleId\":" + idCiclo + "," +
                    "\"name\":\"" + nombreCarpeta + "\"," +
                    "\"description\":\"" + descripcion + "\"," +
                    "\"projectId\":" + idProyecto + "," +
                    "\"versionId\":" + idVersion + "" +
                    "}";

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .post(ClientResponse.class, input);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            idCarpeta = id.getInt("id");

        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idCarpeta;
    }

    public static String crearEjecucion(String url, String idProyecto, String idVersion, String idCiclo, int idCarpeta, String idCP, String username, String pass) throws IOException {
        String json = "";
        String idEjecucion = "";
        try {
            String api = url + "/rest/zapi/latest/execution";

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            String input = "{" +
                    "\"cycleId\":\"" + idCiclo + "\"," +
                    "\"issueId\":\"" + idCP + "\"," +
                    "\"projectId\":\"" + idProyecto + "\"," +
                    "\"versionId\":\"" + idVersion + "\"," +
                    "\"assigneeType\":\"assignee\"," +
                    " \"assignee\":\"" + username + "\"," +
                    " \"folderId\":" + idCarpeta + "" +
                    "}";

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .post(ClientResponse.class, input);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject id = new JSONObject(json);
            Set<String> keys = id.keySet();
            for (String key : keys) {
                idEjecucion = key;
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return idEjecucion;
    }

    public static String statusEjecucion(String url, String idEjecucion, String estadoCaso, String username, String pass) throws IOException {
        String status = "";

        if (estadoCaso.equals("passed")) {
            status = "1";
        } else if (estadoCaso.equals("failed") || estadoCaso.equals("skipped")) {
            status = "2";
        } else {
            status = "3";
        }
        try {
            String api = url + "/rest/zapi/latest/execution/" + idEjecucion + "/execute";

            String name = username;
            String password = pass;
            String authString = name + ":" + password;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            String input = "{" +
                    "\"status\":\"" + status + "\"" +
                    "}";

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .put(ClientResponse.class, input);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            }

        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return status;
    }

    public static void limpiarAdjuntos(String url, String username, String pass, String IdEjecucion) throws IOException {
        String json = "";
        int idCarpeta = 0;
        try {
            String api = url + "/rest/zapi/latest/attachment/attachmentsByEntity?entityId=" + IdEjecucion + "&entityType=EXECUTION";
            System.out.println(api);

            String authString = username + ":" + pass;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .header("content-length", "0")
                    .get(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                json = resp.getEntity(String.class);
            }
            JSONObject jsonO = new JSONObject(json);
            JSONArray array = jsonO.getJSONArray("data");
            for (int x = 0; x < array.length(); x++) {
                if (x > 1) {
                    String IdAdjunto = array.getJSONObject(x).getString("fileId");
                    borrarAdjunto(url, username, pass, IdAdjunto);
                }
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
    }

    public static String borrarAdjunto(String url, String username, String pass, String IdAdjunto) throws IOException {
        String Respuesta = "";
        try {
            String api = url + "/rest/zapi/latest/attachment/" + IdAdjunto;
            System.out.println(api);

            String authString = username + ":" + pass;
            String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(api);

            ClientResponse resp = webResource.type("application/json")
                    .header("Authorization", "Basic " + encodedString)
                    .header("User-Agent", "")
                    .delete(ClientResponse.class);
            if (resp.getStatus() != 200) {
                System.err.println("Servicio generó error " + resp.getStatus());
            } else {
                Respuesta = "Se elimino Adjunto " + IdAdjunto;
            }
        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
        return Respuesta;
    }

    public static void subirAdjunto(String url, String idEjecucion, String archivo, String username, String pass) throws IOException {

        String comando = "";
        String infoterminal1 = "";
        String infoterminal2 = "";
        try {
            infoterminal1 = "bash";
            infoterminal2 = "-c";
            comando = "if [ -f " + archivo + " ];\n then\n curl -D- -u : -X POST -H \"X-Atlassian-Token: nocheck\" " +
                    "-u " + username + ":" + pass + " " +
                    "-F \"file=@" + archivo + "\"" +
                    " \"" + url + "/rest/zapi/latest/attachment?entityId=" + idEjecucion + "&entityType=execution\"\n fi";

            System.out.println(comando);

            ProcessBuilder processBuilder = new ProcessBuilder(infoterminal1, infoterminal2, comando);
            //processBuilder.directory(new File("C:\\SAT"));
            Process process = processBuilder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("error al cargar servicio: " + e);
        }
    }

}