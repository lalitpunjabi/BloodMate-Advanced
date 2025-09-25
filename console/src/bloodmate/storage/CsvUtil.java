package bloodmate.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CsvUtil {
	private CsvUtil() {}

	public static List<String[]> readAll(Path path) throws IOException {
		List<String[]> rows = new ArrayList<>();
		if (!Files.exists(path)) return rows;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				rows.add(parse(line));
			}
		}
		return rows;
	}

	public static void writeAll(Path path, List<String[]> rows) throws IOException {
		Files.createDirectories(path.getParent());
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (String[] row : rows) {
				writer.write(escape(row));
				writer.newLine();
			}
		}
	}

	private static String[] parse(String line) {
		List<String> out = new ArrayList<>();
		StringBuilder cell = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '"') {
				if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
					cell.append('"');
					i++;
				} else {
					inQuotes = !inQuotes;
				}
			} else if (c == ',' && !inQuotes) {
				out.add(cell.toString());
				cell.setLength(0);
			} else {
				cell.append(c);
			}
		}
		out.add(cell.toString());
		return out.toArray(new String[0]);
	}

	private static String escape(String[] row) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < row.length; i++) {
			String cell = row[i] == null ? "" : row[i];
			boolean needQuotes = cell.contains(",") || cell.contains("\"") || cell.contains("\n") || cell.contains("\r");
			if (needQuotes) {
				sb.append('"').append(cell.replace("\"", "\"\"")).append('"');
			} else {
				sb.append(cell);
			}
			if (i < row.length - 1) sb.append(',');
		}
		return sb.toString();
	}
} 