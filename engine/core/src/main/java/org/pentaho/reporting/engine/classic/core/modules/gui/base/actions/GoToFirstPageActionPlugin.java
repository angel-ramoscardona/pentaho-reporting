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


package org.pentaho.reporting.engine.classic.core.modules.gui.base.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.pentaho.reporting.engine.classic.core.modules.gui.base.PreviewPane;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.SwingPreviewModule;
import org.pentaho.reporting.engine.classic.core.modules.gui.commonswing.AbstractActionPlugin;
import org.pentaho.reporting.engine.classic.core.modules.gui.commonswing.ReportEventSource;
import org.pentaho.reporting.engine.classic.core.modules.gui.commonswing.SwingGuiContext;
import org.pentaho.reporting.libraries.base.util.ObjectUtilities;
import org.pentaho.reporting.libraries.base.util.ResourceBundleSupport;

/**
 * Creation-Date: 16.11.2006, 16:34:55
 *
 * @author Thomas Morgner
 */
public class GoToFirstPageActionPlugin extends AbstractActionPlugin implements ControlActionPlugin {
  private class PageUpdateListener implements PropertyChangeListener {
    protected PageUpdateListener() {
    }

    public void propertyChange( final PropertyChangeEvent evt ) {
      revalidate();
    }
  }

  private ResourceBundleSupport resources;
  private ReportEventSource eventSource;
  private PageUpdateListener updateListener;

  public GoToFirstPageActionPlugin() {
    updateListener = new PageUpdateListener();
  }

  public void deinitialize( final SwingGuiContext swingGuiContext ) {
    super.deinitialize( swingGuiContext );
    swingGuiContext.getEventSource().removePropertyChangeListener( updateListener );
  }

  public boolean initialize( final SwingGuiContext context ) {
    super.initialize( context );
    resources =
        new ResourceBundleSupport( context.getLocale(), SwingPreviewModule.BUNDLE_NAME, ObjectUtilities
            .getClassLoader( SwingPreviewModule.class ) );
    eventSource = context.getEventSource();
    eventSource.addPropertyChangeListener( updateListener );
    revalidate();
    return true;
  }

  private void revalidate() {
    if ( eventSource.isPaginated() == false ) {
      setEnabled( false );
      return;
    }
    if ( eventSource.getPageNumber() <= 1 ) {
      setEnabled( false );
      return;
    }

    setEnabled( true );
  }

  protected String getConfigurationPrefix() {
    return "org.pentaho.reporting.engine.classic.core.modules.gui.base.go-to-first."; //$NON-NLS-1$
  }

  /**
   * Returns the display name for the export action.
   *
   * @return The display name.
   */
  public String getDisplayName() {
    return resources.getString( "action.firstpage.name" ); //$NON-NLS-1$
  }

  /**
   * Returns the short description for the export action.
   *
   * @return The short description.
   */
  public String getShortDescription() {
    return resources.getString( "action.firstpage.description" ); //$NON-NLS-1$
  }

  /**
   * Returns the small icon for the export action.
   *
   * @return The icon.
   */
  public Icon getSmallIcon() {
    final Locale locale = getContext().getLocale();
    return getIconTheme().getSmallIcon( locale, "action.firstpage.small-icon" ); //$NON-NLS-1$
  }

  /**
   * Returns the large icon for the export action.
   *
   * @return The icon.
   */
  public Icon getLargeIcon() {
    final Locale locale = getContext().getLocale();
    return getIconTheme().getLargeIcon( locale, "action.firstpage.icon" ); //$NON-NLS-1$
  }

  /**
   * Returns the accelerator key for the export action.
   *
   * @return The accelerator key.
   */
  public KeyStroke getAcceleratorKey() {
    return resources.getOptionalKeyStroke( "action.firstpage.accelerator" ); //$NON-NLS-1$
  }

  /**
   * Returns the mnemonic key code.
   *
   * @return The code.
   */
  public Integer getMnemonicKey() {
    return resources.getOptionalMnemonic( "action.firstpage.mnemonic" ); //$NON-NLS-1$
  }

  public boolean configure( final PreviewPane reportPane ) {
    reportPane.setPageNumber( 1 );
    return true;
  }

}
