/*
 *  21.04.2004 Original verion. davagin@udm.ru.
 *-----------------------------------------------------------------------
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package davaguine.jmac.spi;

import davaguine.jmac.decoder.IAPEDecompress;
import davaguine.jmac.tools.Globals;
import davaguine.jmac.tools.InputStreamFile;
import davaguine.jmac.tools.JMACException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;
import java.io.*;
import java.net.URL;

/**
 * Author: Dmitry Vaguine
 * Date: 12.03.2004
 * Time: 13:35:13
 */

/**
 * Provider for MAC audio file reading.
 */
public class APEAudioFileReader extends AudioFileReader {
    private final static int MAX_HEADER_SIZE = 16384;

    /**
     * Obtains the audio file format of the File provided. The File must point to valid audio file data.
     *
     * @param file - the File from which file format information should be extracted
     * @return an APEAudioFileFormat object describing the MAC audio file format
     * @throws UnsupportedAudioFileException - if the File does not point to valid MAC audio file
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioFileFormat getAudioFileFormat(File file) throws UnsupportedAudioFileException, IOException {
        if (Globals.DEBUG) System.out.println("APEAudioFileReader.getAudioFileFormat( File )");
        IAPEDecompress decoder;
        try {
            decoder = IAPEDecompress.CreateIAPEDecompress(new davaguine.jmac.tools.RandomAccessFile(file, "r"));
        } catch (JMACException e) {
            throw new UnsupportedAudioFileException("Unsupported audio file");
        } catch (EOFException e) {
            throw new UnsupportedAudioFileException("Unsupported audio file");
        }

        APEAudioFormat format = new APEAudioFormat(APEEncoding.APE, decoder.getApeInfoSampleRate(),
                decoder.getApeInfoBitsPerSample(),
                decoder.getApeInfoChannels(),
                AudioSystem.NOT_SPECIFIED, AudioSystem.NOT_SPECIFIED, false);

        return new APEAudioFileFormat(APEAudioFileFormatType.APE, (int) file.length(), format, AudioSystem.NOT_SPECIFIED);
    }

    /**
     * Obtains an audio input stream from the URL provided. The URL must point to valid MAC audio file data.
     *
     * @param url - the URL from which file format information should be extracted
     * @return an APEAudioFileFormat object describing the MAC audio file format
     * @throws UnsupportedAudioFileException - if the URL does not point to valid MAC audio file
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileException, IOException {
        InputStream inputStream = url.openStream();
        try {
            return getAudioFileFormat(inputStream);
        } finally {
            inputStream.close();
        }
    }

    /**
     * Obtains the audio file format of the input stream provided. The stream must point to valid MAC audio file data.
     *
     * @param stream - the input stream from which file format information should be extracted
     * @return an APEAudioFileFormat object describing the MAC audio file format
     * @throws UnsupportedAudioFileException - if the stream does not point to valid MAC audio file
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioFileFormat getAudioFileFormat(InputStream stream) throws UnsupportedAudioFileException, IOException {
        if (Globals.DEBUG) System.out.println("APEAudioFileReader.getAudioFileFormat( InputStream )");
        IAPEDecompress decoder;
        try {
            decoder = IAPEDecompress.CreateIAPEDecompress(new InputStreamFile(stream));
        } catch (JMACException e) {
            throw new UnsupportedAudioFileException("Unsupported audio file");
        } catch (EOFException e) {
            throw new UnsupportedAudioFileException("Unsupported audio file");
        }

        APEAudioFormat format = new APEAudioFormat(APEEncoding.APE, decoder.getApeInfoSampleRate(),
                decoder.getApeInfoBitsPerSample(),
                decoder.getApeInfoChannels(),
                AudioSystem.NOT_SPECIFIED, AudioSystem.NOT_SPECIFIED, false);

        return new APEAudioFileFormat(APEAudioFileFormatType.APE, AudioSystem.NOT_SPECIFIED, format, AudioSystem.NOT_SPECIFIED);
    }

    /**
     * Obtains an audio input stream from the input stream provided. The stream must point to valid MAC audio file data.
     *
     * @param stream - the input stream from which the AudioInputStream should be constructed
     * @return an AudioInputStream object based on the audio file data contained in the input stream.
     * @throws UnsupportedAudioFileException - if the stream does not point to valid MAC audio file data recognized by the system
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioInputStream getAudioInputStream(InputStream stream) throws UnsupportedAudioFileException, IOException {
        // Save byte header since this method must return the stream opened at byte 0.
        final BufferedInputStream in = new BufferedInputStream(stream);
        in.mark(MAX_HEADER_SIZE);
        final AudioFileFormat format = getAudioFileFormat(in);
        in.reset();
        return new AudioInputStream(in, format.getFormat(), format.getFrameLength());
    }

    /**
     * Obtains an audio input stream from the File provided. The File must point to valid MAC audio file data.
     *
     * @param file - the File for which the AudioInputStream should be constructed
     * @return an AudioInputStream object based on the audio file data contained in the input stream.
     * @throws UnsupportedAudioFileException - if the file does not point to valid MAC audio file data recognized by the system
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
        InputStream inputStream = new FileInputStream(file);
        try {
            return getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileException e) {
            inputStream.close();
            throw e;
        } catch (IOException e) {
            inputStream.close();
            throw e;
        }
    }

    /**
     * Obtains the audio file format of the URL provided. The URL must point to valid MAC audio file data.
     *
     * @param url - the URL for which the AudioInputStream should be constructed
     * @return an AudioInputStream object based on the audio file data contained in the input stream.
     * @throws UnsupportedAudioFileException - if the URL does not point to valid MAC audio file data recognized by the system
     * @throws IOException                   - if an I/O exception occurs
     */
    public AudioInputStream getAudioInputStream(URL url) throws UnsupportedAudioFileException, IOException {
        InputStream inputStream = url.openStream();
        try {
            return getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileException e) {
            inputStream.close();
            throw e;
        } catch (IOException e) {
            inputStream.close();
            throw e;
        }
    }

}
