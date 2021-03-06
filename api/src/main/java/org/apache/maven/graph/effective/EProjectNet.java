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
package org.apache.maven.graph.effective;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.graph.common.ref.ProjectVersionRef;
import org.apache.maven.graph.common.version.SingleVersion;
import org.apache.maven.graph.effective.filter.ProjectRelationshipFilter;
import org.apache.maven.graph.effective.ref.EProjectKey;
import org.apache.maven.graph.effective.rel.ProjectRelationship;
import org.apache.maven.graph.spi.GraphDriverException;
import org.apache.maven.graph.spi.effective.EGraphDriver;

public interface EProjectNet
    extends EProjectRelationshipCollection
{

    Map<ProjectVersionRef, ProjectVersionRef> clearSelectedVersions()
        throws GraphDriverException;

    ProjectVersionRef selectVersionFor( ProjectVersionRef variable, SingleVersion version )
        throws GraphDriverException;

    EGraphDriver getDriver();

    boolean isComplete();

    boolean isConcrete();

    Set<ProjectVersionRef> getIncompleteSubgraphs();

    Set<ProjectVersionRef> getVariableSubgraphs();

    //    void recomputeIncompleteSubgraphs();

    Set<EProjectCycle> getCycles();

    boolean introducesCycle( ProjectRelationship<?> rel );

    void addCycle( final EProjectCycle cycle );

    boolean isCycleParticipant( ProjectRelationship<?> rel );

    boolean isCycleParticipant( ProjectVersionRef ref );

    Set<ProjectRelationship<?>> getRelationshipsTargeting( ProjectVersionRef ref );

    Set<List<ProjectRelationship<?>>> getPathsTo( final ProjectVersionRef... refs );

    boolean isDerivedFrom( EProjectNet net );

    EProjectGraph getGraph( ProjectRelationshipFilter filter, EProjectKey key )
        throws GraphDriverException;

    EProjectGraph getGraph( EProjectKey key )
        throws GraphDriverException;

    EProjectWeb getWeb( EProjectKey... keys )
        throws GraphDriverException;

    EProjectWeb getWeb( ProjectRelationshipFilter filter, EProjectKey... keys )
        throws GraphDriverException;

    boolean containsGraph( EProjectKey eProjectKey );

    Set<ProjectVersionRef> getAllProjects();

    //    Set<ProjectRelationship<?>> getAllRelationships();

    Map<String, String> getMetadata( EProjectKey key );

    void addMetadata( EProjectKey key, String name, String value );

    void addMetadata( EProjectKey key, Map<String, String> metadata );

    List<EProjectNet> getSuperNets();

    <T extends ProjectRelationship<?>> Set<T> addAll( Collection<T> rels );

    Set<ProjectVersionRef> getProjectsWithMetadata( String key );

    void reindex()
        throws GraphDriverException;

    boolean connectFor( EProjectKey key )
        throws GraphDriverException;

    void connect( EProjectGraph graph )
        throws GraphDriverException;

    <T extends EProjectNet> T filteredInstance( ProjectRelationshipFilter filter )
        throws GraphDriverException;

    void addDisconnectedProject( ProjectVersionRef ref );
}
