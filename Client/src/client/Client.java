package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PORT = 40080;
        final String HOST = "192.168.0.33";
        
        try {
            Socket socket = new Socket(HOST, PORT);
            
            comunicateWithServer(socket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void comunicateWithServer(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            Scanner sc = new Scanner(System.in);
            String line, lineFromServer, name;
            System.out.println("What's your name?: ");
                name = sc.nextLine();
            do{
                
                System.out.println("Press enter to start. ");
                line = sc.nextLine();
                bw.write(name + " said: " + line);
                bw.newLine();
                bw.flush();
                
                lineFromServer = br.readLine();
                System.out.println("Make a phrase with  " + lineFromServer+ ".");
            }while (line != "FIN");
            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
     
    
}
