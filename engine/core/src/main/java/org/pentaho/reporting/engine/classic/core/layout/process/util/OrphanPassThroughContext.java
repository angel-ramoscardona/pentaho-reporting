/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.reporting.engine.classic.core.layout.process.util;

import org.pentaho.reporting.engine.classic.core.layout.model.FinishedRenderNode;
import org.pentaho.reporting.engine.classic.core.layout.model.RenderBox;

public class OrphanPassThroughContext implements OrphanContext {
  private StackedObjectPool<OrphanPassThroughContext> pool;
  private OrphanContext parent;

  public OrphanPassThroughContext() {
  }

  public OrphanContext getParent() {
    return parent;
  }

  public void init( final StackedObjectPool<OrphanPassThroughContext> pool, final OrphanContext parent ) {
    this.pool = pool;
    this.parent = parent;
  }

  public void startChild( final RenderBox box ) {
    if ( parent != null ) {
      parent.startChild( box );
    }
  }

  public void endChild( final RenderBox box ) {
    if ( parent != null ) {
      parent.endChild( box );
    }
  }

  public long getOrphanValue() {
    return 0;
  }

  public long getWidowValue() {
    return 0;
  }

  public void registerFinishedNode( final FinishedRenderNode node ) {
    if ( parent != null ) {
      parent.registerFinishedNode( node );
    }
  }

  public void registerBreakMark( final RenderBox box ) {
    if ( parent != null ) {
      parent.registerBreakMark( box );
    }
  }

  public OrphanContext commit( final RenderBox box ) {
    return parent;
  }

  public void subContextCommitted( final RenderBox contextBox ) {
    if ( parent != null ) {
      parent.subContextCommitted( contextBox );
    }
  }

  public void clearForPooledReuse() {
    parent = null;
    pool.free( this );
  }
}
