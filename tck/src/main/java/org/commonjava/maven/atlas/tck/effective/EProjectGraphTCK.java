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
package org.commonjava.maven.atlas.tck.effective;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.graph.common.ref.ProjectVersionRef;
import org.apache.maven.graph.effective.EProjectGraph;
import org.apache.maven.graph.effective.traverse.AncestryTraversal;
import org.apache.maven.graph.spi.effective.EGraphDriver;
import org.commonjava.util.logging.Logger;
import org.junit.Test;

public abstract class EProjectGraphTCK
    extends AbstractSPI_TCK
{

    @Test
    public void connectThreeGraphsWithParentInterrelationships()
        throws Exception
    {
        final ProjectVersionRef r = new ProjectVersionRef( "org.test", "root", "1" );
        final ProjectVersionRef p = new ProjectVersionRef( "org.test", "parent", "1.0" );
        final ProjectVersionRef c = new ProjectVersionRef( "org.test", "child", "1.0" );

        final EGraphDriver driver = newDriverInstance();
        final EProjectGraph root = new EProjectGraph.Builder( r, driver ).build();
        final EProjectGraph parent = new EProjectGraph.Builder( p, driver ).withParent( r )
                                                                           .build();
        final EProjectGraph child = new EProjectGraph.Builder( c, driver ).withParent( p )
                                                                          .build();
        parent.connect( root );
        child.connect( parent );

        assertThat( child.isComplete(), equalTo( true ) );

        final AncestryTraversal ancestryTraversal = new AncestryTraversal();
        child.traverse( ancestryTraversal );

        final List<ProjectVersionRef> ancestry = ancestryTraversal.getAncestry();
        new Logger( getClass() ).info( "Ancestry: %s", ancestry );

        assertThat( ancestry, notNullValue() );
        assertThat( ancestry.size(), equalTo( 3 ) );

        final Iterator<ProjectVersionRef> iterator = ancestry.iterator();
        assertThat( iterator.next(), equalTo( c ) );
        assertThat( iterator.next(), equalTo( p ) );
        assertThat( iterator.next(), equalTo( r ) );
    }

}
