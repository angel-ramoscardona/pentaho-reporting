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


package org.pentaho.reporting.engine.classic.core.wizard.parser;

import org.pentaho.reporting.engine.classic.core.wizard.ConceptQueryMapper;
import org.pentaho.reporting.engine.classic.core.wizard.DefaultConceptQueryMapper;
import org.pentaho.reporting.engine.classic.core.wizard.StaticDataAttributeReference;
import org.pentaho.reporting.libraries.base.util.ObjectUtilities;
import org.pentaho.reporting.libraries.xmlns.parser.AbstractXmlReadHandler;
import org.pentaho.reporting.libraries.xmlns.parser.ParseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DataAttributeMappingReadHandler extends AbstractXmlReadHandler {
  private String sourceDomain;
  private String sourceName;
  private String targetDomain;
  private String targetName;
  private ConceptQueryMapper mapper;

  public DataAttributeMappingReadHandler() {
  }

  /**
   * Starts parsing.
   *
   * @param attrs
   *          the attributes.
   * @throws SAXException
   *           if there is a parsing error.
   */
  protected void startParsing( final Attributes attrs ) throws SAXException {
    sourceDomain = attrs.getValue( getUri(), "source-domain" );
    if ( sourceDomain == null ) {
      throw new ParseException( "Required attribute 'source-domain' is missing.", getLocator() );
    }

    sourceName = attrs.getValue( getUri(), "source-name" );
    if ( sourceName == null ) {
      throw new ParseException( "Required attribute 'source-name' is missing.", getLocator() );
    }

    targetDomain = attrs.getValue( getUri(), "target-domain" );
    if ( targetDomain == null ) {
      throw new ParseException( "Required attribute 'target-domain' is missing.", getLocator() );
    }

    targetName = attrs.getValue( getUri(), "target-name" );
    if ( targetName == null ) {
      throw new ParseException( "Required attribute 'target-name' is missing.", getLocator() );
    }

    final String mapperClass = attrs.getValue( getUri(), "concept-mapper" );
    if ( mapperClass == null ) {
      mapper = DefaultConceptQueryMapper.INSTANCE;
    } else {
      final Object maybeMapper =
          ObjectUtilities.loadAndInstantiate( mapperClass, DataAttributeMappingReadHandler.class,
              ConceptQueryMapper.class );
      if ( maybeMapper != null ) {
        mapper = (ConceptQueryMapper) maybeMapper;
      } else {
        mapper = DefaultConceptQueryMapper.INSTANCE;
      }
    }
  }

  public String getSourceDomain() {
    return sourceDomain;
  }

  public String getSourceName() {
    return sourceName;
  }

  public String getTargetDomain() {
    return targetDomain;
  }

  public String getTargetName() {
    return targetName;
  }

  /**
   * Returns the object for this element or null, if this element does not create an object.
   *
   * @return the object.
   * @throws SAXException
   *           if an parser error occured.
   */
  public Object getObject() throws SAXException {
    return new StaticDataAttributeReference( sourceDomain, sourceName, null, mapper );
  }
}
