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

package uk.ac.sanger.mig.analysis.nodetools.filters;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.def.IntCell;
import org.knime.core.node.util.ColumnFilter;

/**
 * Column filter that only leaves integer columns
 * @author pi1 pi1@sanger.ac.uk
 *
 */
public final class IntColumnFilter implements ColumnFilter {

	@Override
	public boolean includeColumn(DataColumnSpec colSpec) {
		return colSpec.getType() == IntCell.TYPE ? true : false;
	}

	@Override
	public String allFilteredMsg() {
		return "No Boundary Columns?";
	}

}
