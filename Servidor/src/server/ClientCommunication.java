/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientCommunication extends Thread {

    Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    public String palabra;

    private String[] diccionario = {"ABEJA", "AEROPUERTO", "COMPUTADOR", "OSO",
        "JAVA", "NEVERA", "PROGRAMA", "INFORMATICA", "COMPUTACION", "COMPUTADOR", "CORAZON", "BANANO", "PLATANO",
        "AUTOMOVIL", "PERRO", "COLOMBIA", "LONDRES", "CEPILLO", "BRAZO", "CABEZA", "CUERPO", "DEPORTE", "SALUD",
        "ANONYMOUS", "CUADERNO", "PANTALLA", "UBUNTU", "SEMAFORO", "LINUX", "LOBO", "AMOR", "MOSCA", "ZANAHORIA",
        "PINGUINO", "HACKER", "SISTEMA", "ELEFANTE", "CASCADA", "JUEGOS", "LARGO", "BONITO", "IMPOSIBLE", "UNIDOS", "ZOMBIE",
        "MATEMATICAS", "CALCULO", "ALGEBRA", "DICCIONARIO", "BIBLIOTECA", "COCINA", "PELICULA", "COMERCIAL", "GRANDE", "PEQUEÑO",
        "GATO", "SAPO", "JIRAFA", "FERROCARRIL", "FACEBOOK", "PERSONA", "BICICLETA", "CONTROL", "PANTALON", "AEROSOL",
        "ERROR", "COPA", "COPA", "PROGRAMADOR", "LICENCIA", "NUEVE", "PROCESADOR", "GARAJE", "MODERNO", "TABLA", "DISCOTECA",
        "LENGUAJE", "PROGRAMACION", "HERRAMIENTAS", "INTERNET", "EJECUTAR", "PROYECTO", "PERIODICO", "COCODRILO", "TORTUGA", "CABALLO",
        "APLICACION", "PERA", "SOFTWARE", "ADMINISTRACION", "VENTANA", "MANTENIMIENTO", "INFORMACION", "PRESIDENTE", "PERSONA", "GENTE",
        "NARANJA", "PRUEBA", "MANZANA", "JARRA", "CELULAR", "TELEFONO", "CONTAMINACION", "COLOR", "ROMANO", "ADIVINAR", "MARCADOR",
        "INSTRUCCION", "CUADERNO", "CASA", "PALA", "ARBOL", "PUENTE", "PAPEL", "HOJA", "HELICOPTERO", "BARCO", "GOLF", "CARRERA",
        "TUBERIA", "PLOMERO", "FUTBOL", "BALONCESTO", "ESTADIO", "JEAN", "FUENTE", "LEOPARDO", "REGLA", "PRIMERO", "SEGUNDO",
        "BLUSA", "CAMISA", "AGUA", "FUEGO", "INDUSTRIA", "AIRE", "TIERRA", "NATURALEZA", "MIERCOLES", "FOTOGRAFIA", "LEON",
        "TIGRE"};

    public char[] palabra_secreta;

    public ClientCommunication(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        serveClient(socket);
    }

    public String Random() {
        int num = (int) (Math.random() * (diccionario.length));
        return diccionario[num];
    }

    private void serveClient(Socket socket) {
        BufferedReader br = null;
        this.palabra_secreta = Random().toCharArray();
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            Scanner sc = new Scanner(System.in);

            String line;
            String lineToSendToClient;
            int score = 0;

            do {
                line = br.readLine();
                if (line.toUpperCase().contains(String.valueOf(this.palabra_secreta))) {
                    System.out.println("True");
                    this.palabra_secreta = Random().toCharArray();
                    score++;
                } else {
                    System.out.println("False");
                    score--;
                }
                
                if(score<0){
                    score= 0;
                }

             

                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    System.out.println(palabra_secreta);
                    dos.writeUTF(String.valueOf(palabra_secreta));

                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                bw.write(" El score es: " + score + ".");
                bw.newLine();
                bw.flush();
            } while (line != "FIN");

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
