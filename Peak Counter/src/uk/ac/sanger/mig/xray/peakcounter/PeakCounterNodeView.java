package uk.ac.sanger.mig.xray.peakcounter;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "PeakCounter" Node.
 * Scans through the image and using buckets counts number of peaks.
 *
 * @author Wellcome Trust Sanger Institute
 */
public class PeakCounterNodeView extends NodeView<PeakCounterNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link PeakCounterNodeModel})
     */
    protected PeakCounterNodeView(final PeakCounterNodeModel nodeModel) {
        super(nodeModel);
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {
        // TODO: generated method stub
    }

}
