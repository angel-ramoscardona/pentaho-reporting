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


package org.pentaho.reporting.engine.classic.demo.ancient.demo;

import java.awt.Color;
import java.awt.geom.Point2D;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.ElementAlignment;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.elementfactory.TextFieldElementFactory;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.PreviewDialog;
import org.pentaho.reporting.engine.classic.demo.util.DefaultCloseHandler;
import org.pentaho.reporting.engine.classic.demo.util.DemoHandler;
import org.pentaho.reporting.engine.classic.demo.util.PreviewHandler;
import org.pentaho.reporting.libraries.base.util.FloatDimension;

/**
 * A very simple JFreeReport demo.  The purpose of this demo is to illustrate the basic steps required to connect a
 * report definition with some data and display a report preview on-screen.
 * <p/>
 * In this example, the report definition is created in code.  It is also possible to read a report definition from an
 * XML file...that is demonstrated elsewhere.
 *
 * @author David Gilbert
 */
public class HelloWorld implements DemoHandler
{
  /**
   * A helper class to make this demo accessible from the DemoFrontend.
   */
  private class HelloWorldPreviewHandler implements PreviewHandler
  {
    protected HelloWorldPreviewHandler()
    {
    }

    public void attemptPreview()
    {
      executeReport();
    }
  }

  /**
   * Creates and displays a simple report.
   */
  public HelloWorld()
  {
  }

  protected void executeReport()
  {
    final MasterReport report = createReportDefinition();
    report.setDataFactory(new TableDataFactory
        ("default", createData()));

    final PreviewDialog preview = new PreviewDialog(report);
    preview.addWindowListener(new DefaultCloseHandler());
    preview.pack();
    preview.setVisible(true);
  }

  /**
   * Creates a small dataset to use in a report.  JFreeReport always reads data from a <code>TableModel</code>
   * instance.
   *
   * @return a dataset.
   */
  private TableModel createData()
  {

    final Object[] columnNames = new String[]{"Column1", "Column2"};
    final DefaultTableModel result = new DefaultTableModel(columnNames, 1);
    result.setValueAt("Hello\n", 0, 0);
    result.setValueAt("World!\n", 0, 1);
    return result;

  }

  /**
   * Creates a report definition.
   *
   * @return a report definition.
   */
  private MasterReport createReportDefinition()
  {

    final MasterReport report = new MasterReport();
    report.setName(getDescription());

    TextFieldElementFactory factory = new TextFieldElementFactory();
    factory.setName("T1");
    factory.setAbsolutePosition(new Point2D.Float(0, 0));
    factory.setMinimumSize(new FloatDimension(150, 12));
    factory.setColor(Color.black);
    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
    factory.setNullString("-");
    factory.setFieldname("Column1");
    report.getItemBand().addElement(factory.createElement());

    factory = new TextFieldElementFactory();
    factory.setName("T2");
    factory.setAbsolutePosition(new Point2D.Float(200, 0));
    factory.setMinimumSize(new FloatDimension(150, 12));
    factory.setColor(Color.black);
    factory.setHorizontalAlignment(ElementAlignment.LEFT);
    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
    factory.setNullString("-");
    factory.setFieldname("Column2");
    report.getItemBand().addElement(factory.createElement());
    return report;

  }

  /**
   * Returns a short description of the demo.
   *
   * @return a description of this report.
   */
  protected String getDescription()
  {
    return "A Very Simple Report";
  }


  public String getDemoName()
  {
    return "Hello World Demo (External)";
  }

  public PreviewHandler getPreviewHandler()
  {
    return new HelloWorldPreviewHandler();
  }


  /**
   * The starting point for the "Hello World" demo application.
   *
   * @param args ignored.
   */
  public static void main(final String[] args)
  {
    // this also installs the log.
    // initialize JFreeReport
    ClassicEngineBoot.getInstance().start();

    //final HelloWorld app =
    new HelloWorld().executeReport();
  }

}
