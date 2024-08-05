package Apis;

import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class creandoEvidencia {
    public static void evidenciaWord(String caso, String nombrecaso, String[] pasos) {
        try {
            File carpeta = new File("./Ev/evidencia/");//ruta evidencia words

            XWPFDocument documentos = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File("./Ev/Evidencia_" + caso + ".doc"));//direccion donde se crea docuemnto

            String[] listado = carpeta.list();
            if (listado == null || listado.length == 0) {
                //System.out.println("No hay elementos dentro de la carpeta actual");
                return;
            } else {
                for (int i = 0; i < listado.length; i++) {
                    if (listado[i].contains(caso)) {

                    }
                }
            }
            XWPFParagraph paragraph = documentos.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(nombrecaso);

            XWPFTable table = documentos.createTable();

            /**Fila Titulos**/
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("Paso");
            tableRowOne.addNewTableCell().setText("Evidencia");

            int x = 0;

            while (pasos[x] != null) {
                File file = new File("./Ev/evidencia/" + caso + ".png");
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(pasos[x]);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream("./Ev/evidencia/" + caso + ".png");
                    XWPFParagraph paragraphCell = tableRowTwo.getCell(1).addParagraph();
                    XWPFRun runCell = paragraphCell.createRun();
                    runCell.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, "images.jpg", Units.toEMU(350), Units.toEMU(200));

                }
                x++;
            }
            documentos.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
