package uk.ac.sanger.mig.regioncropper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.imglib2.meta.ImgPlus;
import net.imglib2.type.logic.BitType;

import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelColumnName;
import org.knime.knip.base.data.img.ImgPlusCell;

import uk.ac.sanger.mig.regioncropper.utils.OutputHelper;
import uk.ac.sanger.mig.regioncropper.utils.Utils;

/**
 * This is the model implementation of RegionCropper. Crops images according to
 * the given the upper, right, lower and left bounds. Everything outside these
 * boundaries is deleted.
 * 
 * @author Wellcome Trust Sanger Institute
 */
public class RegionCropperNodeModel extends NodeModel {

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
	static final String CFGKEY_UPBOUND_COL = "Upper Boundary Column";
	static final String CFGKEY_RIGHTBOUND_COL = "Right Boundary Column";
	static final String CFGKEY_LOWBOUND_COL = "Lower Boundary Column";
	static final String CFGKEY_LEFTBOUND_COL = "Left Boundary Column";

	// example value: the models count variable filled from the dialog
	// and used in the models execution method. The default components of the
	// dialog work with "SettingsModels".

	static final Map<String, SettingsModel> settingsModels;
	static {
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
	}

	/**
	 * Constructor for the node model.
	 */
	protected RegionCropperNodeModel() {
		super(1, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
			final ExecutionContext exec) throws Exception {

//		String rowThresholds = ((SettingsModelString) settingsModels
//				.get(CFGKEY_ROW_THRESHOLD)).getStringValue();
//		String colThresholds = ((SettingsModelString) settingsModels
//				.get(CFGKEY_COL_THRESHOLD)).getStringValue();

		Map<String, Integer> indices = Utils.indices(inData[0]
				.getDataTableSpec());

		OutputHelper out = new OutputHelper(COLUMNS, COLUMN_TYPES, exec);

		Iterator<DataRow> iter = inData[0].iterator();
		while (iter.hasNext()) {
			DataRow row = iter.next();

			ImgPlus<BitType> ip = Utils.imageByIndex(row, indices.get(Utils
					.stringFromSetting(settingsModels.get(CFGKEY_IMAGE_COL))));

			out.open(row.getKey());

			// out.add(box.image());
			// out.add(boundaries);

			out.close();
		}

		return new BufferedDataTable[] { out.getOutputTable() };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void reset() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
			throws InvalidSettingsException {

		return new DataTableSpec[] { null };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {
		for (Entry<String, SettingsModel> e : settingsModels.entrySet()) {
			e.getValue().saveSettingsTo(settings);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
			throws InvalidSettingsException {
		for (Entry<String, SettingsModel> e : settingsModels.entrySet()) {
			e.getValue().loadSettingsFrom(settings);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(final NodeSettingsRO settings)
			throws InvalidSettingsException {
		for (Entry<String, SettingsModel> e : settingsModels.entrySet()) {
			e.getValue().validateSettings(settings);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadInternals(final File internDir,
			final ExecutionMonitor exec) throws IOException,
			CanceledExecutionException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(final File internDir,
			final ExecutionMonitor exec) throws IOException,
			CanceledExecutionException {
	}

}