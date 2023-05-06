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

package davaguine.jmac.test;

import davaguine.jmac.core.APESimple;
import davaguine.jmac.info.CompressionLevel;
import davaguine.jmac.tools.ProgressCallback;

/**
 * Author: Dmitry Vaguine
 * Date: 04.03.2004
 * Time: 14:51:31
 */
public class Convert extends ProgressCallback {
    private static long g_nInitialTickCount;

    public void callback(int persent) {
        double dProgress = ((double) (pPercentageDone)) / 1000;
        double dElapsedMS = (System.currentTimeMillis() - g_nInitialTickCount);

        double dSecondsRemaining = (((dElapsedMS * 100) / dProgress) - dElapsedMS) / 1000;
        System.out.println("Progress: " + dProgress + " (" + dSecondsRemaining + " seconds remaining)          ");
    }

    public static void main(String[] args) {
        try {
            ///////////////////////////////////////////////////////////////////////////////
            // error check the command line parameters
            ///////////////////////////////////////////////////////////////////////////////
            if (args.length != 2) {
                System.out.print("~~~Improper Usage~~~\n\n");
                System.out.print("Usage Example: Convert \"c:\\1.ape\" \"c:\\2.ape\"\n\n");
                return;
            }

            ///////////////////////////////////////////////////////////////////////////////
            // variable declares
            ///////////////////////////////////////////////////////////////////////////////
            String pFilename = args[0];		//the file to open
            String pOFilename = args[1];    //the file to open

            ///////////////////////////////////////////////////////////////////////////////
            // attempt to verify the file
            ///////////////////////////////////////////////////////////////////////////////

            // set the start time and display the starting message
            g_nInitialTickCount = System.currentTimeMillis();
            System.out.println("Converting '" + pFilename + "'...");

            // do the verify (call unmac.dll)
            APESimple.ConvertFile(pFilename, pOFilename, CompressionLevel.COMPRESSION_LEVEL_HIGH, new Convert());

            // process the return value
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
