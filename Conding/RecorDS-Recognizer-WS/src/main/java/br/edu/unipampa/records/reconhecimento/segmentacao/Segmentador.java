package br.edu.unipampa.records.reconhecimento.segmentacao;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import br.edu.unipampa.records.modelo.Elemento;
import br.edu.unipampa.records.persistencia.Arquivo;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static org.opencv.imgproc.Imgproc.morphologyEx;


public class Segmentador {
    public ArrayList<Elemento> extrairElementos(Mat imagem) {
        ArrayList<MatOfPoint> contornos = extrairContornos(imagem.clone());
        return extrairMinElemento(contornos, imagem);
    }

    public Mat localizarElementos(Mat imagem, Mat imagemColorida) {
        ArrayList<MatOfPoint> contornos = extrairContornos(imagem);
        ArrayList<RotatedRect> retangulos = extrairMinRetangulo(contornos);
        return desenharMinRetangulo(retangulos, imagemColorida);
    }
    
    private ArrayList<MatOfPoint> extrairContornos(Mat imagem) {
        ArrayList<MatOfPoint> contornos = new ArrayList();
        prepararBordas(imagem);
        Imgproc.threshold(imagem, imagem, 0, 200, Imgproc.THRESH_OTSU); // Codificação do método de segmentação Threshold. THRESH_OTSU
        Imgproc.findContours(imagem, contornos, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        
        Arquivo.salvarImagem(imagem, "extrairContornos.png");
        
        return contornos;
    }
    
//  Codificação aplicação da morfologia matemática.
    private Mat prepararBordas(Mat imagem) {
        int operation = 4;//operação MORPH_GRADIENT
        int sizeKernel = 3;
        Mat element = getStructuringElement(0, new Size(sizeKernel, sizeKernel), new Point(0, 0));
        morphologyEx(imagem, imagem, operation, element, new Point(0, 0), 1);
        return imagem;
    }

    private ArrayList<RotatedRect> extrairMinRetangulo(ArrayList<MatOfPoint> contornos) {
        ArrayList<RotatedRect> retangulos = new ArrayList<RotatedRect>();
        for (MatOfPoint contorno : contornos) {
            MatOfPoint2f matrizAtual = new MatOfPoint2f();
            contorno.convertTo(matrizAtual, CvType.CV_32FC2);
            RotatedRect retanguloEncontrado = Imgproc.minAreaRect(matrizAtual);
            
            if (retanguloEncontrado.boundingRect().area() > 100) {//caso o tamanho do objeto encontrado for menor que 100 pixels o mesmo é ignorado
                retangulos.add(retanguloEncontrado);
            }
            
            matrizAtual.convertTo(contorno, CvType.CV_32S);
        }
        return retangulos;
    }

    private ArrayList<Elemento> extrairMinElemento(ArrayList<MatOfPoint> contornos, Mat imagem) {
        ArrayList<Elemento> elementos = new ArrayList<Elemento>();
        for (MatOfPoint contorno : contornos) {
            MatOfPoint2f matrizAtual = new MatOfPoint2f();
            contorno.convertTo(matrizAtual, CvType.CV_32FC2);
            RotatedRect retanguloEncontrado = Imgproc.minAreaRect(matrizAtual);
            
            if (retanguloEncontrado.size.area() > 100) {//caso o tamanho do objeto encontrado for menor que 100 pixels o mesmo é ignorado
                Elemento elemento = new Elemento();
                elemento.setRetangulo(retanguloEncontrado);
                elemento.setContorno(contorno);
                elementos.add(elemento);             
            }
            
            matrizAtual.convertTo(contorno, CvType.CV_32S);
        }
        
         Mat mResult = new Mat();
         Core.subtract(imagem, imagem, mResult);
         Imgproc.drawContours(mResult, contornos, -1, new Scalar(255));
         
         Arquivo.salvarImagem(mResult, "drawContours.png");
         
        
        return elementos;
    }
    

    private Mat desenharMinRetangulo(ArrayList<RotatedRect> retangulos, Mat imagem) {
        for (RotatedRect retangulo : retangulos) {
            Point rect_points[] = new Point[4];
            retangulo.points(rect_points);
            if(retangulo.size.area() >100) {
                for (int j = 0; j < 4; j++) {
                    Imgproc.line(imagem, rect_points[j], rect_points[(j + 1) % 4], new Scalar(255, 0, 255), 2, 2, 0);
                }
            }
        }
        return imagem;
    }


}
