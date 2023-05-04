package io.scvis;

import java.io.IOException;
import java.lang.reflect.Type;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import io.scvis.geometry.Border2D;
import io.scvis.geometry.Kinetic2D;
import io.scvis.geometry.Shape.Circle;
import io.scvis.geometry.Vector2D;

public class TestSerialisation {
	static Kinetic2D object = new Kinetic2D(new Circle(new Vector2D(5, 5), 5), Vector2D.ZERO, 0, Vector2D.ZERO,
			Vector2D.ZERO) {
		@Override
		public void accelerate(double deltaT) {
		}

		@Override
		public void velocitate(double deltaT) {
		}

		@Override
		public void displacement(double deltaT) {
		}
	};
}

class TestJacksonJson {

	@Test
	void serialisation() throws StreamWriteException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(TestSerialisation.object));
	}

	@Test
	void deserialisation() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Kinetic2D kinetic2D = mapper.readValue(mapper.writeValueAsString(TestSerialisation.object), Kinetic2D.class);
		System.out.println(kinetic2D.getPosition());
	}
}

class TestJacksonXml {

	@Test
	void serialisation() throws StreamWriteException, DatabindException, IOException {
		ObjectMapper mapper = new XmlMapper();
		System.out.println(mapper.writeValueAsString(TestSerialisation.object));
	}

	@Test
	void deserialisation() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new XmlMapper();
		Kinetic2D kinetic2D = mapper.readValue(mapper.writeValueAsString(TestSerialisation.object),
				TestSerialisation.object.getClass());
		System.out.println(kinetic2D.getPosition());
	}
}

class TestGson {

	@Test
	void serialisation() {
		Gson gson = new Gson();
		System.out.println(gson.toJson(TestSerialisation.object));
	}

	@Test
	void deserialisation() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Border2D.class, new InstanceCreator<Border2D>() {
			@Override
			public Border2D createInstance(Type type) {
				return new Circle(new Vector2D(5, 5), 5);
			}
		}).create();
		System.out.println(gson.fromJson(gson.toJson(TestSerialisation.object), Kinetic2D.class).getBorderInLocal());
	}
}
