package com.deadlast.entities;

public class Player extends Entity {

	private int healthStat;
	private int speedStat;
	private int strengthStat;
	
	public int currentHealth;
	public int currentSpeed;
	public int currentStrength;
	
	private String attackType;
	
	public Player(String name, int scoreValue) {
		super(name, scoreValue);
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
	public void setCurrentStrength(int currentStrength) {
		this.currentStrength = currentStrength;
	}
	
	public void resetStats() {
		this.currentHealth = healthStat;
		this.currentSpeed = speedStat;
		this.currentStrength = strengthStat;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
