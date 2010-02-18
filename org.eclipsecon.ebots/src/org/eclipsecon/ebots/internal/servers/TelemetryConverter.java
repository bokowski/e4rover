package org.eclipsecon.ebots.internal.servers;

import java.util.Map.Entry;

import org.eclipsecon.ebots.core.ITelemetry;
import org.eclipsecon.ebots.internal.core.Telemetry;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TelemetryConverter implements Converter {
	
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return ITelemetry.class.isAssignableFrom(type);
	}


	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ITelemetry t = (ITelemetry)source;
		for(Entry<String, String> e : t.getValues().entrySet()) {
			writer.startNode("t");
			writer.addAttribute("k", e.getKey());
			writer.addAttribute("v", e.getValue());
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		ITelemetry t = new Telemetry();
		while(reader.hasMoreChildren()) {
			reader.moveDown();
			t.getValues().put(reader.getAttribute("k"), reader.getAttribute("v"));
			reader.moveUp();
		}
		return t;
	}

}
