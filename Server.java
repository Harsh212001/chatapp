import java.io.*;
import java.net.*;

//ip-192.168.1.7
class Server
{
ServerSocket server;
Socket socket;

BufferedReader br;
PrintWriter out;
 //constructor
    public Server(){
  try {
    server =new ServerSocket(2177);
    System.out.println("server is ready toaccept connection ");
    System.out.println("waiting");
    socket=server.accept();
br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

out=new PrintWriter(socket.getOutputStream());

startReading();
startWriting();

} catch (IOException e) {

    e.printStackTrace();
}
    }





    public void startReading() {
        Runnable r1=()->{
System.out.println("reader stared");
try{
    while(true){
     
    String msg=br.readLine();
    if(msg.equals("exit")){
    System.out.println("Server Terminated The Chat");
    socket.close();
    break;
    }
    System.out.println("Client : "+msg);

}
}
catch(Exception e){
//e.printStackTrace();
System.out.println("Connection Closed");  
    }

        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2=()->{
            System.out.println("Writer Started..");
           try{
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
        catch(Exception e){
            //e.printStackTrace();
            System.out.println("Connection Closed");
        }
        };
        new Thread(r2).start();
    }






    public static void main(String[] args) {
      System.out.println("Harsh");
      new Server();
    }
}