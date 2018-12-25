package Music;

import java.util.Scanner;
import java.io.PrintWriter;

public class MusicReader {

	// Scanner kb = new Scanner (System.in);

	public static void main(String args[]) {

		MUI window = new MUI();
		window.setVisible(true);
		return;
	}

	public static void analyzeFile(Scanner sc, PrintWriter pw1, PrintWriter pw2, PrintWriter pw3) {

		// System.out.println("How many entries are in the file?");
		int x = 35000; // x has to be bigger than the total number of song
						// entries

		MusicFile musicLib[] = new MusicFile[x];

		int i = 0;

		while (sc.hasNextLine()) {
			musicLib[i] = new MusicFile(sc);

			sc.nextLine();
			i++;

			if (i % 1000 == 0)
				System.out.println(i + " entries read");
		}

		System.out.println(i + " song entries read. Input processed.");
		System.out.println("Now analyzing...");

		// rating results array
		int ratingSongs[][] = new int[6][3];
		for (int j = 0; j < 6; j++) {
			ratingSongs[j][0] = j;
		}

		// year results array
		int yearSongs[][] = new int[100][3];
		for (int j = 0; j < 100; j++) {
			yearSongs[j][0] = j + 1950;
		}

		// length results array
		int lengthSongs[][] = new int[601][3];
		for (int j = 0; j < 600; j++) {
			lengthSongs[j][0] = j;
		}
		lengthSongs[600][0] = 600;

		// artist result arrays
		String artistResults[] = new String[i];
		int artistPlays[] = new int[i];
		int artistCount[] = new int[i];
		// album result arrays
		String albumResults[] = new String[i];
		int albumPlays[] = new int[i];
		// genre result arrays
		String genreResults[] = new String[i];
		int genrePlays[] = new int[i];
		int genreCount[] = new int[i];

		int albumIndex = 0;
		int albumIndexTemp = 0;
		int artistIndex = 0;
		int artistIndexTemp = 0;
		int genreIndex = 0;
		int genreIndexTemp = 0;

		int totalPlays = 0;
		int totalLength = 0;
		long totalListen = 0;

		for (int j = 0; j < i; j++) {

			// total play+ song informations
			totalPlays = totalPlays + musicLib[j].getPlays();
			totalLength = totalLength + musicLib[j].getLength();
			totalListen = totalListen + musicLib[j].getLength() * musicLib[j].getPlays();

			// rating data, already sorted
			for (int k = 0; k < ratingSongs.length; k++) {
				if (ratingSongs[k][0] * 20 == musicLib[j].getRating()) {
					ratingSongs[k][1] = ratingSongs[k][1] + musicLib[j].getPlays();
					ratingSongs[k][2]++;
				}
			}

			// year data, already sorted
			for (int k = 0; k < yearSongs.length; k++) {
				if (yearSongs[k][0] == musicLib[j].getYear()) {
					yearSongs[k][1] = yearSongs[k][1] + musicLib[j].getPlays();
					yearSongs[k][2]++;
				}
			}

			// length data, already sorted
			for (int k = 0; k < lengthSongs.length; k++) {
				if (lengthSongs[k][0] == musicLib[j].getLength()) {
					lengthSongs[k][1] = lengthSongs[k][1] + musicLib[j].getPlays();
					lengthSongs[k][2]++;
				}
			}

			if (musicLib[j].getLength() > 600) {
				lengthSongs[600][1] = lengthSongs[600][1] + musicLib[j].getPlays();
				lengthSongs[600][2]++;
			}

			// artist, genre, and album data
			boolean isUniqueArtist = true;
			boolean isUniqueAlbum = true;
			boolean isUniqueGenre = true;

			for (int k = 0; k < i; k++) {

				if (musicLib[j].getArtist().equalsIgnoreCase(artistResults[k])) {
					isUniqueArtist = false;
					artistIndexTemp = k;

				}

				if (musicLib[j].getAlbum().equalsIgnoreCase(albumResults[k])) {
					isUniqueAlbum = false;
					albumIndexTemp = k;
				}

				if (musicLib[j].getGenre().equalsIgnoreCase(genreResults[k])) {
					isUniqueGenre = false;
					genreIndexTemp = k;
				}
			}

			if (isUniqueArtist) {
				artistResults[artistIndex] = musicLib[j].getArtist();
				artistPlays[artistIndex] = musicLib[j].getPlays();

				artistCount[artistIndex] = artistCount[artistIndex] + 1;

				artistIndex++;
			}

			if (!isUniqueArtist) {
				artistPlays[artistIndexTemp] = artistPlays[artistIndexTemp] + musicLib[j].getPlays();
				artistCount[artistIndexTemp] = artistCount[artistIndexTemp] + 1;
			}

			if (isUniqueAlbum) {
				albumResults[albumIndex] = musicLib[j].getAlbum();
				albumPlays[albumIndex] = musicLib[j].getPlays();

				albumIndex++;
			}

			if (!isUniqueAlbum) {
				albumPlays[albumIndexTemp] = albumPlays[albumIndexTemp] + musicLib[j].getPlays();
			}

			if (isUniqueGenre) {
				genreResults[genreIndex] = musicLib[j].getGenre();
				genrePlays[genreIndex] = musicLib[j].getPlays();

				genreCount[genreIndex] = genreCount[genreIndex] + 1;

				genreIndex++;
			}

			if (!isUniqueGenre) {
				genrePlays[genreIndexTemp] = genrePlays[genreIndexTemp] + musicLib[j].getPlays();
				genreCount[genreIndexTemp] = genreCount[genreIndexTemp] + 1;
			}

		}

		System.out.println("Sorting...");
		String[][] sortedArtists = sortArray(artistPlays, artistResults, artistCount);
		String[][] sortedAlbums = sortArray(albumPlays, albumResults);
		String[][] sortedGenres = sortArray(genrePlays, genreResults, genreCount);

		pw1.println("Artists");
		pw1.println("Artist \tPlays \tSong count");
		pw1.println();

		for (int j = 0; j < artistIndex; j++) {
			pw1.print(sortedArtists[j][0] + "\t");
			pw1.print(sortedArtists[j][1] + "\t");
			pw1.println(sortedArtists[j][2]);
		}
		pw1.println();

		pw2.println("Albums");
		pw2.println("Album \t Plays");
		pw2.println();

		for (int j = 0; j < albumIndex; j++) {
			pw2.print(sortedAlbums[j][0] + "\t");
			pw2.println(sortedAlbums[j][1]);
		}

		pw3.println("Total songs: " + i);
		pw3.println("Total library length: " + totalLength + "s");
		pw3.println();
		pw3.println("Total Plays: " + totalPlays);
		pw3.println("Total Playtime: " + totalListen + "s");
		pw3.println();
		pw3.println("Average song length: " + totalLength / (double) i + "s");
		pw3.println("Preferred song length: " + totalListen / (double) totalPlays + "s");
		pw3.println();

		pw3.println("Genres");
		pw3.println();
		pw3.println("Genre \t Plays \t Song count");

		for (int j = 0; j < genreIndex; j++) {
			pw3.print(sortedGenres[j][0] + "\t");
			pw3.print(sortedGenres[j][1] + "\t");
			pw3.println(sortedGenres[j][2]);
		}

		pw3.println();
		pw3.println();

		pw3.println("Years");
		pw3.println();
		pw3.println("Year \t Plays \t Song count");

		for (int j = 0; j < 70; j++) {
			pw3.print(yearSongs[j][0] + "\t");
			pw3.print(yearSongs[j][1] + "\t");
			pw3.println(yearSongs[j][2]);
		}

		pw3.println();
		pw3.println();

		pw3.println("Length");
		pw3.println();
		pw3.println("Length \t Plays \t Song count");

		for (int j = 0; j < lengthSongs.length; j++) {
			pw3.print(lengthSongs[j][0] + "\t");
			pw3.print(lengthSongs[j][1] + "\t");
			pw3.println(lengthSongs[j][2]);
		}

		pw3.println();
		pw3.println();

		pw3.println("Ratings");
		pw3.println();
		pw3.println("Rating \t Plays \t Song count");

		for (int j = 0; j < ratingSongs.length; j++) {
			pw3.print(ratingSongs[j][0] + "\t");
			pw3.print(ratingSongs[j][1] + "\t");
			pw3.println(ratingSongs[j][2]);
		}

		System.out.println("Done.");
	}

	private static String[][] sortArray(int[] plays, String[] category) {
		String sortedResults[][] = new String[plays.length][2];
		int holder = 0;
		String temp = null;

		while (!sorted(plays)) {
			for (int i = 0; i < plays.length - 1; i++) {
				if (plays[i] < plays[i + 1]) {
					temp = category[i];
					holder = plays[i];
					category[i] = category[i + 1];
					plays[i] = plays[i + 1];
					category[i + 1] = temp;
					plays[i + 1] = holder;

				}
			}

		}

		for (int i = 0; i < plays.length; i++) {

			sortedResults[i][0] = category[i];
			sortedResults[i][1] = plays[i] + "";
		}

		return sortedResults;
	}

	private static boolean sorted(int[] plays) {

		for (int i = 0; i < plays.length - 1; i++) {
			if (plays[i] < plays[i + 1]) {
				return false;
			}
		}

		return true;
	}

	private static String[][] sortArray(int[] plays, String[] category, int[] count) {
		String sortedResults[][] = new String[plays.length][3];
		int holder = 0;
		int holder2 = 0;
		String temp = null;

		while (!sorted(plays)) {
			for (int i = 0; i < plays.length - 1; i++) {
				if (plays[i] < plays[i + 1]) {
					temp = category[i];
					holder = plays[i];
					holder2 = count[i];

					category[i] = category[i + 1];
					plays[i] = plays[i + 1];
					count[i] = count[i + 1];

					category[i + 1] = temp;
					plays[i + 1] = holder;
					count[i + 1] = holder2;

				}
			}

		}

		for (int i = 0; i < plays.length; i++) {

			sortedResults[i][0] = category[i];
			sortedResults[i][1] = plays[i] + "";
			sortedResults[i][2] = count[i] + "";
		}

		return sortedResults;
	}

}
