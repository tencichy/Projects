import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Color {

    private Integer red;
    private Integer green;
    private Integer blue;

    @Override
    public String toString(){
        return "RGB: " + "[" + red + "," + green + "," + blue + "]";
    }

}
