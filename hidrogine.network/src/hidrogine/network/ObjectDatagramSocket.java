package hidrogine.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class ObjectDatagramSocket {

    private DatagramSocket socket;

    private byte buffer[] = new byte[20000];
    private DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    private InetAddress endAddress;
    private int endPort;

        
    public ObjectDatagramSocket(int port) {
        try {
            socket = new DatagramSocket(port);
            endPort = port;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObjectDatagramSocket(String ip, int port) {
        try {
            socket = new DatagramSocket();
            endAddress = InetAddress.getByName(ip);
            endPort = port;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Serializable receive() {
        try {
            Arrays.fill(buffer, (byte) 0);
            socket.receive(packet);
            endAddress = packet.getAddress();
            endPort = packet.getPort();
            return decompress(packet.getData());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(final Serializable obj) {
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] bytes = compress(obj);
                    System.out.println("Sending " + bytes.length + " bytes of data.");
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length, endAddress, endPort);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thr.start();
    }

    private static byte[] compress(Serializable obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(bos);
        ObjectOutputStream ous = new ObjectOutputStream(zos);
        ous.writeObject(obj);
        zos.finish();
        bos.flush();
        return bos.toByteArray();
    }

    private static Serializable decompress(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        GZIPInputStream gzip = new GZIPInputStream(bais);
        ObjectInputStream ois = new ObjectInputStream(gzip);
        return (Serializable) ois.readObject();
    }


    public void close() {
        socket.close();
    }

}
