package de.pfeiffy.help;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {

	public static int gebezeilenAnzahl(File inFile) {
		BufferedReader f;
		String line;
		int zaehler = 0;
		try {
			f = new BufferedReader(new FileReader(inFile));
			while ((line = f.readLine()) != null) {
				zaehler++;
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen der Datei");
		}
		return zaehler;
	}

	public static double getDistance(double lat1, double lat2, double lon1, double lon2) {

		double dx = 71.5 * (lon1 - lon2);
		double dy = 111.3 * (lat1 - lat2);

		return (Math.sqrt(dx * dx + dy * dy));

	}

	// ----------------------------------
	public static void schreiben(String sLine, String outputFile, boolean anhaengen) {
		// ----------------------------------
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new String(outputFile), anhaengen)));
			out.write(sLine);
			out.newLine();
			out.close();
		}

		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}

	}

	public static int count = 0;
	public static int ax = 0;

	public static int debugStufe = 0;// 0 hei�t alles anzeigen

	// ---------------------------------
	public static void s(String text) {
		// ---------------------------------
		if (debugStufe == 0) {
			System.out.println(text);
		}

	} // ---------------------------------
		// ---------------------------------

	public static void s(boolean text) {
		// ---------------------------------
		if (debugStufe == 0) {
			System.out.println(text);
		}

	} // ---------------------------------
		// ---------------------------------

	public static void s(int text) {
		// ---------------------------------
		if (debugStufe == 0) {
			System.out.println(text);
		}

	} // ---------------------------------

	public static void s(String text, int stufe) {
		// ---------------------------------
		if (stufe == 0) {
			System.out.println(text);
		}

		if (stufe == 8) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
			// SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hh_mm_ss");
			// SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String time1 = sdf.format(time);
			System.out.println(time1 + "   " + text);
		}
	}

	public static String getDateDatei() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return sdf.format(time);
	}

	public static String getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return sdf.format(time);
	}

	// -------------------------------------------------------------------------------------------------
	public static void schreiben(String sLine, String outputFile, boolean datAnzeige, boolean anhaengen) {
		// -------------------------------------------------------------------------------------------------
		try {
			String time1 = "";
			if (datAnzeige) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");
				Timestamp time = new Timestamp(System.currentTimeMillis());
				time1 = sdf.format(time);
				time1 = time1 + "; ";
				System.out.println();
			}

			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile, anhaengen)));
			out.write(time1 + sLine);
			out.newLine();
			out.close();
		}

		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}

	}

	// -------------------------------------------------------
	public static File dateiAnDatei(File inFile, File outFile) {
		// --------------------------------------------------------
		BufferedReader f;
		String line;

		try {
			f = new BufferedReader(new FileReader(inFile));
			while ((line = f.readLine()) != null) {

				Helper.s(line);
				// Linie in einzelne Strings aufteilen
				String[] spalten = line.split(";");

				// nun wird wieder weggeschrieben
				schreiben(line, outFile, true);

			}
			f.close();

		} catch (IOException e) {
			System.out.println("Fehler beim Lesen der Datei");
		}

		return outFile;
	}

	// -------------------------------------------------------
	public static File dateienInDatei(String[] inFiles, String outFile) {
		// --------------------------------------------------------
		BufferedReader f;
		String line;

		for (String datei : inFiles) {

			try {
				f = new BufferedReader(new FileReader(datei));
				while ((line = f.readLine()) != null) {

					Helper.s(line);

					// nun wird wieder weggeschrieben
					schreiben(line, outFile, true);

				}
				f.close();

			} catch (IOException e) {
				System.out.println("Fehler beim Lesen der Datei");
			}
		}

		return new File(outFile);
	}

	// ----------------------------------
	public static void schreiben(String sLine, File outputFile, boolean anhaengen) {
		// ----------------------------------
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile, anhaengen)));
			out.write(sLine);
			out.newLine();
			out.close();
		}

		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}

	}

	// -------------------------------------------------------------
	public static void schreibenXML(String sLine, String outputFile, boolean anhaengen) {
		// ---------------------------------------------------------
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile, anhaengen)));
			out.write(sLine);
			out.newLine();
			out.close();
		}

		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}

	}

	// ----------------------------------------------------------------
	public static ArrayList<String> file2ArrayList(String filename) {
		// ----------------------------------------------------------------
		BufferedReader f;
		ArrayList<String> list = new ArrayList<String>();
		String line;
		try {
			f = new BufferedReader(new FileReader(filename));
			while ((line = f.readLine()) != null) {

				// nun wird wieder weggeschrieben
				list.add(line);

			}
			f.close();

		} catch (IOException e) {
			System.out.println("Fehler beim Lesen der Datei");
		}

		return list;
	}

	// ----------------------------------------------------------------
	public static String[] ArrayList2StringList(ArrayList arrayList) {
		// ----------------------------------------------------------------

		String[] outStringList = new String[arrayList.size()];
		for (int x = 0; x < arrayList.size(); x++) {
			outStringList[x] = (String) arrayList.get(x);

		}

		return outStringList;
	}

	// ----------------------------------------------------------------
	public static Map<String, String> file2MapKey(String filename, int key, int value) {
		// ----------------------------------------------------------------
		BufferedReader f;
		Map<String, String> map = new HashMap<String, String>();

		String line;
		try {
			f = new BufferedReader(new FileReader(filename));
			while ((line = f.readLine()) != null) {
				String[] daten = line.split(";");
				if (daten.length > 0) {
					if (!daten[key].equalsIgnoreCase("") && !daten[value].equalsIgnoreCase("")) {

						// nun wird wieder weggeschrieben
						map.put(daten[key], daten[value]);
					}
				}
			}
			f.close();

		} catch (IOException e) {
			System.out.println("Fehler beim Lesen der Datei");
		}

		return map;
	}

	// ----------------------------------------------------------------
	public static String file2String(String fileName) {
	// ----------------------------------------------------------------
		FileReader fr = null;
		StringBuffer sb = new StringBuffer();

		try {
			fr = new FileReader(fileName);

			int ch;
			while ((ch = fr.read()) != -1)
				sb.append((char) ch);

			System.out.println(sb.toString());
		}
		/*
		 * catch(FileNotFoundException ex) { System.out.println(ex); }
		 */
		catch (IOException ex) {
			System.out.println(ex);
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (Exception ex) {
			}
		}
		return sb.toString();

	}

	public static String getName(int x) {

		String buchs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String ret = "";

		if (x < 26) {
			ret = buchs.substring(x, x + 1);
		}

		if (x > 25) {

			ret = buchs.substring(ax, ax + 1) + buchs.substring(count, count + 1);
			if (count == 25) {
				ax++;
				count = -1;

			}

			count++;
		}

		return "\"" + ret + "\"";

	}

	public static ArrayList<String> getVerzeichnisliste(String Verzeichnis, String endung) {
		File userdir = new File(Verzeichnis);
		ArrayList<String> Dateiliste = new ArrayList<String>();
		for (String entry : userdir.list(new TxtFilenameFilter(endung))) {
			Dateiliste.add(Verzeichnis + "/" + entry);
		}
		return Dateiliste;
	}

	static class TxtFilenameFilter implements FilenameFilter {
		String endung;

		public TxtFilenameFilter(String endung) {
			this.endung = endung;
		}

		public boolean accept(File f, String s) {
			return s.toLowerCase().endsWith("." + endung);
		}
	}

}
