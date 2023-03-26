import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class DataToSave {

    private String comPort;
    private Integer baudrate;
    private Integer databits;
    private String stopbits;
    private String parity;

    private String telemetryApiUrl;

    private ArrayList<CheckBoxTreeItemToJson> checkedItems;

    private Integer timeInterval;

}
