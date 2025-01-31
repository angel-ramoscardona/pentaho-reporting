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


package org.pentaho.reporting.engine.classic.extensions.datasources.mondrian;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class DefaultMondrianConnectionProvider implements MondrianConnectionProvider {
  private static final Log logger = LogFactory.getLog( DefaultMondrianConnectionProvider.class );

  public DefaultMondrianConnectionProvider() {
  }

  protected String computeConnectionString( final Properties parameters ) {
    final StringBuffer connectionStr = new StringBuffer( 100 );
    connectionStr.append( "provider=mondrian" );

    connectionStr.append( "; " );
    connectionStr.append( "Catalog=" );
    connectionStr.append( parameters.getProperty( "Catalog" ) );

    final Enumeration objectEnumeration = parameters.keys();
    while ( objectEnumeration.hasMoreElements() ) {
      final String key = (String) objectEnumeration.nextElement();
      if ( "Catalog".equals( key ) ) {
        continue;
      }
      final Object value = parameters.getProperty( key );
      if ( value != null ) {
        connectionStr.append( "; " );
        connectionStr.append( key );
        connectionStr.append( "=" );
        connectionStr.append( value );
      }
    }
    return connectionStr.toString();
  }

  public Connection createConnection( final Properties properties, final DataSource dataSource )
    throws ReportDataFactoryException {
    logger.debug( "Creating Mondrian connection: " + Util.parseConnectString( computeConnectionString( properties ) ) );
    return DriverManager
      .getConnection( Util.parseConnectString( computeConnectionString( properties ) ), null, dataSource );
  }

  public Object getConnectionHash( final Properties properties ) throws ReportDataFactoryException {
    final ArrayList<Object> list = new ArrayList<Object>();
    list.add( getClass().getName() );
    list.add( properties.clone() );
    return list;
  }
}
