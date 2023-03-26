import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
class ToSave {
    private Integer ledNumber;
    private List<Commands> commands;
}