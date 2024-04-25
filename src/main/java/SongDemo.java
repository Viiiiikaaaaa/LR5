import java.io.*;
import java.util.*;

public class SongDemo {

    public static void main(String[] args) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            songs = readSongsFromFile("C:\\Users\\vikah\\IdeaProjects\\LR5.1\\src\\main\\java\\org\\example\\5-3.txt");
            Song earliestSong = findEarliestSong(songs);
            ArrayList<Song> duetSongs = findDuetSongs(songs);
            String singerWithTheMostSongs = findSingerWithMostSongs(songs);
            ArrayList<Song> topSingerSongs = findTopSingerSongs(songs, singerWithTheMostSongs);

            writeResultsToFile(songs, earliestSong, duetSongs, singerWithTheMostSongs, topSingerSongs);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static ArrayList<Song> readSongsFromFile(String filePath) throws IOException {
        ArrayList<Song> songs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(", ");
                Song song = new Song(parts[0], Integer.parseInt(parts[1]), new ArrayList<>(Arrays.asList(parts).subList(2, parts.length)));
                songs.add(song);
            }
        }
        return songs;
    }


    private static Song findEarliestSong(ArrayList<Song> songs) {
        Song earliestSong = songs.getFirst();
        for (Song song : songs) {
            if (song.getYear() < earliestSong.getYear()) {
                earliestSong = song;
            }
        }
        return earliestSong;
    }


    private static ArrayList<Song> findDuetSongs(ArrayList<Song> songs) {
        ArrayList<Song> duetSongs = new ArrayList<>();
        for (Song song : songs) {
            Iterator<String> iterator = song.getSingers().iterator();
            if (iterator.hasNext() && iterator.next() != null && iterator.hasNext()) {
                duetSongs.add(song);
            }
        }
        return duetSongs;
    }


    private static String findSingerWithMostSongs(ArrayList<Song> songs) {
        Map<String, Integer> singerSongCount = new HashMap<>();
        for (Song song : songs) {
            for (String singer : song.getSingers()) {
                singerSongCount.put(singer, singerSongCount.getOrDefault(singer, 0) + 1);
            }
        }
        return Collections.max(singerSongCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    private static ArrayList<Song> findTopSingerSongs(ArrayList<Song> songs, String singerWithTheMostSongs) {
        ArrayList<Song> topSingerSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getSingers().contains(singerWithTheMostSongs)) {
                topSingerSongs.add(song);
            }
            topSingerSongs.sort((song1, song2) -> Integer.compare(song1.getYear(), song2.getYear()));
        }
        return topSingerSongs;
    }


    private static void writeResultsToFile(ArrayList<Song> songs, Song earliestSong, ArrayList<Song> duetSongs, String singerWithTheMostSongs, ArrayList<Song> topSingerSongs) {
        try (PrintWriter writer = new PrintWriter("results.txt")){
            writer.println("Список песен: \n" + songs + "\n");
            writer.println("Песня, выпущенная раньше остальных:\n" + "Название: " + earliestSong.getTitle() + "\nГод: " + earliestSong.getYear()+ "\nИсполнитель(и):");
            for (String singer : earliestSong.getSingers()) {
                writer.println(singer);
            }
            writer.println("\nКоллекция песен, которые исполняются дуэтом: \n" + duetSongs);
            writer.println("\nИсполнитель с наибольшим количеством печен: " + singerWithTheMostSongs);
            writer.println("\nСписок песен певца " + singerWithTheMostSongs + " по годам "  + ":\n" + topSingerSongs);
        }
        catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла");
        }
    }
}
