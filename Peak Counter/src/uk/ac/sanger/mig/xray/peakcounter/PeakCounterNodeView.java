/*
 * Copyright (c) 2014-2015 Genome Research Ltd.
 * 
 * Author: Mouse Informatics Group <team110g@sanger.ac.uk>
 * This file is part of Peak Counter.
 * 
 * Peak Counter is free software: you can redistribute it and/or modify it under
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

package uk.ac.sanger.mig.xray.peakcounter;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "PeakCounter" Node.
 * Scans through the image and using buckets counts number of peaks. * nWhen it finds a white pixel, it creates a bucket. Continue on scanning, every following white pixel is added to the same bucket. When a black pixel is reached, the bucket is closed.
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

