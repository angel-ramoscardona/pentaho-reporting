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


package org.pentaho.reporting.designer.core.editor.report.layouting;

import org.pentaho.reporting.engine.classic.core.ResourceBundleFactory;
import org.pentaho.reporting.engine.classic.core.imagemap.ImageMap;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.StyleSheet;
import org.pentaho.reporting.engine.classic.core.style.TextStyleKeys;
import org.pentaho.reporting.engine.classic.core.util.ReportDrawable;
import org.pentaho.reporting.libraries.base.config.Configuration;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SubreportDrawable implements ReportDrawable {
  private Color background;
  private Color foreground;
  private Font font;
  private Object value;

  public SubreportDrawable( final Object value ) {
    this.value = value;
    setStyleSheet( null );
  }

  public void draw( final Graphics2D g2, final Rectangle2D bounds ) {
    final Graphics2D graphics2D = (Graphics2D) g2.create();
    graphics2D.clip( bounds );
    graphics2D.setColor( background );
    graphics2D.fill( bounds );
    graphics2D.setFont( font );
    graphics2D.setColor( foreground );
    if ( value == null ) {
      graphics2D.drawString( "Subreport", 0, font.getSize2D() );
    } else {
      graphics2D.drawString( String.valueOf( value ), 0, font.getSize2D() );
    }
    graphics2D.dispose();
  }

  /**
   * Provides the current report configuration of the current report process to the drawable. The report configuration
   * can be used to configure the drawing process through the report.
   *
   * @param config the report configuration.
   */
  public void setConfiguration( final Configuration config ) {

  }

  /**
   * Provides the computed stylesheet of the report element that contained this drawable. The stylesheet is immutable.
   *
   * @param style the stylesheet.
   */
  public void setStyleSheet( final StyleSheet style ) {
    if ( style != null ) {
      background = (Color) style.getStyleProperty( ElementStyleKeys.BACKGROUND_COLOR, new Color( 159, 224, 255 ) );

      int fontstyle = Font.PLAIN;
      if ( style.getBooleanStyleProperty( TextStyleKeys.BOLD ) ) {
        fontstyle |= Font.BOLD;
      }
      if ( style.getBooleanStyleProperty( TextStyleKeys.ITALIC ) ) {
        fontstyle |= Font.ITALIC;
      }

      font = new Font( (String) style.getStyleProperty( TextStyleKeys.FONT, Font.SANS_SERIF ),
        fontstyle, style.getIntStyleProperty( TextStyleKeys.FONTSIZE, 12 ) );
      foreground = (Color) style.getStyleProperty( ElementStyleKeys.PAINT );
    } else {
      background = new Color( 159, 224, 255 );
      font = new Font( Font.SANS_SERIF, Font.PLAIN, 12 );
      foreground = Color.BLACK;
    }
  }

  /**
   * Defines the resource-bundle factory that can be used to localize the drawing process.
   *
   * @param bundleFactory the resource-bundle factory.
   */
  public void setResourceBundleFactory( final ResourceBundleFactory bundleFactory ) {

  }

  /**
   * Returns an optional image-map for the entry.
   *
   * @param bounds the bounds for which the image map is computed.
   * @return the computed image-map or null if there is no image-map available.
   */
  public ImageMap getImageMap( final Rectangle2D bounds ) {
    return null;
  }
}
