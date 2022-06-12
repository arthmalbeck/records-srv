package br.edu.unipampa.records.controlador;

import org.opencv.core.Mat;
import java.util.ArrayList;
import br.edu.unipampa.records.reconhecimento.classificacao.Classificador;
import br.edu.unipampa.records.modelo.Classe;
import br.edu.unipampa.records.modelo.Elemento;
import br.edu.unipampa.records.modelo.Modelo;
import br.edu.unipampa.records.modelo.Multiplicidade;
import br.edu.unipampa.records.modelo.Relacao;
import br.edu.unipampa.records.modelo.RelationTypes;
import br.edu.unipampa.records.reconhecimento.interpretacao.InterpretadorMultiplicidade;
import br.edu.unipampa.records.reconhecimento.interpretacao.InterpretadorNome;
import br.edu.unipampa.records.reconhecimento.interpretacao.InterpretadorRelacao;
import br.edu.unipampa.records.reconhecimento.segmentacao.Segmentador;
import br.edu.unipampa.records.reconhecimento.segmentacao.SegmentadorNome;
import br.edu.unipampa.records.xmi.ClassGeneralization;
import br.edu.unipampa.records.xmi.ClassRelation;
import br.edu.unipampa.records.xmi.Generalization;
import br.edu.unipampa.records.xmi.Multiplicity;
import br.edu.unipampa.records.xmi.Relation;
import br.edu.unipampa.records.xmi.XMI;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;


public class ControladorReconhecimento {
    private Mat imagem;
    private Mat imagemColorida;

    private Segmentador segmentador;
    private SegmentadorNome segmentadorNome;

    private InterpretadorRelacao interpretadorRelacao;
    private InterpretadorMultiplicidade interpretadorMultiplicidade;
    private InterpretadorNome interpretadorNome;

    private Classificador classificador;

    public ControladorReconhecimento(){
        this.interpretadorMultiplicidade = new InterpretadorMultiplicidade();
        this.interpretadorNome = new InterpretadorNome();
        this.interpretadorRelacao = new InterpretadorRelacao();

        this.segmentador = new Segmentador();
        this.segmentadorNome = new SegmentadorNome();

        this.classificador = new Classificador();
    }

    public Modelo reconhecer(String nomeDiagrama) throws TesseractException, FileNotFoundException, IOException{
        interpretadorNome.iniciarOCR();
        
        ArrayList<Elemento> elementos = segmentador.extrairElementos(imagem);
        Modelo modelo = classificador.classificar(elementos, imagem);
        interpretadorRelacao.interpretarConexoes(modelo);
        interpretadorMultiplicidade.interpretarConexoes(modelo);
        for (Classe classe : modelo.getClasses()) {
            Mat nome = new SegmentadorNome().extrairNome(classe.getImagem());
            String nomeTexto = interpretadorNome.interpretarTexto(nome);
            classe.setNome(nomeTexto);
        }
        for(Multiplicidade multiplicidade: modelo.getMultiplicidades()){
            String nomeTexto = interpretadorNome.interpretarTexto(multiplicidade.getImagem());
            multiplicidade.setValor(nomeTexto);
        }

        return modelo;
    }

    public XMI salvarXMI(Modelo modelo){
        XMI xmi = new XMI();
        xmi.getModel().setName("Diagrama");
        for(Classe classe: modelo.getClasses()){
            br.edu.unipampa.records.xmi.Class classXmi = new  br.edu.unipampa.records.xmi.Class(classe.getNome());
            
            for(Relacao relacao : classe.getRelacao()){
                
                if(relacao.getClasseDestino() != null && relacao.getClasseOrigem() != null) {
                    //If GENERALIZATION
                    if(relacao.getType().equals(RelationTypes.GENERALIZATION)){ 
                       if(classe.equals(relacao.getClasseOrigem())){
                            ClassGeneralization general = new ClassGeneralization();
                            general.setNameClass(relacao.getClasseDestino().getNome());

                            ClassGeneralization specific = new ClassGeneralization();
                            specific.setNameClass(relacao.getClasseOrigem().getNome());

                            Generalization heranca = new Generalization();
                            heranca.setClasseOrigem(specific.getNameClass());
                            heranca.setClasseDestino(general.getNameClass());
                            heranca.setType("uml:Generalization");

                            classXmi.getHerancas().add(heranca);
                       }
                    }else{
                    //If ASSOCIATION
                        ClassRelation classRelation = new ClassRelation();
                        classRelation.setNameClass(relacao.getClasseOrigem().getNome());
                        if(relacao.getMultiplicidadeOrigem() != null) {
                            Multiplicity multiplicity = new Multiplicity(relacao.getMultiplicidadeOrigem().getValor(), relacao.getClasseOrigem().getNome());
                            classRelation.setMultiplicityUpper(multiplicity);
                            classRelation.setMultiplicityLower(multiplicity);
                        }

                        ClassRelation classRelation2 = new ClassRelation();
                        classRelation2.setNameClass(relacao.getClasseDestino().getNome());
                        if(relacao.getMultiplicidadeDestino() != null) {
                            Multiplicity multiplicity2 = new Multiplicity(relacao.getMultiplicidadeDestino().getValor(), relacao.getClasseDestino().getNome());
                            classRelation2.setMultiplicityUpper(multiplicity2);
                            classRelation2.setMultiplicityLower(multiplicity2);
                        }

                        Relation relation = new Relation();
                        relation.setClasseOrigem(classRelation);
                        relation.setClasseDestino(classRelation2);

                        classXmi.getRelacoes().add(relation);
                    }
                }
            }
            xmi.getModel().getElement().getElements().add(classXmi);
        }
        return xmi;
    }

    public Mat detectarElementos(){
        return segmentador.localizarElementos(imagem, imagemColorida);
    }

    public void setImagem(Mat imagem){
        this.imagem = imagem;
    }

    public Mat getImagemColorida() {
        return imagemColorida;
    }

    public void setImagemColorida(Mat imagemColorida) {
        this.imagemColorida = imagemColorida;
    }
}
