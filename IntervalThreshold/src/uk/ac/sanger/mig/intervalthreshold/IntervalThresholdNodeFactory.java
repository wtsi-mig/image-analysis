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

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "IntervalThreshold" Node.
 * Select a range of pixel values, between an upper and lower bound setting all other pixels to the background value.
 *
 * @author Wellcome Trust Sanger Institute
 */
public class IntervalThresholdNodeFactory<T extends RealType<T> & NativeType<T>>
        extends NodeFactory<IntervalThresholdNodeModel<T>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public IntervalThresholdNodeModel<T> createNodeModel() {
        return new IntervalThresholdNodeModel<T>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<IntervalThresholdNodeModel<T>> createNodeView(final int viewIndex,
            final IntervalThresholdNodeModel<T> nodeModel) {
        return new IntervalThresholdNodeView<T>(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new IntervalThresholdNodeDialog();
    }

}

