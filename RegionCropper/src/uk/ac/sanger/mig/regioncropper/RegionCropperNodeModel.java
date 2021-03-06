/*
 * Copyright (c) 2014-2015 Genome Research Ltd.
 * 
 * Author: Mouse Informatics Group <team110g@sanger.ac.uk>
 * This file is part of Region Cropper.
 * 
 * Region Cropper is free software: you can redistribute it and/or modify it under
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

package uk.ac.sanger.mig.regioncropper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.imglib2.meta.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelColumnName;
import org.knime.knip.base.data.img.ImgPlusCell;

import uk.ac.sanger.mig.analysis.GenericNodeModel;
import uk.ac.sanger.mig.analysis.nodetools.OutputHelper;
import uk.ac.sanger.mig.analysis.nodetools.Utils;
import uk.ac.sanger.mig.regioncropper.utils.RegionCropper;

/**
 * This is the model implementation of RegionCropper. Crops images according to
 * the given the upper, right, lower and left bounds. Everything outside these
 * boundaries is deleted.
 * 
 * @author Wellcome Trust Sanger Institute
 * @author Paulius pi1@sanger.ac.uk
 */
public class RegionCropperNodeModel<T extends RealType<T> & NativeType<T>>
		extends GenericNodeModel {

	/** Columns in the schema */
	private final static String[] COLUMN_NAMES = { "Image" };

	/** Column types of the schema */
	private final static DataType[] COLUMN_TYPES = { ImgPlusCell.TYPE };

	/**
	 * the settings key which is used to retrieve and store the settings (from
	 * the dialog or from a settings file) (package visibility to be usable from
	 * the dialog).
	 */
	static final String CFGKEY_IMAGE_COL = "Image Column";
	static final String CFGKEY_UPBOUND_COL = "Upper Boundary Column";
	static final String CFGKEY_RIGHTBOUND_COL = "Right Boundary Column";
	static final String CFGKEY_LOWBOUND_COL = "Lower Boundary Column";
	static final String CFGKEY_LEFTBOUND_COL = "Left Boundary Column";

	// example value: the models count variable filled from the dialog
	// and used in the models execution method. The default components of the
	// dialog work with "SettingsModels".

	private final Map<String, SettingsModel> settingsModels;

	/**
	 * Constructor for the node model.
	 */
	protected RegionCropperNodeModel() {
		super(1, 1);
		
		settingsModels = new HashMap<String, SettingsModel>();

		settingsModels.put(CFGKEY_IMAGE_COL, new SettingsModelColumnName(
				CFGKEY_IMAGE_COL, "Image"));

		settingsModels.put(CFGKEY_UPBOUND_COL, new SettingsModelColumnName(
				CFGKEY_UPBOUND_COL, "Upper Boundary"));

		settingsModels.put(CFGKEY_RIGHTBOUND_COL, new SettingsModelColumnName(
				CFGKEY_RIGHTBOUND_COL, "Right Boundary"));

		settingsModels.put(CFGKEY_LOWBOUND_COL, new SettingsModelColumnName(
				CFGKEY_LOWBOUND_COL, "Lower Boundary"));

		settingsModels.put(CFGKEY_LEFTBOUND_COL, new SettingsModelColumnName(
				CFGKEY_LEFTBOUND_COL, "Left Boundary"));
		
		setSettings(settingsModels);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
			final ExecutionContext exec) throws Exception {
		
		indices = Utils.indices(inData[INPORT_0]
				.getDataTableSpec());

		OutputHelper out = new OutputHelper(COLUMN_NAMES, COLUMN_TYPES, exec);

		Iterator<DataRow> iter = inData[INPORT_0].iterator();
		while (iter.hasNext()) {
			DataRow row = iter.next();

			// get the image according to the setting
			ImgPlus<T> ip = (ImgPlus<T>) imageBySetting(row, CFGKEY_IMAGE_COL);
			
			// get the boundaries according to the setting
			int[] boundaries = { 
					intBySetting(row, CFGKEY_UPBOUND_COL),
					intBySetting(row, CFGKEY_LOWBOUND_COL),
					intBySetting(row, CFGKEY_LEFTBOUND_COL),
					intBySetting(row, CFGKEY_RIGHTBOUND_COL)
				};

			RegionCropper<T> cropper = new RegionCropper<T>(boundaries);

			out.open(row.getKey());

			// crop out the required part and add it to the output table
			out.add(cropper.crop(ip));

			out.close();
		}

		// return the output table on the first (0th) outport
		return new BufferedDataTable[] { out.getOutputTable() };
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
			throws InvalidSettingsException {
		
		for (int i = 0; i < inSpecs[INPORT_0].getNumColumns(); i++) {
			DataColumnSpec spec = inSpecs[INPORT_0].getColumnSpec(i);
			
			if (spec.getType() == ImgPlusCell.TYPE) {
				
				SettingsModel sm = settingsModels.get(CFGKEY_IMAGE_COL);
				((SettingsModelColumnName) sm).setStringValue(spec.getName());
				
				break;
			}
		}

		return new DataTableSpec[] { null };
	}
}
