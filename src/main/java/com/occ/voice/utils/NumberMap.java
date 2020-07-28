package com.occ.voice.utils;

public enum NumberMap {

 FIRST(1) , SECOND(2), THIRD(3), FOURTH(4),
 FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8), NINTH(9),
 TENTH(10), ELEVENTH(11), TWELVETH(12), THIRTEENTH(13);
	
 private int value;  
	
 private NumberMap(int value){  
  this.value=value;  
 }

public int getValue() {
	return value;
}  
	
}
