package br.edu.unipampa.records.recognizer.ws.resource;

import br.edu.unipampa.records.controlador.ControladorReconhecimento;
import br.edu.unipampa.records.modelo.Modelo;
import br.edu.unipampa.records.persistencia.Arquivo;
import br.edu.unipampa.records.xmi.XMI;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("/recognizer")
public class RecognizerResource {
     
    @GetMapping("/load")
    public String load(){
        return "WS was loaded";
    }

    
//    Orientation: portraitUp, portraitDown, landscapeLeft, landscapeRight
    @RequestMapping(value ="/process-image",method=RequestMethod.POST ,headers="Accept=application/xml, application/json")
    public @ResponseBody Object processImage(@RequestParam("image") String encodedString, @RequestParam("rotation") String rotation) throws Exception {
        nu.pattern.OpenCV.loadLocally();
        byte[] fileBytes = Base64.getDecoder().decode(encodedString);
        
        ControladorReconhecimento cr = new ControladorReconhecimento();
        Mat imagemColorida = Imgcodecs.imdecode(new MatOfByte(fileBytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        
        Mat flip = new Mat();
        flip = rectifyOrientation(imagemColorida, rotation);
       
        Mat imagem = flip.clone();
        cr.setImagemColorida(flip); 
        Imgproc.cvtColor(imagem, imagem, Imgproc.COLOR_RGB2GRAY); 
        cr.setImagem(imagem);
        
        Modelo modelo = cr.reconhecer("diagrama");
        XMI xmi = cr.salvarXMI(modelo);
        String rtn = Arquivo.criarXMI("Diagrama", xmi);
       
        return rtn;
    }
    
    @RequestMapping(value ="/detect",method=RequestMethod.POST ,headers="Accept=application/xml, application/json")
    public @ResponseBody Object detect(@RequestParam("image") String encodedString, @RequestParam("rotation") String rotation) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
   
        nu.pattern.OpenCV.loadLocally();

        byte[] fileBytes = decodedBytes;
        
        ControladorReconhecimento cr = new ControladorReconhecimento();
        Mat imagemColorida = Imgcodecs.imdecode(new MatOfByte(fileBytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        
        Mat flip = new Mat();
        flip = rectifyOrientation(imagemColorida, rotation);
       
        Mat imagem = flip.clone();
        cr.setImagemColorida(flip); 
        Imgproc.cvtColor(imagem, imagem, Imgproc.COLOR_RGB2GRAY); 
        cr.setImagem(imagem);
        
        Mat response = cr.detectarElementos();
        
        BufferedImage res = matToBufferedImage(response);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(res, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        
        String sss = Base64.getEncoder().encodeToString(bytes);
        
        return sss;
    }
    
    //    Orientation: portraitUp, portraitDown, landscapeLeft, landscapeRight
    private Mat rectifyOrientation(Mat image, String rotation){
    
        Mat transpose = new Mat();
        Mat flip = new Mat();
        
        switch(rotation) {
            case "-90": // Image veio em landscapeLeft -90 ok
                Core.transpose(image, transpose);
                Core.flip(transpose, flip, -1);
                Core.flip(flip, flip, 1);
              break;
            case "90": // Image veio em landscapeRight 90 ok
                Core.transpose(image, transpose);
                Core.flip(transpose, flip, 1);
              break;
            case "180": // Image veio em portraitDown  180 ok
                Core.flip(image, flip, -1);
              break;
            default: // Image veio em portraitUp  0 ok
                flip = image;
        }
        
        return flip;
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
