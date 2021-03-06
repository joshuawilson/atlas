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
package org.apache.maven.graph.effective.rel;

import java.io.Serializable;

import org.apache.maven.graph.common.RelationshipType;
import org.apache.maven.graph.common.ref.ArtifactRef;
import org.apache.maven.graph.common.ref.ProjectRef;
import org.apache.maven.graph.common.ref.ProjectVersionRef;
import org.apache.maven.graph.common.version.SingleVersion;

public final class PluginDependencyRelationship
    extends AbstractProjectRelationship<ArtifactRef>
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final ProjectRef plugin;

    public PluginDependencyRelationship( final ProjectVersionRef declaring, final ProjectRef plugin,
                                         final ArtifactRef target, final int index, final boolean managed )
    {
        super( RelationshipType.PLUGIN_DEP, declaring, target, index, managed );
        this.plugin = plugin;
    }

    public final ProjectRef getPlugin()
    {
        return plugin;
    }

    @Override
    public synchronized ProjectRelationship<ArtifactRef> cloneFor( final ProjectVersionRef projectRef )
    {
        return new PluginDependencyRelationship( projectRef, plugin, getTarget(), getIndex(), isManaged() );
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( isManaged() ? 1231 : 1237 );
        result = prime * result + ( ( plugin == null ) ? 0 : plugin.hashCode() );
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( !super.equals( obj ) )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final PluginDependencyRelationship other = (PluginDependencyRelationship) obj;
        if ( isManaged() != other.isManaged() )
        {
            return false;
        }
        if ( plugin == null )
        {
            if ( other.plugin != null )
            {
                return false;
            }
        }
        else if ( !plugin.equals( other.plugin ) )
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return String.format( "PluginDependencyRelationship [%s(%s) => %s (managed=%s, index=%s)]", getDeclaring(),
                              plugin, getTarget(), isManaged(), getIndex() );
    }

    @Override
    public ArtifactRef getTargetArtifact()
    {
        return getTarget();
    }

    public ProjectRelationship<ArtifactRef> selectDeclaring( final SingleVersion version )
    {
        final ProjectVersionRef d = getDeclaring().selectVersion( version );
        final ArtifactRef t = getTarget();

        return new PluginDependencyRelationship( d, getPlugin(), t, getIndex(), isManaged() );
    }

    public ProjectRelationship<ArtifactRef> selectTarget( final SingleVersion version )
    {
        final ProjectVersionRef d = getDeclaring();
        ArtifactRef t = getTarget();
        t = new ArtifactRef( t.selectVersion( version ), t.getType(), t.getClassifier(), t.isOptional() );

        return new PluginDependencyRelationship( d, getPlugin(), t, getIndex(), isManaged() );
    }

}
