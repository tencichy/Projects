package com.damiskot;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class portScan {
    public String scan(){
        String port = "";
        CommPortIdentifier serialPortId;
        Enumeration enumComm;

        enumComm = CommPortIdentifier.getPortIdentifiers();
        while (enumComm.hasMoreElements()) {
            serialPortId = (CommPortIdentifier) enumComm.nextElement();
            if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                port = serialPortId.getName();
            }
        }
        return port;
    }
}
