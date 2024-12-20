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


package org.pentaho.reporting.engine.classic.core.modules.output.fast.xls;

import org.pentaho.reporting.engine.classic.core.Band;
import org.pentaho.reporting.engine.classic.core.ReportDefinition;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.function.ExpressionRuntime;
import org.pentaho.reporting.engine.classic.core.layout.output.ContentProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.fast.template.AbstractContentProducerTemplate;
import org.pentaho.reporting.engine.classic.core.modules.output.fast.template.FastExportTemplateProducer;
import org.pentaho.reporting.engine.classic.core.modules.output.fast.template.FormattedDataBuilder;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.SheetLayout;

import java.io.IOException;
import java.io.OutputStream;

public class FastExcelContentProducerTemplate extends AbstractContentProducerTemplate {
  private final OutputStream outputStream;
  private final boolean useXlsx;
  private FastExcelPrinter excelPrinter;

  public FastExcelContentProducerTemplate( final SheetLayout sheetLayout, final OutputStream outputStream,
      final boolean useXlsx ) {
    super( sheetLayout );
    this.outputStream = outputStream;
    this.useXlsx = useXlsx;
  }

  public void initialize( final ReportDefinition report, final ExpressionRuntime runtime, final boolean pagination ) {
    super.initialize( report, runtime, pagination );
    this.excelPrinter = new FastExcelPrinter( getSharedSheetLayout() );
    this.excelPrinter.setUseXlsxFormat( useXlsx );
    this.excelPrinter.init( getMetaData(), runtime.getProcessingContext().getResourceManager(), report );
  }

  protected void writeContent( final Band band, final ExpressionRuntime runtime,
      final FormattedDataBuilder messageFormatSupport ) throws IOException, ReportProcessingException,
    ContentProcessingException {
    messageFormatSupport.compute( band, runtime, outputStream );
  }

  public void finishReport() throws ReportProcessingException {
    try {
      this.excelPrinter.closeWorkbook( outputStream );
    } catch ( IOException e ) {
      throw new ReportProcessingException( "Failed to close report", e );
    }
  }

  protected FastExportTemplateProducer createTemplateProducer() {
    return new FastExcelTemplateProducer( getMetaData(), getSharedSheetLayout(), excelPrinter );
  }
}
