package br.edu.unipampa.records.reconhecimento;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;


public class Teste {
    public static Mat encontrarQuadro(Mat mat){
        Imgproc.GaussianBlur(mat, mat, new Size(3, 3), 0);
        Mat kernel = Imgproc.getStructuringElement(0, new Size(6,6),new Point(0,0));
        Mat dilated = new Mat();
        Imgproc.dilate(mat, dilated, kernel);

        Mat edges = new Mat();
        Imgproc.Canny(dilated, edges, 84, 3);

        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 25);
        for (int x = 0; x < lines.cols(); x++) {
            double[] vec = lines.get(0, x);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(edges, start, end, new Scalar(255, 0, 0), 3);
        }
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(edges, contours, new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_TC89_KCOS, new Point(0, 0));
        ArrayList<MatOfPoint> contoursCleaned = new ArrayList<MatOfPoint>();
        for (int i=0; i < contours.size(); i++) {
            MatOfPoint2f matrizAtual = new MatOfPoint2f();
            contours.get(i).convertTo(matrizAtual, CvType.CV_32FC2);
            if (Imgproc.arcLength(matrizAtual, false) > 100) {
                contoursCleaned.add(contours.get(i));
            }
        }
        ArrayList<MatOfPoint> contoursArea = new ArrayList<MatOfPoint>();;

        for (int i=0; i < contoursCleaned.size(); i++) {
            if (Imgproc.contourArea(contoursCleaned.get(i)) > 10000){
                contoursArea.add(contoursCleaned.get(i));
            }
        }
        ArrayList<MatOfPoint> contoursDraw = new ArrayList<MatOfPoint>();;
        for (int i=0; i < contoursArea.size(); i++){
            MatOfPoint2f area = new MatOfPoint2f();
            contoursArea.get(i).convertTo(area, CvType.CV_32FC2);
            MatOfPoint2f draw = new MatOfPoint2f();
            contoursDraw.get(i).convertTo(draw, CvType.CV_32FC2);
            Imgproc.approxPolyDP(area, draw, 40, true);
            area.convertTo(contoursArea.get(i), CvType.CV_32S);
            draw.convertTo(contoursDraw.get(i), CvType.CV_32S);
        }
        Mat drawing = Mat.zeros(mat.size(), CvType.CV_8UC3);
        Imgproc.drawContours(drawing, contoursDraw, -1, new Scalar(0,255,0),1);
        return drawing;
    }

    public static Mat encontrarLinhas(Mat imagem) {
        Mat mYuv = new Mat();
        Mat mRgba = new Mat();
        Imgproc.Canny(imagem, imagem, 80, 120);
        Mat lines = new Mat();
        int threshold = 2;
        int minLineSize = 20;
        int lineGap = 20;

        Imgproc.HoughLinesP(imagem, lines,1, Math.PI/2, 2, 30, 1);

        for (int x = 0; x < lines.cols(); x++)
        {
            double[] vec = lines.get(0, x);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(mRgba, start, end, new Scalar(255, 0, 0), 3);

        }

        return imagem;
    }

}
