package br.edu.unipampa.records.reconhecimento.interpretacao;

import org.opencv.core.Point;
import java.util.ArrayList;
import br.edu.unipampa.records.modelo.Modelo;
import br.edu.unipampa.records.modelo.Multiplicidade;
import br.edu.unipampa.records.modelo.Relacao;


public class InterpretadorMultiplicidade {
    public Modelo interpretarConexoes(Modelo modelo){
        ArrayList<Relacao> relacoes = modelo.getRelacoes();
        ArrayList<Multiplicidade> Multiplicidades = modelo.getMultiplicidades();
        for(Relacao relacao: relacoes){
            Point extremidadeRelacao1 = relacao.getRect_points()[0];
            Point extremidadeRelacao2 = relacao.getRect_points()[2];
            for(Multiplicidade multiplicidade: Multiplicidades){
                Point centroMultiplicidade = multiplicidade.getRetangulo().center;
                double distancia = getProximidade(extremidadeRelacao1, centroMultiplicidade);
                double distancia2 = getProximidade(extremidadeRelacao2, centroMultiplicidade);
                double distanciaPermitida = (multiplicidade.getRetangulo().size.width + multiplicidade.getRetangulo().size.height);
                if(distancia <= distanciaPermitida){
                    relacao.setMultiplicidadeOrigem(multiplicidade);
                } else if(distancia2 <= distanciaPermitida){
                    relacao.setMultiplicidadeDestino(multiplicidade);
                }
            }
        }
        return modelo;
    }

    public double getProximidade(Point ponto1, Point ponto2){
        double proximidade = Math.sqrt(Math.pow(ponto1.x - ponto2.x, 2) + Math.pow(ponto1.y - ponto2.y, 2));
        return proximidade;
    }
}
