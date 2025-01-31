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


package org.pentaho.reporting.engine.classic.extensions.datasources.pmd;

import org.pentaho.metadata.query.model.Selection;
import org.pentaho.reporting.engine.classic.core.MetaTableModel;
import org.pentaho.reporting.engine.classic.core.util.CloseableTableModel;
import org.pentaho.reporting.engine.classic.core.wizard.DataAttributes;

import javax.swing.event.TableModelListener;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PmdMetaTableModel implements MetaTableModel, CloseableTableModel {
  private MetaTableModel parentTableModel;
  private Selection[] selections;
  private String[] columnNames;

  public PmdMetaTableModel( final MetaTableModel parentTableModel,
                            final List<Selection> selections ) {
    if ( parentTableModel == null ) {
      throw new NullPointerException();
    }
    if ( selections == null ) {
      throw new NullPointerException();
    }
    this.parentTableModel = parentTableModel;
    this.selections = new Selection[ selections.size() ];
    this.columnNames = new String[ selections.size() ];
    final Set<String> uniqueIds = new TreeSet<String>();
    for ( int i = 0; i < this.selections.length; i++ ) {
      this.selections[ i ] = selections.get( i );
      String columnName = this.selections[ i ].getLogicalColumn().getId();
      if ( uniqueIds.contains( columnName ) ) {
        // append ID with selection aggregation type
        columnName += ( ( this.selections[ i ].getAggregationType() != null ) ?
          ":" + this.selections[ i ].getAggregationType().toString() : "" );
      }
      uniqueIds.add( columnName );
      this.columnNames[ i ] = columnName;
    }
  }

  public DataAttributes getCellDataAttributes( final int row, final int column ) {
    return new PentahoMetaDataAttributes( parentTableModel.getCellDataAttributes( row, column ),
      selections[ column ].getLogicalColumn(), getColumnName( column ) );
  }

  public boolean isCellDataAttributesSupported() {
    return parentTableModel.isCellDataAttributesSupported();
  }

  public DataAttributes getColumnAttributes( final int column ) {
    return new PentahoMetaDataAttributes( parentTableModel.getColumnAttributes( column ),
      selections[ column ].getLogicalColumn(), getColumnName( column )  );
  }

  public DataAttributes getTableAttributes() {
    return parentTableModel.getTableAttributes();
  }

  public int getRowCount() {
    return parentTableModel.getRowCount();
  }

  public int getColumnCount() {
    return parentTableModel.getColumnCount();
  }

  public String getColumnName( final int columnIndex ) {
    return columnNames[ columnIndex ];
  }

  public Class getColumnClass( final int columnIndex ) {
    return parentTableModel.getColumnClass( columnIndex );
  }

  public boolean isCellEditable( final int rowIndex, final int columnIndex ) {
    return false;
  }

  public Object getValueAt( final int rowIndex, final int columnIndex ) {
    return parentTableModel.getValueAt( rowIndex, columnIndex );
  }

  public void setValueAt( final Object aValue, final int rowIndex, final int columnIndex ) {
  }

  public void addTableModelListener( final TableModelListener l ) {
    parentTableModel.addTableModelListener( l );
  }

  public void removeTableModelListener( final TableModelListener l ) {
    parentTableModel.removeTableModelListener( l );
  }

  /**
   * If this model has disposeable resources assigned, close them or dispose them.
   */
  public void close() {
    if ( parentTableModel instanceof CloseableTableModel ) {
      final CloseableTableModel closeableTableModel = (CloseableTableModel) parentTableModel;
      closeableTableModel.close();
    }
  }
}
