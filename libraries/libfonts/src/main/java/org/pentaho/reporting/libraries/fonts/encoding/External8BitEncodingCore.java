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


package org.pentaho.reporting.libraries.fonts.encoding;

/**
 * This is an autogenerated file. The charset contents of this file have been read from '${source}'. This class supports
 * the encoding '${encoding}'.
 */
public final class External8BitEncodingCore implements EncodingCore {
  private char[] byteToChar;
  private byte[] charToByte;

  public External8BitEncodingCore( final External8BitEncodingData resource ) {
    if ( resource == null ) {
      throw new NullPointerException();
    }

    final int[] indexDelta = resource.getIndexDelta();
    final int[] valueDelta = resource.getValueDelta();

    // decompress ...
    byteToChar = new char[ 256 ];
    final int encodingLength = byteToChar.length;
    for ( int i = 0; i < encodingLength; i++ ) {
      byteToChar[ i ] = (char) i;
    }

    int index = 0;
    int value = 0;
    final int indexDeltaLength = indexDelta.length;
    for ( int i = 0; i < indexDeltaLength; i++ ) {
      index += indexDelta[ i ];
      value += valueDelta[ i ];
      byteToChar[ index ] = (char) value;
    }

    // build the backward reference list ...
    charToByte = new byte[ 65536 ];
    for ( int i = 0; i < encodingLength; i++ ) {
      charToByte[ byteToChar[ i ] ] = (byte) i;
    }
  }

  public boolean isUnicodeCharacterSupported( final int c ) {
    if ( c == 0 ) {
      return true;
    }
    return ( charToByte[ c ] != 0 );
  }

  /**
   * Encode, but ignore errors.
   *
   * @param text
   * @param buffer
   * @return
   */
  public ByteBuffer encode( final CodePointBuffer text, ByteBuffer buffer ) {
    final int textLength = text.getLength();
    if ( buffer == null ) {
      buffer = new ByteBuffer( textLength );
    } else if ( buffer.getLength() < textLength ) {
      buffer.ensureSize( textLength );
    }

    final byte[] targetArray = buffer.getData();
    final int[] sourceArray = text.getData();

    int targetIdx = buffer.getOffset();
    final int endPos = text.getCursor();
    for ( int i = text.getOffset(); i < endPos; i++ ) {
      final int sourceItem = sourceArray[ i ];
      if ( isUnicodeCharacterSupported( sourceItem ) ) {
        targetArray[ targetIdx ] = charToByte[ ( sourceItem & 0xffff ) ];
        targetIdx += 1;
      }
    }

    buffer.setCursor( targetIdx );
    return buffer;
  }

  public ByteBuffer encode( final CodePointBuffer text,
                            ByteBuffer buffer,
                            final EncodingErrorType errorHandling )
    throws EncodingException {
    final int textLength = text.getLength();
    if ( buffer == null ) {
      buffer = new ByteBuffer( textLength );
    } else if ( buffer.getLength() < textLength ) {
      buffer.ensureSize( textLength );
    }

    final byte[] targetArray = buffer.getData();
    final int[] sourceArray = text.getData();

    int targetIdx = buffer.getOffset();
    final int endPos = text.getCursor();
    for ( int i = text.getOffset(); i < endPos; i++ ) {
      final int sourceItem = sourceArray[ i ];
      if ( isUnicodeCharacterSupported( sourceItem ) ) {
        targetArray[ targetIdx ] = charToByte[ ( sourceItem & 0xffff ) ];
        targetIdx += 1;
      } else {
        if ( EncodingErrorType.REPLACE.equals( errorHandling ) ) {
          targetArray[ targetIdx ] = (byte) ( '?' & 0xff );
          targetIdx += 1;
        } else if ( EncodingErrorType.FAIL.equals( errorHandling ) ) {
          throw new EncodingException();
        }
      }
    }

    buffer.setCursor( targetIdx );
    return buffer;
  }

  public CodePointBuffer decode( final ByteBuffer text, CodePointBuffer buffer ) {
    final int textLength = text.getLength();
    if ( buffer == null ) {
      buffer = new CodePointBuffer( textLength );
    } else if ( buffer.getLength() < textLength ) {
      buffer.ensureSize( textLength );
    }

    final int[] targetArray = buffer.getData();
    final byte[] sourceArray = text.getData();

    int targetIdx = buffer.getOffset();
    final int endPos = text.getCursor();
    for ( int i = text.getOffset(); i < endPos; i++ ) {
      targetArray[ targetIdx ] = byteToChar[ ( sourceArray[ i ] & 0xff ) ];
      targetIdx += 1;
    }

    buffer.setCursor( targetIdx );
    return buffer;
  }

  public CodePointBuffer decode( final ByteBuffer text,
                                 CodePointBuffer buffer,
                                 final EncodingErrorType errorHandling ) {
    final int textLength = text.getLength();
    if ( buffer == null ) {
      buffer = new CodePointBuffer( textLength );
    } else if ( buffer.getLength() < textLength ) {
      buffer.ensureSize( textLength );
    }

    final int[] targetArray = buffer.getData();
    final byte[] sourceArray = text.getData();

    int targetIdx = buffer.getOffset();
    final int endPos = text.getCursor();
    for ( int i = text.getOffset(); i < endPos; i++ ) {
      targetArray[ targetIdx ] = byteToChar[ ( sourceArray[ i ] & 0xff ) ];
      targetIdx += 1;
    }

    buffer.setCursor( targetIdx );
    return buffer;
  }
}
