import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public class Commands {

    private Integer Note;
    private HashMap<Integer, Color> ledsNumber;

    String ledsNumberToString(){
        StringBuilder temp = new StringBuilder();
        ledsNumber.forEach((led,color) -> temp.append(led).append("-").append(color.toString()).append(" "));
        return temp.toString();
    }
}
