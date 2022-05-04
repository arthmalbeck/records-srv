package br.edu.unipampa.records.persistencia;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import br.edu.unipampa.records.xmi.XMI;


public class Arquivo {
    final static String TARGET_BASE_PATH = "//Users//arthurbecker//Desktop//Records//";

    public void copiarArquivosParaSD() {
        copiarArquivoOuDiretorio(""); // copy all files in assets folder in my project
    }

    private void copiarArquivoOuDiretorio(String path) {
//        AssetManager assetManager = CameraActivity.camera.getAssets();
        String assets[] = null;
//        try {
            System.out.println("tag" + "copiarArquivoOuDiretorio() " + path); //Log.i -> Sout... Fazer salvar em arquivo
//            assets = assetManager.list(path);
            if (assets.length == 0) {
                copiarArquivo(path);
            } else {
                String fullPath =  TARGET_BASE_PATH + path;
                System.out.println("tag" + "path=" + fullPath); //Log.i -> Sout... Fazer salvar em arquivo
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    if (!dir.mkdirs())
                        System.out.println("tag" + "could not create dir " + fullPath); //Log.i -> Sout... Fazer salvar em arquivo
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copiarArquivoOuDiretorio(p + assets[i]);
                }
            }
//        } catch (IOException ex) {
//            Log.e("tag", "I/O Exception", ex);
//        }
    }

    private void copiarArquivo(String nomeArquivo) {
//        AssetManager assetManager = CameraActivity.camera.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            System.out.println("tag" + "copiarArquivo() " + nomeArquivo); //Log.i -> Sout... Fazer salvar em arquivo
//            in = assetManager.open(nomeArquivo);
            if (nomeArquivo.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = TARGET_BASE_PATH + nomeArquivo.substring(0, nomeArquivo.length()-4);
            else
                newFileName = TARGET_BASE_PATH + nomeArquivo;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            System.out.println("tag" + "Exception in copiarArquivo() of "+newFileName); //Log.e -> Sout... Fazer salvar em arquivo
            System.out.println("tag" + "Exception in copiarArquivo() "+e.toString()); //Log.e -> Sout... Fazer salvar em arquivo
        }

    }

    public static void salvarImagem(Mat imagem, String nomeArquivo){
        Imgcodecs.imwrite(TARGET_BASE_PATH+nomeArquivo, imagem);
    }

    public static boolean salvarXMI(String nome, XMI xmi){
        try {
            FileWriter out = new FileWriter(new File(TARGET_BASE_PATH, nome+".xmi"));
            out.write(xmi.gerarXMI());
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static String criarXMI(String nome, XMI xmi){
            return xmi.gerarXMI();
    }
    
}
