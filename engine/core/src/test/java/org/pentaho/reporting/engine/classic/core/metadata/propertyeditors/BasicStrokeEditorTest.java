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


package org.pentaho.reporting.engine.classic.core.metadata.propertyeditors;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

public class BasicStrokeEditorTest {

  private BasicStrokeEditor editor;

  @Before
  public void setUp() {
    editor = new BasicStrokeEditor();
  }

  @Test
  public void testSettingValue() {
    editor.setValue( null );
    assertThat( editor.getValue(), is( nullValue() ) );

    BasicStroke basicStroke = new BasicStroke();
    editor.setValue( basicStroke );
    assertThat( editor.getValue(), is( instanceOf( BasicStroke.class ) ) );
    assertThat( (BasicStroke) editor.getValue(), is( equalTo( basicStroke ) ) );

    editor.setValue( "incorrect" );
    assertThat( editor.getValue(), is( nullValue() ) );
  }

  @Test
  public void testSettingValueAsText() {
    editor.setValue( null );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );

    editor.setAsText( EMPTY );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );
    editor.setAsText( "1.5" );
    assertThat( editor.getAsText(), is( equalTo( "solid, 1.5" ) ) );
    editor.setAsText( "-1.5" );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );
    editor.setAsText( "1.5,1.0,0.5" );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );
    editor.setAsText( "-1.5,-1.5" );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );
    editor.setAsText( "solid,1.5" );
    assertThat( editor.getAsText(), is( equalTo( "solid, 1.5" ) ) );
    editor.setAsText( "incorrect,1.5" );
    assertThat( editor.getAsText(), is( equalTo( EMPTY ) ) );

    editor.setAsText( "incorrect" );
    assertThat( editor.getValue(), is( nullValue() ) );
  }

  @Test
  public void testGetTags() {
    assertThat( editor.getTags(), is( nullValue() ) );
  }

  @Test
  public void testIsPaintable() {
    assertThat( editor.isPaintable(), is( equalTo( true ) ) );
  }

  @Test
  public void testGetJavaInitializationStringWithNull() {
    assertThat( editor.getJavaInitializationString(), is( nullValue() ) );
  }

  @Test
  public void testGetCustomEditor() {
    assertThat( editor.getCustomEditor(), is( notNullValue() ) );
  }

  @Test
  public void testSupportsCustomEditor() {
    assertThat( editor.supportsCustomEditor(), is( equalTo( true ) ) );
  }

  @Test
  public void testPaintValue() {
    BasicStroke stroke = new BasicStroke();
    editor.setValue( stroke );
    Graphics2D gfx = mock( Graphics2D.class );
    Rectangle box = mock( Rectangle.class );
    editor.paintValue( gfx, box );
    verify( gfx ).setStroke( stroke );
    verify( gfx ).drawLine( 0, 0, 0, 0 );
  }
}
