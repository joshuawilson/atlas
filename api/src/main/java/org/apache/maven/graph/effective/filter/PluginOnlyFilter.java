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
package org.apache.maven.graph.effective.filter;

import org.apache.maven.graph.common.RelationshipType;
import org.apache.maven.graph.effective.rel.PluginRelationship;
import org.apache.maven.graph.effective.rel.ProjectRelationship;

// TODO: Do we need to consider excludes in the direct plugin-level dependency?
public class PluginOnlyFilter
    extends AbstractTypedFilter
{

    public PluginOnlyFilter()
    {
        this( false, true );
    }

    public PluginOnlyFilter( final boolean includeManaged, final boolean includeConcrete )
    {
        super( RelationshipType.PLUGIN, false, includeManaged, includeConcrete );
    }

    @Override
    public boolean doAccept( final ProjectRelationship<?> rel )
    {
        final PluginRelationship pr = (PluginRelationship) rel;
        if ( isManagedInfoIncluded() && pr.isManaged() )
        {
            return true;
        }
        else if ( isConcreteInfoIncluded() && !pr.isManaged() )
        {
            return true;
        }

        return false;
    }

    public ProjectRelationshipFilter getChildFilter( final ProjectRelationship<?> parent )
    {
        return new NoneFilter();
    }

    public void render( final StringBuilder sb )
    {
        if ( sb.length() > 0 )
        {
            sb.append( " " );
        }
        sb.append( "PLUGINS ONLY" );
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        render( sb );
        return sb.toString();
    }

}
