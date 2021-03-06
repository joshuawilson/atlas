/*******************************************************************************
 * Copyright (C) 2013 John Casey.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.apache.maven.graph.effective.traverse.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.maven.graph.common.ref.ProjectRef;
import org.apache.maven.graph.effective.EProjectCycle;

public final class BuildOrder
{

    private final List<ProjectRef> order;

    private final Set<EProjectCycle> cycles;

    public BuildOrder( final List<ProjectRef> order, final Set<EProjectCycle> cycles )
    {
        this.order = Collections.unmodifiableList( order );
        this.cycles = cycles == null ? null : Collections.unmodifiableSet( cycles );
    }

    public List<ProjectRef> getOrder()
    {
        return order;
    }

    public Set<EProjectCycle> getCycles()
    {
        return cycles;
    }

}
