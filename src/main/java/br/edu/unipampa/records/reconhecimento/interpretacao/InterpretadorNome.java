package br.edu.unipampa.records.reconhecimento.interpretacao;

import org.opencv.core.Mat;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;


public class InterpretadorNome {
    ITesseract instance;

    public InterpretadorNome() {}

    public void iniciarOCR() throws FileNotFoundException{
        instance = new Tesseract(); 
        instance.setTessVariable("user_defined_dpi", "300");
        String fileName = "src/main/resources/tessdata";
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath()); 
    }

    public String interpretarTexto(Mat imagem) throws TesseractException, IOException {
        BufferedImage buffer = matToBufferedImage(imagem);
        String result = instance.doOCR(buffer);
        return result;
    }
   
    private BufferedImage matToBufferedImage(Mat m) {
        if (!m.empty()) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if (m.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = m.channels() * m.cols() * m.rows();
            byte[] b = new byte[bufferSize];
            m.get(0, 0, b); // get all the pixels
            BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
            final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(b, 0, targetPixels, 0, b.length);
            return image;
        }
        return null;
    }
}
