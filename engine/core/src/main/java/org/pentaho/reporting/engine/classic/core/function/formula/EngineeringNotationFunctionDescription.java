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


package org.pentaho.reporting.engine.classic.core.function.formula;

import org.pentaho.reporting.libraries.formula.function.AbstractFunctionDescription;
import org.pentaho.reporting.libraries.formula.function.FunctionCategory;
import org.pentaho.reporting.libraries.formula.function.userdefined.UserDefinedFunctionCategory;
import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.formula.typing.coretypes.LogicalType;
import org.pentaho.reporting.libraries.formula.typing.coretypes.NumberType;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

/**
 * Function that pick a number and outputs it's engineering representation
 * <p/>
 * <p/>
 * Date: 23.07.2009 Time: 19:57:42
 *
 * @author Pedro Alves.
 */
public class EngineeringNotationFunctionDescription extends AbstractFunctionDescription {

  public EngineeringNotationFunctionDescription() {
    super( "ENGINEERINGNOTATION",
        "org.pentaho.reporting.engine.classic.core.function.formula.EngineeringNotation-Function" );
  }

  public Type getValueType() {
    return TextType.TYPE;
  }

  public FunctionCategory getCategory() {
    return UserDefinedFunctionCategory.CATEGORY;
  }

  public int getParameterCount() {
    return 3;
  }

  /**
   * Returns the parameter type at the given position using the function metadata. The first parameter is at the
   * position 0;
   *
   * @param position
   *          The parameter index.
   * @return The parameter type.
   */
  public Type getParameterType( final int position ) {
    switch ( position ) {
      case 0:
        return NumberType.GENERIC_NUMBER;

      case 1:
        return NumberType.GENERIC_NUMBER;

      case 2:
        return LogicalType.TYPE;
    }

    return TextType.TYPE;
  }

  /**
   * Defines, whether the parameter at the given position is mandatory. A mandatory parameter must be filled in, while
   * optional parameters need not to be filled in.
   *
   * @return
   */
  public boolean isParameterMandatory( final int position ) {
    if ( position == 2 ) {
      return false;
    } else {
      return true;
    }
  }
}
