import java.util.HashMap;

class MidiCommands {

    private HashMap<Integer,String> param1 = new HashMap<>();

    HashMap<Integer, String> getParam1() {
        return param1;
    }

    Object getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    MidiCommands(){

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

}
