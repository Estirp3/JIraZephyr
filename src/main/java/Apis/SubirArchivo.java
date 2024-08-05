package Apis;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Base64;

public class SubirArchivo {

    public static void agregarEvidencia(String url, String idEjecucion, String Keycaso,String username, String pass) {

        String authString = username + ":" + pass;
        String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());


        RequestSpecification uploadRequest = RestAssured.given();
        uploadRequest.header("Content-Type", "multipart/form-data");
        uploadRequest.header("Authorization", "Basic " + encodedString);
        String uploadEndpoint = url + "/rest/zapi/latest/attachment/";
        uploadRequest.multiPart("file", new File("./Ev/evidencia/" + Keycaso + ".png"));
        uploadRequest.queryParam("entityId", idEjecucion);
        uploadRequest.queryParam("entityType", "execution");
        int uploadStatus = uploadRequest.post(uploadEndpoint).getStatusCode();
        System.out.println("Respuesta del servicio " + uploadStatus);
    }

}
