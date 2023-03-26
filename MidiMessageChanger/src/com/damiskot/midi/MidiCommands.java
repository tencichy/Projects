package com.damiskot.midi;

import java.util.HashMap;

public class MidiCommands {

    private HashMap<Integer,String> command = new HashMap<>();
    private HashMap<Integer,String> param1 = new HashMap<>();
    private HashMap<Integer,Integer> channel = new HashMap<>();

    HashMap<Integer, Integer> getChannel() {
        return channel;
    }

    public HashMap<Integer, String> getParam1() {
        return param1;
    }

    public Object getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public MidiCommands(){

        channel.put(-112,0);
        channel.put(-128,0);
        channel.put(-32,0);
        channel.put(-80,0);

        channel.put(-111,1);
        channel.put(-127,1);
        channel.put(-31,1);
        channel.put(-79,1);

        channel.put(-110,2);
        channel.put(-126,2);
        channel.put(-30,2);
        channel.put(-78,2);

        channel.put(-109,3);
        channel.put(-125,3);
        channel.put(-29,3);
        channel.put(-77,3);

        channel.put(-108,4);
        channel.put(-124,4);
        channel.put(-28,4);
        channel.put(-76,4);

        channel.put(-107,5);
        channel.put(-123,5);
        channel.put(-27,5);
        channel.put(-75,5);

        channel.put(-106,6);
        channel.put(-122,6);
        channel.put(-26,6);
        channel.put(-74,6);

        channel.put(-105,7);
        channel.put(-121,7);
        channel.put(-25,7);
        channel.put(-73,7);

        channel.put(-104,8);
        channel.put(-120,8);
        channel.put(-24,8);
        channel.put(-72,8);

        channel.put(-103,9);
        channel.put(-119,9);
        channel.put(-23,9);
        channel.put(-71,9);

        channel.put(-102,10);
        channel.put(-118,10);
        channel.put(-22,10);
        channel.put(-70,10);

        channel.put(-101,11);
        channel.put(-117,11);
        channel.put(-21,11);
        channel.put(-69,11);

        channel.put(-100,12);
        channel.put(-116,12);
        channel.put(-20,12);
        channel.put(-68,12);

        channel.put(-99,13);
        channel.put(-115,13);
        channel.put(-19,13);
        channel.put(-67,13);

        channel.put(-98,14);
        channel.put(-114,14);
        channel.put(-18,14);
        channel.put(-66,14);

        channel.put(-97,15);
        channel.put(-113,15);
        channel.put(-17,15);
        channel.put(-65,15);

        command.put(-112,"Channel 1: Note On");
        command.put(-128, "Channel 1: Note Off");
        command.put(-32, "Channel 1: Pitch Bend");
        command.put(-80, "Channel 1: Control Change");

        command.put(-111,"Channel 2: Note On");
        command.put(-127, "Channel 2: Note Off");
        command.put(-31, "Channel 2: Pitch Bend");
        command.put(-79, "Channel 2: Control Change");

        command.put(-110,"Channel 3: Note On");
        command.put(-126, "Channel 3: Note Off");
        command.put(-30, "Channel 3: Pitch Bend");
        command.put(-78, "Channel 3: Control Change");

        command.put(-109,"Channel 4: Note On");
        command.put(-125, "Channel 4: Note Off");
        command.put(-29, "Channel 4: Pitch Bend");
        command.put(-77, "Channel 4: Control Change");

        command.put(-108,"Channel 5: Note On");
        command.put(-124, "Channel 5: Note Off");
        command.put(-28, "Channel 5: Pitch Bend");
        command.put(-76, "Channel 5: Control Change");

        command.put(-107,"Channel 6: Note On");
        command.put(-123, "Channel 6: Note Off");
        command.put(-27, "Channel 6: Pitch Bend");
        command.put(-75, "Channel 6: Control Change");

        command.put(-106,"Channel 7: Note On");
        command.put(-122, "Channel 7: Note Off");
        command.put(-26, "Channel 7: Pitch Bend");
        command.put(-74, "Channel 7: Control Change");

        command.put(-105,"Channel 8: Note On");
        command.put(-121, "Channel 8: Note Off");
        command.put(-25, "Channel 8: Pitch Bend");
        command.put(-73, "Channel 8: Control Change");

        command.put(-104,"Channel 9: Note On");
        command.put(-120, "Channel 9: Note Off");
        command.put(-24, "Channel 9: Pitch Bend");
        command.put(-72, "Channel 9: Control Change");

        command.put(-103,"Channel 10: Note On");
        command.put(-119, "Channel 10: Note Off");
        command.put(-23, "Channel 10: Pitch Bend");
        command.put(-71, "Channel 10: Control Change");
        command.put(-103, "Channel 10: Pad On");
        command.put(-119, "Channel 10: Pad Off");

        command.put(-102,"Channel 11: Note On");
        command.put(-118, "Channel 11: Note Off");
        command.put(-22, "Channel 11: Pitch Bend");
        command.put(-70, "Channel 11: Control Change");

        command.put(-101,"Channel 12: Note On");
        command.put(-117, "Channel 12: Note Off");
        command.put(-21, "Channel 12: Pitch Bend");
        command.put(-69, "Channel 12: Control Change");

        command.put(-100, "Channel 13: Note On");
        command.put(-116, "Channel 13: Note Off");
        command.put(-20, "Channel 13: Pitch Bend");
        command.put(-68, "Channel 13: Control Change");

        command.put(-99,"Channel 14: Note On");
        command.put(-115, "Channel 14: Note Off");
        command.put(-19, "Channel 14: Pitch Bend");
        command.put(-67, "Channel 14: Control Change");

        command.put(-98,"Channel 15: Note On");
        command.put(-114, "Channel 15: Note Off");
        command.put(-18, "Channel 15: Pitch Bend");
        command.put(-66, "Channel 15: Control Change");

        command.put(-97,"Channel 16: Note On");
        command.put(-113, "Channel 16: Note Off");
        command.put(-17, "Channel 16: Pitch Bend");
        command.put(-65, "Channel 16: Control Change");


        //Define notes
        param1.put(21 , "A0");
        param1.put(22 , "A#0");
        param1.put(23 , "H0");
        param1.put(24 , "C1");
        param1.put(25 , "C#1");
        param1.put(26 , "D1");
        param1.put(27 , "D#1");
        param1.put(28 , "E1");
        param1.put(29 , "F1");
        param1.put(30 , "F#1");
        param1.put(31 , "G1");
        param1.put(32 , "G#1");
        param1.put(33 , "A1");
        param1.put(34 , "A#1");
        param1.put(35 , "H1");
        param1.put(36 , "C2");
        param1.put(37 , "C#2");
        param1.put(38 , "D2");
        param1.put(39 , "D#2");
        param1.put(40 , "E2");
        param1.put(41 , "F2");
        param1.put(42 , "F#2");
        param1.put(43 , "G2");
        param1.put(44 , "G#2");
        param1.put(45 , "A2");
        param1.put(46 , "A#2");
        param1.put(47 , "H2");
        param1.put(48 , "C3");
        param1.put(49 , "C#3");
        param1.put(50 , "D3");
        param1.put(51 , "D#3");
        param1.put(52 , "E3");
        param1.put(53 , "F3");
        param1.put(54 , "F#3");
        param1.put(55 , "G3");
        param1.put(56 , "G#3");
        param1.put(57 , "A3");
        param1.put(58 , "A#3");
        param1.put(59 , "H3");
        param1.put(60 , "C4");
        param1.put(61 , "C#4");
        param1.put(62 , "D4");
        param1.put(63 , "D#4");
        param1.put(64 , "E4");
        param1.put(65 , "F4");
        param1.put(66 , "F#4");
        param1.put(67 , "G4");
        param1.put(68 , "G#4");
        param1.put(69 , "A4");
        param1.put(70 , "A#4");
        param1.put(71 , "H4");
        param1.put(72 , "C5");
        param1.put(73 , "C#5");
        param1.put(74 , "D5");
        param1.put(75 , "D#5");
        param1.put(76 , "E5");
        param1.put(77 , "F5");
        param1.put(78 , "F#5");
        param1.put(79 , "G5");
        param1.put(80 , "G#5");
        param1.put(81 , "A5");
        param1.put(82 , "A#5");
        param1.put(83 , "H5");
        param1.put(84 , "C6");
        param1.put(85 , "C#6");
        param1.put(86 , "D6");
        param1.put(87 , "D#6");
        param1.put(88 , "E6");
        param1.put(89 , "F6");
        param1.put(90 , "F#6");
        param1.put(91 , "G6");
        param1.put(92 , "G#6");
        param1.put(93 , "A6");
        param1.put(94 , "A#6");
        param1.put(95 , "H6");
        param1.put(96 , "C7");
        param1.put(97 , "C#7");
        param1.put(98 , "D7");
        param1.put(99 , "D#7");
        param1.put(100 , "E7");
        param1.put(101 , "F7");
        param1.put(102 , "F#7");
        param1.put(103 , "G7");
        param1.put(104 , "G#7");
        param1.put(105 , "A7");
        param1.put(106 , "A#7");
        param1.put(107 , "H7");
        param1.put(108 , "C8");

    }

    String checkCom(String[] table){
        //TODO: Check table length. It's not always 3
        int command = Integer.valueOf(table[0]);
        int param1 = Integer.valueOf(table[1]);
        int param2 = Integer.valueOf(table[2]);
        String commandS = "";
        String param1S = "";
        String param2S = "";
        if(command < -96 && command > -129){
            commandS =  "Command: " + this.command.get(command);
            //TODO: Check on other devices if note that you hit is equals to this note \/
            param1S = "Note: " + this.param1.get(param1+12);
            param2S = "Velocity: " + param2;
        }else if(command < -64 && command > -81){
            commandS =  "Command: " + this.command.get(command);
            param1S = "CC Number: " + param1;
            param2S = "Value: " + param2;
        }else if(command < -16 && command > -33){
            commandS =  "Command: " + this.command.get(command);
            param1S = "???: " + param1;
            param2S = "Value: " + param2;
        }
        return commandS + " " + param1S + " " + param2S;
    }

    public String getNote(Integer note){
        return this.param1.get(note+12);
    }
}
