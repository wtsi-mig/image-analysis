/*
 * Copyright (c) 2014-2015 Genome Research Ltd.
 * 
 * Author: Mouse Informatics Group <team110g@sanger.ac.uk>
 * This file is part of Node Toolset.
 * 
 * Node Toolset is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option ) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.sanger.mig.analysis.nodetools.enums;

import com.sun.istack.internal.Nullable;

public enum ReturnType {
	/** Return the original, unmodified image */
	ORIG("Original"),
	/** Returns the modified image, depending on the node, usually used for debugging */
	MODIFIED("Modified");
	
	private String name;
	
	private ReturnType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Finds enum which has names as provided
	 * @param name name to look for, <b>null</b> if not found
	 */
	@Nullable
	public static final ReturnType whereName(String name) {
		
		for (int i = 0; i < values().length; i++) {
			if (values()[i].toString().equals(name)) {
				return values()[i];
			}
		}
		
		return null;
	}
	
	/**
	 * Returns names of all enums
	 */
	public static final String[] names() {
		String[] names = new String[values().length];
		
		for (int i = 0; i < values().length; i++) {
			names[i] = values()[i].toString();
		}
		
		return names;
	}
}