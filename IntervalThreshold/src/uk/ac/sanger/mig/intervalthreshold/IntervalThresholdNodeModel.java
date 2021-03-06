/*
 * Copyright (c) 2014-2015 Genome Research Ltd.
 * 
 * Author: Mouse Informatics Group <team110g@sanger.ac.uk>
 * This file is part of Interval Threshold.
 * 
 * Interval Threshold is free software: you can redistribute it and/or modify it under
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

package uk.ac.sanger.mig.intervalthreshold;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.imglib2.meta.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.knime.core.data.DataRow;
import org.knime.core.data.DataType;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelColumnName;
import org.knime.core.node.defaultnodesettings.SettingsModelInteger;
import org.knime.knip.base.data.img.ImgPlusCell;

import uk.ac.sanger.mig.analysis.GenericNodeModel;
import uk.ac.sanger.mig.analysis.nodetools.OutputHelper;
import uk.ac.sanger.mig.analysis.nodetools.Utils;
import uk.ac.sanger.mig.intervalthreshold.utils.IntervalThreshold;

/**
 * This is the model implementation of IntervalThreshold. Select a range of
 * pixel values, between an upper and lower bound setting all other pixels to
 * the background value.
 * 
 * @author Wellcome Trust Sanger Institute
 */
public class IntervalThresholdNodeModel<T extends RealType<T> & NativeType<T>>
		extends GenericNodeModel {

	/** Columns in the schema */
	private final static String[] COLUMNS = { "Image" };

	/** Column types of the schema */
	private final static DataType[] COLUMN_TYPES = { ImgPlusCell.TYPE };

	/**
	 * the settings key which is used to retrieve and store the settings (from
	 * the dialog or from a settings file) (package visibility to be usable from
	 * the dialog).
	 */
	static final String CFGKEY_IMAGE_COL = "Image Column";
	static final String CFGKEY_UPP_THRESH = "Upper Threshold";
	static final String CFGKEY_LOW_THRESH = "Lower Threshold";
	static final String CFGKEY_BACKGROUND_VAL = "Background Pixel Value";

	// example value: the models count variable filled from the dialog
	// and used in the models execution method. The default components of the
	// dialog work with "SettingsModels".

	private final Map<String, SettingsModel> settingsModels;

	/**
	 * Constructor for the node model.
	 */
	protected IntervalThresholdNodeModel() {
		super(1, 1);
		
		settingsModels = new HashMap<>();

		settingsModels.put(CFGKEY_IMAGE_COL, new SettingsModelColumnName(
				CFGKEY_IMAGE_COL, "Image"));

		settingsModels.put(CFGKEY_UPP_THRESH, new SettingsModelInteger(
				CFGKEY_UPP_THRESH, 0));

		settingsModels.put(CFGKEY_LOW_THRESH, new SettingsModelInteger(
				CFGKEY_LOW_THRESH, 0));

		settingsModels.put(CFGKEY_BACKGROUND_VAL, new SettingsModelInteger(
				CFGKEY_BACKGROUND_VAL, 0));
		
		setSettings(settingsModels);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
			final ExecutionContext exec) throws Exception {

		indices = Utils.indices(inData[INPORT_0].getDataTableSpec());

		OutputHelper out = new OutputHelper(COLUMNS, COLUMN_TYPES, exec);

		Iterator<DataRow> iter = inData[INPORT_0].iterator();

		int upperThreshold = intFromSetting(CFGKEY_UPP_THRESH);
		int lowerThreshold = intFromSetting(CFGKEY_LOW_THRESH);
		int backgroundPixelValue = intFromSetting(CFGKEY_BACKGROUND_VAL);

		while (iter.hasNext()) {
			DataRow row = iter.next();

			// get the image according to the setting
			ImgPlus<T> ip = (ImgPlus<T>) imageBySetting(row, CFGKEY_IMAGE_COL);

			IntervalThreshold<T> thresholder = new IntervalThreshold<T>(
					lowerThreshold, upperThreshold, backgroundPixelValue);

			out.open(row.getKey());

			out.add(thresholder.process(ip));

			out.close();
		}

		// return the output table on the first (0th) outport
		return new BufferedDataTable[] { out.getOutputTable() };
	}

}
