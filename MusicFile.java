package Music;

import java.util.Scanner;

public class MusicFile {

	String title, artist, aArtist, album, genre;
	int rating, year, plays, length, bitrate;
	double size;

	public MusicFile(Scanner sc) {
		sc.useDelimiter("\t");
		setTitle(sc.next());
		sc.next(); // extra \t after title
		setArtist(sc.next());
		setAArtist(sc.next());
		setAlbum(sc.next());
		setLength(sc.next());
		setRating(sc.next());
		sc.next(); // skip heart column
		setPlays(sc.next());
		setGenre(sc.next());
		setYear(sc.next());
		setBitrate(sc.next());
		setSize(sc.next());
		sc.next(); // skips track listing #
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAArtist(String aArtist) {
		this.aArtist = aArtist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setLength(String temp) {
		if (temp.indexOf(":") != temp.lastIndexOf(":")) // in case it's in the
														// hh:mm:ss format
			length = Integer.parseInt(temp.substring(0, temp.indexOf(":"))) * 3600
					+ Integer.parseInt(temp.substring(temp.indexOf(":") + 1, temp.lastIndexOf(":"))) * 60
					+ Integer.parseInt(temp.substring(temp.lastIndexOf(":") + 1));
		else if (temp != null && !temp.equals("") && temp.contains(":"))
			length = Integer.parseInt(temp.substring(0, temp.indexOf(":"))) * 60
					+ Integer.parseInt(temp.substring(temp.indexOf(":") + 1));
		else
			length = 0;
	}

	public void setRating(String temp) {
		if (temp != null && !temp.equals(""))
			rating = Integer.parseInt(temp);
		else
			rating = 0;
	}

	public void setPlays(String temp) {
		if (temp.contains(",")) {
			plays = Integer.parseInt(temp.substring(0, temp.indexOf(","))) * 1000
					+ Integer.parseInt(temp.substring(temp.indexOf(",") + 1));
			// System.out.println("\t" + plays);
		} else if (temp != null && !temp.equals(""))
			plays = Integer.parseInt(temp);
		else
			plays = 0;
	}

	public void setYear(String temp) {
		if (temp != null && !temp.equals(""))
			year = Integer.parseInt(temp);
		else
			year = 0;
	}

	public void setBitrate(String temp) {
		if (temp.contains(",") && temp.contains("kbps")) {
			bitrate = Integer.parseInt(temp.substring(0, temp.indexOf(","))) * 1000
					+ Integer.parseInt(temp.substring(temp.indexOf(",") + 1, temp.indexOf("kbps") - 1));
		} else if (temp.contains("kbps"))
			bitrate = Integer.parseInt(temp.substring(0, temp.indexOf("kbps") - 1));
		else
			bitrate = 0;
	}

	public void setSize(String temp) {
		if (temp.contains("MB")) {
			size = Double.parseDouble(temp.substring(0, temp.indexOf("MB") - 1));
		} else if (temp.contains(",") && temp.contains("KB"))
			size = Double.parseDouble(temp.substring(0, temp.indexOf(",")))
					+ Double.parseDouble(temp.substring(temp.indexOf(",") + 1, temp.indexOf("KB") - 1)) * 0.001;
		else
			size = Double.parseDouble(temp.substring(0, temp.indexOf("KB") - 1)) * 0.1;

	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAArtist() {
		return aArtist;
	}

	public String getAlbum() {
		return album;
	}

	public String getGenre() {
		return genre;
	}

	public int getLength() {
		return length;
	}

	public int getRating() {
		return rating;
	}

	public int getPlays() {
		return plays;
	}

	public int getYear() {
		return year;
	}

	public int getBitrate() {
		return bitrate;
	}

	public double getSize() {
		return size;
	}

	public String toString() {
		return "\"" + getTitle() + "\" by " + getArtist() + " in the album: " + getAlbum() + " (" + getYear()
				+ ") Played a total amount of " + getPlays() + " times.";
	}

}
