import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
class Song {
    private String title;
    private int year;
    private ArrayList<String> singers;
}