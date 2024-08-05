
import Apis.testReport;
import Varios.leectorProperties;

public class Integrador {

    public static void main(String[] args) throws Exception {

        testReport App = new testReport();
        leectorProperties lec = new leectorProperties();
        final String keyProyecto = lec.getDatoProperties("keyProyecto");
        final String version = lec.getDatoProperties("version");
        final String ciclo = lec.getDatoProperties("ciclo");
        final String carpeta = lec.getDatoProperties("carpeta");
        final String url = lec.getDatoProperties("url");
        final String username = System.getenv("USER_JIRA_ID");
        final String token = System.getenv("PASS_JIRA_ID");
        final String nuevaCarpeta = lec.getDatoProperties("nuevaCarpeta");
        final String ruta = lec.getDatoProperties("ruta");
        final String nameArchivo = lec.getDatoProperties("archivo");

        /**
         *
         * Desde aca se comunica con integrador para realizar todas las
         * llamadas, a los servicios También se crea carpeta en jira
         *
         *
         */
        App.reporte(carpeta, url, username, token, keyProyecto, version, ciclo, nuevaCarpeta, ruta, nameArchivo);

    }

}
