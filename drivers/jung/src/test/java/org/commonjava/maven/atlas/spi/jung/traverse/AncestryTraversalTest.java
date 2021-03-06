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
package org.commonjava.maven.atlas.spi.jung.traverse;

import org.apache.log4j.Level;
import org.apache.maven.graph.spi.effective.EGraphDriver;
import org.commonjava.maven.atlas.spi.jung.effective.JungEGraphDriver;
import org.commonjava.maven.atlas.tck.effective.traverse.AncestryTraversalTCK;
import org.commonjava.util.logging.Log4jUtil;
import org.junit.BeforeClass;

public class AncestryTraversalTest
    extends AncestryTraversalTCK
{
    @BeforeClass
    public static void logging()
    {
        Log4jUtil.configure( Level.DEBUG );
    }

    @Override
    protected EGraphDriver newDriverInstance()
        throws Exception
    {
        return new JungEGraphDriver();
    }
}
