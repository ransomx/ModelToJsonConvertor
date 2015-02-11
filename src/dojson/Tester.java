/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import java.util.Date;

/**
 *
 * @author Dominik
 */
public class Tester {

    public static void main(String[] args) {
        JSONBroker broker = new JSONBroker();
        ObjectFactory factory = new ObjectFactory();
        
        MessageCo mesCo = 
                factory.createMessage(
                        "+420 775 414 547",
                        "+420 112 445 778",
                        "nooord meeeaaaad", 
                        new Date());
        
        DeviceCo devCo = factory.createDevice(
                "+420 775 414 547");

        ContactCo conCo = factory.createContact(
                "alfa@gmail.com",
                "Martin",
                "Luther",
                "Cartn",
                "+478 542 112");
        
        
        MsgPack packedContact = factory.createMsgPack(conCo, conCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.MES);
        MsgPack packedDevice = factory.createMsgPack(devCo, devCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.DEV);
        MsgPack packedMessage = factory.createMsgPack(mesCo, mesCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.CON);
        
        String encodedMessage = broker.messageToJson(packedMessage);
        String encodedContact = broker.contactToJson(packedContact);
        String encodedDevice = broker.deviceToJson(packedDevice);
        
        System.out.println(encodedMessage);
        System.out.println(encodedContact);
        System.out.println(encodedDevice);
        
        MsgPack extractedPack1 = broker.extractObject(encodedMessage);
        MsgPack extractedPack2 = broker.extractObject(encodedContact);
        MsgPack extractedPack3 = broker.extractObject(encodedDevice);
        
        recognize(extractedPack1);
        recognize(extractedPack2);
        recognize(extractedPack3);  
    }
    
    public static void recognize(MsgPack extractedPack){
        switch(extractedPack.getObjectType()){
            case DEV: System.out.println("Device found"); break;
            case MES: System.out.println("Message found"); break;
            case CON: System.out.println("Contact found"); break;
        }
    }

}
