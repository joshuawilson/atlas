package org.commonjava.maven.atlas.spi.neo4j.effective.traverse;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.graph.effective.filter.ProjectRelationshipFilter;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;

@SuppressWarnings( "rawtypes" )
public class EndNodesCollector
    extends AbstractAtlasCollector<Node>
{

    private final Set<Node> endNodes;

    public EndNodesCollector( final Node start, final Node end, final ProjectRelationshipFilter filter,
                              final boolean checkExistence )
    {
        this( Collections.singleton( start ), Collections.singleton( end ), filter, checkExistence );
    }

    public EndNodesCollector( final Set<Node> startNodes, final Set<Node> endNodes,
                              final ProjectRelationshipFilter filter, final boolean checkExistence )
    {
        super( startNodes, filter, checkExistence );
        this.endNodes = endNodes;
    }

    private EndNodesCollector( final Set<Node> startNodes, final Set<Node> endNodes,
                               final ProjectRelationshipFilter filter, final boolean checkExistence,
                               final Direction direction )
    {
        super( startNodes, filter, checkExistence, direction );
        this.endNodes = endNodes;
    }

    public PathExpander reverse()
    {
        return new EndNodesCollector( startNodes, endNodes, filter, checkExistence, direction.reverse() );
    }

    public boolean hasFoundNodes()
    {
        return !found.isEmpty();
    }

    public Set<Node> getFoundNodes()
    {
        return found;
    }

    public Iterator<Node> iterator()
    {
        return found.iterator();
    }

    @Override
    protected boolean returnChildren( final Path path )
    {
        final Node end = path.endNode();
        if ( endNodes.contains( end ) )
        {
            if ( accept( path ) )
            {
                //                logger.info( "FOUND path ending in: %s", endId );
                found.add( end );
            }

            return false;
        }

        return true;
    }

}
