package cn.yw.socket.heart;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *Java Socket通信之心跳包的创建实例

 1.什么是心跳包？

 　　心跳包就是在客户端和服务器间定时通知对方自己状态的一个自己定义的命令字，按照一定的时间间隔发送，类似于心跳，所以叫做心跳包。

 　　用来判断对方（设备，进程或其它网元）是否正常运行，采用定时发送简单的通讯包，如果在指定时间段内未收到对方响应，则判断对方已经离线。用于检测TCP的异常断开。基本原因是服务器端不能有效的判断客户端是否在线，也就是说，服务器无法区分客户端是长时间在空闲，还是已经掉线的情况。所谓的心跳包就是客户端定时发送简单的信息给服务器端告诉它我还在而已。代码就是每隔几分钟发送一个固定信息给服务端，服务端收到后回复一个固定信息如果服务端几分钟内没有收到客户端信息则视客户端断开。

 　　比如有些通信软件长时间不使用，要想知道它的状态是在线还是离线就需要心跳包，定时发包收包。发包方：可以是客户也可以是服务端，看哪边实现方便合理，一般是客户端。服务器也可以定时发心跳下去。一般来说，出于效率的考虑，是由客户端主动向服务器端发包，而不是服务器向客户端发。客户端每隔一段时间发一个包，使用TCP的，用send发，使用UDP的，用sendto发，服务器收到后，就知道当前客户端还处于“活着”的状态，否则，如果隔一定时间未收到这样的包，则服务器认为客户端已经断开，进行相应的客户端断开逻辑处理。
 */
public class Server extends Thread{
    private ServerSocket server = null;
    Object obj = new Object();
    @Override
    public void run() {
        try{
            while(true){
                server = new ServerSocket(25535);
                Socket client = server.accept();
                synchronized(obj){
                    new Thread(new Client(client)).start();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 客户端线程
     * @author USER
     *
     */
    class Client implements Runnable{
        Socket client;
        public Client(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                while(true){
                    ObjectInput in = new ObjectInputStream(client.getInputStream());
                    Entity entity = (Entity)in.readObject();
                    System.out.println(entity.getName());
                    System.out.println(entity.getSex());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     *程序的入口main方法
     * @param args
     */
    public static void main(String[] args){
        new Server().start();
    }
}