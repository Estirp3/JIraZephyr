package Varios;



import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class leectorProperties {

    public static final String ARCHIVO_PROPIEDADES = "./Datos.properties";


    private static final Properties properties;

    static {
        try {

            properties = new Properties();
            FileInputStream file = new FileInputStream(ARCHIVO_PROPIEDADES);
            properties.load(file);
        } catch (FileNotFoundException e) {

            throw new RuntimeException("Excepcion  Archivo " + ARCHIVO_PROPIEDADES + " no encontrado.", e);

        } catch (IOException e) {
            throw new RuntimeException("Excepcion Archivo " + ARCHIVO_PROPIEDADES + " no pudo ser cargado.", e);
        }
    }

    public static String getDatoProperties(String value) {
        return properties.getProperty(value);
    }

    public static String lectorData(String file){
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public static void comprimirZip(String archivo) throws IOException {

        FileOutputStream fos = new FileOutputStream(archivo.substring(0, archivo.length()-5)+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(archivo);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();

    }


}
