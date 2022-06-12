package br.edu.unipampa.records.reconhecimento.interpretacao;

import org.opencv.core.Point;
import java.util.ArrayList;
import br.edu.unipampa.records.modelo.Classe;
import br.edu.unipampa.records.modelo.Modelo;
import br.edu.unipampa.records.modelo.Relacao;
import br.edu.unipampa.records.modelo.Heranca;
import br.edu.unipampa.records.modelo.RelationTypes;


public class InterpretadorRelacao {
    public Modelo interpretarConexoes(Modelo modelo) {
        ArrayList<Relacao> relacoes = modelo.getRelacoes();
        ArrayList<Classe> classes = modelo.getClasses();
        ArrayList<Heranca> herancas = modelo.getHerancas();
      
        
        // Para cada TRIANGULO, 
        for(Heranca heranca: herancas){
            Relacao nearestRelation = null;
            Classe nearestClass = null;
            Point centerTriangle = heranca.getRetangulo().center;
                      
//          - identificar a RELACAO com extremidade mais próxima do triangul, 
            for (Relacao relacao : relacoes) {
                Point extremidadeRelacao1 = relacao.getRect_points()[0];
                Point extremidadeRelacao2 = relacao.getRect_points()[2];
                double distRelacaoTriangAnterior = 0;
                
                double distRelacaoTriang = getProximidade(extremidadeRelacao1,  centerTriangle);
                double distRelacaoTriang2 = getProximidade(extremidadeRelacao2,  centerTriangle);

                double mediaDistancia = (heranca.getRetangulo().size.width + heranca.getRetangulo().size.height) / 1.8;             
                if(distRelacaoTriang <= mediaDistancia || distRelacaoTriang2 <= mediaDistancia){
                    if( distRelacaoTriang < distRelacaoTriangAnterior || distRelacaoTriangAnterior == 0 ||
                        distRelacaoTriang2 < distRelacaoTriangAnterior
                    ) {
                        nearestRelation = relacao;
                        if(distRelacaoTriang < distRelacaoTriang2){
                            distRelacaoTriangAnterior = distRelacaoTriang;
                        }else{
                            distRelacaoTriangAnterior = distRelacaoTriang2;
                        }
                    }
                }
            }
            
            // identificar a CLASSE com o centro mais próximo do triangulo;
            for (Classe classe : classes) {
                double distanciaAnterior = 0;
                double distancia = getProximidade(centerTriangle,  classe.getRetangulo().center);

                double mediaDistancia = (classe.getRetangulo().size.width + classe.getRetangulo().size.height) / 1.8;
                if(distancia <= mediaDistancia){
                    if(distancia < distanciaAnterior || distanciaAnterior == 0){
                        nearestClass = classe;
                        distanciaAnterior = distancia;
                    }
                }
            }
            //Setando a Classe proxima ao triangulo como GENERAL
            nearestRelation.setClasseDestino(nearestClass);
            
            // - definir o tipo como Generalization;
            nearestRelation.setType(RelationTypes.GENERALIZATION);    
            
            // - Identificar a CLASSE da outra extremidade
            Point aux = null;
            Point extremidadeRelacao1 = nearestRelation.getRect_points()[0];
            Point extremidadeRelacao2 = nearestRelation.getRect_points()[2];
            double distancia = getProximidade(extremidadeRelacao1,  centerTriangle);
            double distancia2 = getProximidade(extremidadeRelacao2,  centerTriangle);
            
            if(distancia < distancia2){
                aux = extremidadeRelacao2;
            }else{
                aux = extremidadeRelacao1;
            }
            
            double distanciaAnterior = 0;
            for (Classe classe : classes) {
                
                double distRelacao = getProximidade(aux,  classe.getRetangulo().center);
                double mediaDistancia = (classe.getRetangulo().size.width + classe.getRetangulo().size.height) / 1.8;
                if(distRelacao <= mediaDistancia){
                    if(distRelacao < distanciaAnterior || distanciaAnterior == 0) {
                        nearestRelation.setClasseOrigem(classe);
                        distanciaAnterior = distRelacao;
                    }
                }
            }
        }
        
        
        for (Relacao relacao : relacoes) {
            if(!relacao.getType().equals(RelationTypes.GENERALIZATION)){
                Point extremidadeRelacao1 = relacao.getRect_points()[0];
                Point extremidadeRelacao2 = relacao.getRect_points()[2];
                double distanciaAnteriorEx1 = 0;
                double distanciaAnteriorEx2 = 0;

                for (Classe classe : classes) {
                    double distancia = getProximidade(extremidadeRelacao1,  classe.getRetangulo().center);
                    double distancia2 = getProximidade(extremidadeRelacao2,  classe.getRetangulo().center);

                    double mediaDistancia = (classe.getRetangulo().size.width + classe.getRetangulo().size.height) / 1.8;
                    if(distancia <= mediaDistancia){
                        if(distancia < distanciaAnteriorEx1 || distanciaAnteriorEx1 == 0) {
                            relacao.setClasseOrigem(classe);
                            distanciaAnteriorEx1 = distancia;
                        }
                    }

                    if(distancia2 <= mediaDistancia){
                        if(distancia2 < distanciaAnteriorEx2 || distanciaAnteriorEx2 == 0) {
                            relacao.setClasseDestino(classe);
                            distanciaAnteriorEx2 = distancia2;
                        }
                    }
                }
            }
        }
        
        verificarClasses(relacoes);
        return modelo;
    }

    public double getProximidade(Point ponto1, Point ponto2){
        double proximidade = Math.sqrt(Math.pow(ponto1.x - ponto2.x, 2) + Math.pow(ponto1.y - ponto2.y, 2));
        return proximidade;
    }

    public void verificarClasses(ArrayList<Relacao> relacoes){
        for(Relacao relacao: relacoes){
            Classe classeOrigem = relacao.getClasseOrigem();
            Classe classeDestino = relacao.getClasseDestino();

            if(classeOrigem !=null){
                classeOrigem.getRelacao().add(relacao);
            }
            if(classeDestino !=null){
                classeDestino.getRelacao().add(relacao);
            }
        }
    }
}
