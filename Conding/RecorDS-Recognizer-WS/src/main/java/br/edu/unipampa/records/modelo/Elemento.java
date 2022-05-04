package br.edu.unipampa.records.modelo;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;


public class Elemento {
    private Mat imagem;
    private Point[] rect_points;
    private MatOfPoint contorno;
    private RotatedRect retangulo;

    public Mat getImagem() {
        return imagem;
    }

    public void setImagem(Mat imagem) {
        this.imagem = imagem;
    }

    public Point[] getRect_points() {
        return rect_points;
    }

    public void setRect_points(Point[] rect_points) {
        this.rect_points = rect_points;
    }

    public MatOfPoint getContorno() {
        return contorno;
    }

    public void setContorno(MatOfPoint contorno) {
        this.contorno = contorno;
    }

    public RotatedRect getRetangulo() {
        return retangulo;
    }

    public void setRetangulo(RotatedRect retangulo) {
        this.retangulo = retangulo;
    }
}
