import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.*;

@Data
@AllArgsConstructor
class Song {
    private String title;
    private int year;
    private ArrayList<String> singers;

    public static void main(String[] args) {
        ArrayList<Song> songs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\vikah\\IdeaProjects\\LR5.1\\src\\main\\java\\org\\example\\5-3.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(", ");
                Song song = new Song(parts[0], Integer.parseInt(parts[1]), new ArrayList<>(Arrays.asList(parts).subList(2, parts.length)));
                songs.add(song);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Song earliestSong = songs.getFirst();
        for (Song song : songs) {
            if (song.getYear() < earliestSong.getYear()) {
                earliestSong = song;
            }
        }

        ArrayList<Song> duetSongs = new ArrayList<>();
        for (Song song : songs){
            if (song.getSingers().size() == 2){
                duetSongs.add(song);
            }
    }

        Map<String,Integer> singerSongCount = new HashMap<>();
        for (Song song : songs){
            for (String singer : song.getSingers()){
                singerSongCount.put(singer, singerSongCount.getOrDefault(singer, 0) + 1);
            }
        }
        String singerWithTheMostSongs = Collections.max(singerSongCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        ArrayList<Song> topSingerSongs = new ArrayList<>();
        for (Song song : songs){
            if (song.getSingers().contains(singerWithTheMostSongs)){
                topSingerSongs.add(song);
            }
            Collections.sort(topSingerSongs, (song1, song2) -> Integer.compare(song1.getYear(), song2.getYear()));
        }

        try (PrintWriter writer = new PrintWriter("results.txt")){
            writer.println("Песня, выпущенная раньше остальных:\n" + "Название: " + earliestSong.getTitle() + "\nГод: " + earliestSong.getYear()+ "\nИсполнитель(и):");
            for (String singer : earliestSong.getSingers()) {
                writer.println(singer);
            }
            writer.println("\nКоллекция песен, которые исполняются дуэтом: \n" + duetSongs);
            writer.println("\nИсполнитель с наибольшим количеством печен: " + singerWithTheMostSongs);
            writer.println("\nСписок песен певца " + singerWithTheMostSongs + " по годам "  + ":\n" + topSingerSongs);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error writing to file.");
        }
    }
}