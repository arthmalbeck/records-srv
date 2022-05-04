package br.edu.unipampa.records.reconhecimento.classificacao;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import java.util.ArrayList;
import br.edu.unipampa.records.modelo.Classe;
import br.edu.unipampa.records.modelo.Elemento;
import br.edu.unipampa.records.modelo.Modelo;
import br.edu.unipampa.records.modelo.Multiplicidade;
import br.edu.unipampa.records.modelo.Relacao;
import br.edu.unipampa.records.modelo.Heranca;
import br.edu.unipampa.records.modelo.RelationTypes;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;


public class Classificador {
    public Modelo classificar(ArrayList<Elemento> retangulos, Mat imagem){
        Modelo modelo = new Modelo();
        ArrayList<Classe> classes = new ArrayList<Classe>();
        ArrayList<Relacao> relacoes = new ArrayList<Relacao>();
        ArrayList<Heranca> herancas = new ArrayList<Heranca>();
        ArrayList<Multiplicidade> multiplicidades = new ArrayList<Multiplicidade>();
        
        for(Elemento elemento: retangulos){
            Point rect_points[] = new Point[4];
            RotatedRect retangulo = elemento.getRetangulo();
            MatOfPoint contorno = elemento.getContorno();
            
            retangulo.points(rect_points);
            
            try {
                MatOfPoint2f approx = new MatOfPoint2f();
                MatOfPoint2f yourImage = new MatOfPoint2f( contorno.toArray() );
                Imgproc.approxPolyDP(yourImage, approx, Imgproc.arcLength(yourImage, true) * 0.07, true);
                
                long count = approx.total();
                
                if (count == 3) {  
                    Heranca heranca = new Heranca();
                    heranca.setRect_points(rect_points);
                    heranca.setImagem(new Mat(imagem, Imgproc.boundingRect(contorno)).clone());
                    heranca.setRetangulo(retangulo);
                    herancas.add(heranca);
                }else 
                    if (retangulo.size.height / 4 > retangulo.size.width ||
                        retangulo.size.width / 4 > retangulo.size.height) {
                    Relacao relacao = new Relacao();
                    relacao.setRect_points(rect_points);
                    relacao.setImagem(new Mat(imagem, new Rect(rect_points[0], rect_points[2])).clone());
                    relacao.setType(RelationTypes.ASSOCIATION);
                    relacoes.add(relacao);
                } else if (retangulo.size.area() > 1000 && retangulo.size.width > 50 && retangulo.size.height > 50) {
                    Classe classe = new Classe();
                    classe.setRect_points(rect_points);
                    classe.setImagem(new Mat(imagem, new Rect(rect_points[0], rect_points[2])).clone());
                    classe.setRetangulo(retangulo);
                    classes.add(classe);
                } else if (retangulo.size.area() > 100) {
                    Multiplicidade multiplicidade = new Multiplicidade();
                    multiplicidade.setRect_points(rect_points);
                    multiplicidade.setImagem(new Mat(imagem, new Rect(rect_points[0], rect_points[2])).clone());
                    multiplicidade.setRetangulo(retangulo);
                    multiplicidades.add(multiplicidade);
                }

            }catch(Exception ex){
                System.out.println("Um elemento não pode ser classificado -> " + ex);
            }
        }
        modelo.setClasses(classes);
        modelo.setRelacoes(relacoes);
        modelo.setHerancas(herancas);
        modelo.setMultiplicidades(multiplicidades);
        return modelo;
    }
}
