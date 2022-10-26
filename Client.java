import java.io.*;
import java.net.*;

import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;


public class Client extends JFrame {

Socket socket;

BufferedReader br;
PrintWriter out;

//dECALARE Components
private JLabel heading=new JLabel("client Area");
private JTextArea messageArea=new JTextArea();
private JTextField messageInput=new JTextField();
private Font font=new Font("Roboto",Font.PLAIN,20);


//constructor  


public Client(){
 
    

try {
    System.out.println("Sending request to Server");
    socket=new Socket("192.168.1.7",2177);
    System.out.println("Connection Done");
    br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

 out=new PrintWriter(socket.getOutputStream());
createGUI();
handleEvents();
startReading();
//startWriting();
} catch (Exception e) {
   
    e.printStackTrace();
}

}
private void handleEvents() {
    messageInput.addKeyListener(new KeyListener(){

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
           //  System.out.println("key reeased "+e.getKeyCode());
            if(e.getKeyCode()==10){
                //System.out.println("you Have Pressed Enter Button");
             String contentToSend=messageInput.getText();
             messageArea.append("Me :"+contentToSend+"\n");
             out.println(contentToSend);
             out.flush();
             messageInput.setText("");
             messageInput.requestFocus();
            
            }
            
        }

    });
}
/**
 * 
 */
private void  createGUI(){
//gui code
this.setTitle("Client Messager[End]");
this.setSize(900,1000);
this.setLocationRelativeTo(null);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//coding for component
heading.setFont(font);
messageArea.setFont(font);
messageInput.setFont(font);
// heading.setIcon(new ImageIcon("Harsh.jpg"));
heading.setHorizontalTextPosition(SwingConstants.CENTER);
heading.setVerticalTextPosition(SwingConstants.BOTTOM);
heading.setHorizontalAlignment(SwingConstants.CENTER);
heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
messageArea.setEditable(false);
messageInput.setHorizontalAlignment(SwingConstants.CENTER);
//frame layout
this.setLayout(new BorderLayout());
//adding the components of frame
this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messageArea);
this.add(jScrollPane,BorderLayout.CENTER);
this.add(messageInput,BorderLayout.SOUTH);



this.setVisible(true);
}
//start raeding
public void startReading() {
    Runnable r1=()->{
System.out.println("reader stared");
try{
while(true){
    
String msg=br.readLine();
if(msg.equals("exit")){
    System.out.println("Server Terminated The Chat");
    JOptionPane.showMessageDialog(this, "server terminated the chgat");
    messageInput.setEnabled(false);
    socket.close();
    break;
    }
//System.out.println("Client : "+msg);
messageArea.append("Client : "+msg+"\n");

}}
catch(Exception e){
    
    System.out.println("Connection Closed");
    }

    };
    new Thread(r1).start();
}
// start writing
public void startWriting() {
    Runnable r2=()->{
        System.out.println("Writer Started..");
        try {
        while(!socket.isClosed()){
           
                BufferedReader br1=new BufferedReader
                (new InputStreamReader(System.in));
            String content=br1.readLine();
            out.println(content);
            out.flush();
            if(content.equals("exit")){
                socket.close(); 
            }

            
        }
     
    }
    catch (Exception e) {
       // e.printStackTrace();
       System.out.println("Connection Closed"); 
    }
    };
    new Thread(r2).start();
}




    public static void main(String[] args) {
        new Client();
        System.out.println("This is Client");
    }
    
}
