
package com.ponysdk.core.export.xml;

import com.ponysdk.core.export.Exporter;

public class XMLExporterFactory {

    public static <T> Exporter<T> newXMLExporter(String rootName, String fileName) {
        return new XMLExporter<T>(rootName, fileName);
    }
}
