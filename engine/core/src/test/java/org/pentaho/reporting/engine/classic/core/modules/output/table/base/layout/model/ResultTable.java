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


package org.pentaho.reporting.engine.classic.core.modules.output.table.base.layout.model;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.layout.model.BorderEdge;
import org.pentaho.reporting.engine.classic.core.layout.model.LogicalPageBox;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.CellBackground;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.CellBackgroundProducer;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.CellMarker;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.SheetLayout;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.TableContentProducer;
import org.pentaho.reporting.libraries.base.util.ObjectTable;

/**
 * Creation-Date: 20.08.2007, 15:39:22
 *
 * @author Thomas Morgner
 */
public class ResultTable extends ObjectTable {
  private static final Log logger = LogFactory.getLog( ResultTable.class );
  private String name;
  private CellBackgroundProducer cellBackgroundProducer;

  public ResultTable() {
    this.cellBackgroundProducer = new CellBackgroundProducer( true, true );
  }

  public String getName() {
    return name;
  }

  public void setName( final String name ) {
    this.name = name;
  }

  public void setResultCell( final int row, final int column, final ResultCell object ) {
    super.setObject( row, column, object );
  }

  public ResultCell getResultCell( final int row, final int column ) {
    return (ResultCell) super.getObject( row, column );
  }

  public void validate( final LogicalPageBox logicalPageBox, final SheetLayout sheetLayout,
      final TableContentProducer tableContentProducer ) {
    Assert.assertEquals( "RowCount", getRowCount(), sheetLayout.getRowCount() );
    Assert.assertEquals( "ColCount", getColumnCount(), sheetLayout.getColumnCount() );
    int row = 0;
    int col = 0;
    try {
      for ( row = 0; row < getRowCount(); row++ ) {
        for ( col = 0; col < getColumnCount(); col++ ) {
          final ResultCell resultCell = getResultCell( row, col );
          final CellMarker.SectionType sectionType = tableContentProducer.getSectionType( row, col );
          final CellBackground backgroundAt =
              cellBackgroundProducer.getBackgroundAt( logicalPageBox, sheetLayout, col, row, true, sectionType );
          if ( resultCell == null ) {
            assertEmptyBackground( backgroundAt );
          } else {
            resultCell.assertValidity( backgroundAt );
          }
        }
      }
    } catch ( AssertionFailedError afe ) {
      logger.error( "Assertation failure at row " + row + ", column " + col );
      throw afe;
    }
  }

  private void assertEmptyBackground( final CellBackground background ) {
    if ( background == null ) {
      return;
    }
    Assert.assertEquals( background.getAnchors().length, 0 );
    Assert.assertEquals( BorderEdge.EMPTY, background.getBottom() );
    Assert.assertEquals( BorderEdge.EMPTY, background.getTop() );
    Assert.assertEquals( BorderEdge.EMPTY, background.getLeft() );
    Assert.assertEquals( BorderEdge.EMPTY, background.getRight() );
    Assert.assertNull( background.getBackgroundColor() );
  }
}
