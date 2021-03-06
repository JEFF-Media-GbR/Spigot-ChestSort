package de.jeff_media.chestsort.data;

import de.jeff_media.chestsort.utils.TypeMatchPositionPair;

public class Category implements Comparable<Category>{

	// Represents a sorting category
	// Includes an array of strings called typeMatches
	// A typeMatch is like a regular expression, but it only supports * as
	// placeholders
	// e.g. "DIRT" will match the typeMatch "dirt"
	// "COARSE_DIRT" will not match the typeMatch "dirt"
	// "COARSE_DIRT" will match the typeMatch "*dirt"

	public final String name;
	boolean sticky = false;
	public final TypeMatchPositionPair[] typeMatches;

	public Category(String name, TypeMatchPositionPair[] typeMatchPositionPairs) {
		this.name = name;
		this.typeMatches = typeMatchPositionPairs;
	}
	
	public void setSticky() {
		this.sticky=true;
	}
	
	boolean isSticky() {
		return this.sticky;
	}
	

	// Checks whether a the given itemname fits into this category and returns the line number. 0 means not found
	public short matches(String itemname) {

		// Very, very simple wildcard checks
		for (TypeMatchPositionPair typeMatchPositionPair : typeMatches) {
			
			boolean asteriskBefore = false;
			boolean asteriskAfter = false;
			
			String typeMatch = typeMatchPositionPair.getTypeMatch();
			if (typeMatch.startsWith("*")) {
				asteriskBefore = true;
				typeMatch = typeMatch.substring(1);
			}
			if (typeMatch.endsWith("*")) {
				asteriskAfter = true;
				typeMatch = typeMatch.substring(0, typeMatch.length() - 1);
			}

			if (!asteriskBefore && !asteriskAfter) {
				if (itemname.equalsIgnoreCase(typeMatch)) {
					return typeMatchPositionPair.getPosition();
				}
			} else if (asteriskBefore && asteriskAfter) {
				if (itemname.contains(typeMatch)) {
					return typeMatchPositionPair.getPosition();
				}
			} else if (asteriskBefore && !asteriskAfter) {
				if (itemname.endsWith(typeMatch)) {
					return typeMatchPositionPair.getPosition();
				}
			} else {
				if (itemname.startsWith(typeMatch)) {
					return typeMatchPositionPair.getPosition();
				}
			}
		}

		return 0;
	}
	
	public int compareTo(Category compareCategory) {
		return this.name.compareTo(compareCategory.name);
	}

}
