package com.deadlast.assets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
	
	List<Entry> entries;
	
	public Scoreboard() {
		this.entries = new ArrayList<>();
	}
	
	public class Entry implements Comparable<Entry> {
		
		String name;
		int score;
		String dateTime;
		
		public Entry(String name, int score, String dateTime) {
			this.name = name;
			this.score = score;
			this.dateTime = dateTime;
		}
		
		public Entry(String line) throws IllegalArgumentException {
			String[] vars = line.split(",");
			if (vars.length != 3) {
				System.out.println("Bad argument length");
				throw new IllegalArgumentException();
			}
			this.name = vars[0];
			try {
				this.score = Integer.parseInt(vars[1]);
			} catch (NumberFormatException e) {
				System.out.println("Unacceptable score parameter");
				throw new IllegalArgumentException();
			}
			this.dateTime = vars[2];
		}
		
		public String getName() {
			return name;
		}
		
		public int getScore() {
			return score;
		}
		
		public String getScoreString() {
			return Integer.toString(score);
		}
		
		public String getDate() {
			return dateTime;
		}
		
		@Override
		public String toString() {
			return this.name + "," + this.score + "," + this.dateTime;
		}

		@Override
		public int compareTo(Entry entry) {
			return entry.getScore() - this.score;
		}
		
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	public List<Entry> getEntries() {
		Collections.sort(entries);
		return entries;
	}

}
