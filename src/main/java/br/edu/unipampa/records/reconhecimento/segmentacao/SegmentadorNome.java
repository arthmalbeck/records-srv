package br.edu.unipampa.records.reconhecimento.segmentacao;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;


public class SegmentadorNome {
    private Mat extrairContornos(Mat imagem) {
        Imgproc.threshold(imagem, imagem, 0, 255, Imgproc.THRESH_OTSU);
        return imagem;
    }

    public Mat extrairNome(Mat imagemNome){
        Imgproc.resize(imagemNome, imagemNome, new Size((imagemNome.size().width*1.5), (imagemNome.size().height*1.5)));
        imagemNome = extrairContornos(imagemNome);
        imagemNome = retirarFundo(imagemNome);

        return imagemNome;
    }

    /**
     * Retira o fundo de uma imagem e pega o conteudo de dentro
     *
     * @param imagem
     * @return
     */
    private Mat retirarFundo(Mat imagem){
        ArrayList<MatOfPoint> contornos = new ArrayList<MatOfPoint>();
        ArrayList<MatOfPoint> contornoTotal = new ArrayList<MatOfPoint>();
        Scalar colorContorno = new Scalar(255, 255, 255);
        Mat hierarquia = new Mat();
        Mat hierarquia2 = new Mat();
        Imgproc.findContours(imagem, contornos, hierarquia, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(imagem, contornoTotal, hierarquia2, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat resultadoBorda = new Mat(imagem.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
        
        Imgproc.drawContours(resultadoBorda, contornos, -1, colorContorno, -1);
        Mat resultadoTotal = new Mat(imagem.size(), CvType.CV_8UC3,new Scalar(0, 0, 0));
        Imgproc.drawContours(resultadoTotal, contornoTotal, -1, colorContorno,-1);
        Mat resultadoRetiradaFundo = new Mat(imagem.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
        Core.absdiff(resultadoBorda, resultadoTotal, resultadoRetiradaFundo);
        return resultadoRetiradaFundo;
    }
}
