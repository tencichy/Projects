package com.damiskot;


import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class settingsUdp{


    static Enumeration portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static OutputStream outputStream;
    static portScan scan = new portScan();


    private JPanel panel1;
    private JTabbedPane tabs;
    private JPanel settingsPanel;
    private JPanel helpPanel;
    private JLabel welcomeLabel;
    private JLabel ipAddressLabel;
    private JFormattedTextField ipTextField;
    private JLabel portLabel;
    private JFormattedTextField portTextField;
    private JButton startButton;
    private JLabel statusLabel;
    private JLabel statL;
    private JButton connectToArduinoButton;
    private JLabel firstQLabel;
    private JLabel firstALabel;
    private JLabel firstALabel2;

    private boolean conToArduino = false;
    private int port = 0;
    private boolean isActive = false;
    private InetAddress ipAddress;
    private boolean firstStartThread = false;
    private volatile boolean stop = false;




    final Thread threadSend = new Thread(){
        public void run(){
            try {
                        sendPackets();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    }
        }
    };

    public settingsUdp(){
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conToArduino) {
                    if (!isActive) {
                        if(isCorrect()){
                            isActive = true;
                            stop = true;
                            if(!firstStartThread){
                                threadSend.start();
                                firstStartThread = true;
                            }
                            startButton.setText("Stop");
                            statL.setText("On");
                            statL.setForeground(Color.green);
                        }
                    } else {
                        isActive = false;
                        stop = false;
                        startButton.setText("Start");
                        statL.setText("Off");
                        statL.setForeground(Color.red);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please connect with Arduino!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        connectToArduinoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = scan.scan();
                if(port.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please connect Arduino to computer!", "Error!", JOptionPane.ERROR_MESSAGE);
                }else{
                    portList = CommPortIdentifier.getPortIdentifiers();
                    while (portList.hasMoreElements()) {
                        portId = (CommPortIdentifier) portList.nextElement();
                        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                            if (portId.getName().equals(port)) {

                                try {
                                    serialPort = (SerialPort)
                                            portId.open("Serial", 2000);
                                } catch (PortInUseException e1) {JOptionPane.showMessageDialog(null,e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);}
                                try {
                                    outputStream = serialPort.getOutputStream();
                                } catch (IOException e1) {JOptionPane.showMessageDialog(null,e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);}
                                try {
                                    serialPort.setSerialPortParams(115200,
                                            SerialPort.DATABITS_8,
                                            SerialPort.STOPBITS_1,
                                            SerialPort.PARITY_NONE);
                                } catch (UnsupportedCommOperationException e1) {JOptionPane.showMessageDialog(null,e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);}
                            }
                        }
                    }
                    conToArduino = true;
                    connectToArduinoButton.setEnabled(false);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LFS Outgauage");
        frame.setContentPane(new settingsUdp().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void sendPackets() throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(port,ipAddress);
        byte[] receiveData = new byte[1024];
        while(true) {
            if(stop){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                byte dane[] = receivePacket.getData();
                int bits = 0;
                int bits2 = 0;
                int idx = 12;
                int idx2 = 16;
                for (int shiftBy = 0; shiftBy < 4; shiftBy++) {
                    bits |= ((int) dane[idx++] & 0xFF) << (shiftBy * 8);
                }
                for (int shiftBy = 0; shiftBy < 4; shiftBy++) {
                    bits2 |= ((int) dane[idx2++] & 0xFF) << (shiftBy * 8);
                }
                String rpm = String.valueOf(Math.round(Float.intBitsToFloat(bits2)));
                String gear = String.valueOf(Math.round(dane[10]));
                String speed = String.valueOf(Math.round(Float.intBitsToFloat(bits) * 3.6f));
                //outputStream.write(speed.getBytes());
                //outputStream.write('s');
                outputStream.write(rpm.getBytes());
                //outputStream.write('r');
                //outputStream.write(gear.getBytes());
                //outputStream.write('g');
            }
        }
    }

    public boolean isCorrect(){
        boolean correct = true;
        if (ipTextField.getText().isEmpty()) {
            correct = false;
            JOptionPane.showMessageDialog(null, "IP field is empty!", "Error!", JOptionPane.ERROR_MESSAGE);

        } else if (portTextField.getText().isEmpty()) {
            correct = false;
            JOptionPane.showMessageDialog(null, "Port field is empty!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            ipAddress = InetAddress.getByName(ipTextField.getText());
        } catch (UnknownHostException e1) {
            correct = false;
            JOptionPane.showMessageDialog(null, "Please enter correct IP Address!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            port = Integer.valueOf(portTextField.getText());
        } catch (Exception e2) {
            correct = false;
            JOptionPane.showMessageDialog(null, "Port: " + e2.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
        if(correct){
            return true;
        }else {
            return false;
        }
    }

}



