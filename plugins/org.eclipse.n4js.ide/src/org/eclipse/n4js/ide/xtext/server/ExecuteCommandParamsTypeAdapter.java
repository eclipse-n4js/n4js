/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Type adapter that serializes / deserializes the arguments of ExecuteCommandParams according the the available
 * descriptions as provided by a ExecuteCommandParamsDescriber.
 */
public class ExecuteCommandParamsTypeAdapter extends TypeAdapter<ExecuteCommandParams> {

	private static Type[] EMPTY_TYPE_ARRAY = {};

	/**
	 * The factory for a ExecuteCommandParamsTypeAdapter.
	 */
	public static class Factory implements TypeAdapterFactory {

		private final XLanguageServerImpl languageServer;

		/**
		 * Create the serialization logic for ExecuteCommandParams
		 */
		public Factory(XLanguageServerImpl languageServer) {
			this.languageServer = languageServer;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!ExecuteCommandParams.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			ExecutableCommandRegistry commandRegistry = languageServer.getCommandRegistry();
			Map<String, Type[]> argumentTypes = Collections.emptyMap();
			if (commandRegistry instanceof ExecuteCommandParamsDescriber) {
				argumentTypes = ((ExecuteCommandParamsDescriber) commandRegistry).argumentTypes();
			}
			return (TypeAdapter<T>) new ExecuteCommandParamsTypeAdapter(argumentTypes, gson);
		}

	}

	private final Gson gson;
	private final Map<String, Type[]> argumentTypes;

	private ExecuteCommandParamsTypeAdapter(Map<String, Type[]> argumentTypes, Gson gson) {
		this.argumentTypes = argumentTypes;
		this.gson = gson;
	}

	@Override
	public void write(JsonWriter out, ExecuteCommandParams value) throws IOException {
		out.beginObject();
		out.name("command");
		out.value(value.getCommand());
		out.name("arguments");
		out.beginArray();
		for (Object argument : value.getArguments()) {
			gson.toJson(argument, argument.getClass(), out);
		}
		out.endArray();
		out.endObject();
	}

	@Override
	public ExecuteCommandParams read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		ExecuteCommandParams result = new ExecuteCommandParams();
		in.beginObject();
		String command = null;
		while (in.hasNext()) {
			String name = in.nextName();
			switch (name) {
			case "command": {
				command = in.nextString();
				result.setCommand(command);
				break;
			}
			case "arguments": {
				result.setArguments(parseArguments(in, command));
				break;
			}
			default:
				in.skipValue();
			}
		}
		in.endObject();
		return result;
	}

	@SuppressWarnings("hiding")
	private List<Object> parseArguments(JsonReader in, String command) throws IOException, JsonIOException {
		JsonToken next = in.peek();
		if (next == JsonToken.NULL) {
			in.nextNull();
			return Collections.emptyList();
		}
		Type[] argumentTypes = getArgumentTypes(command);
		List<Object> arguments = new ArrayList<>(argumentTypes.length);
		int index = 0;
		in.beginArray();
		while (in.hasNext()) {
			Type parameterType = index < argumentTypes.length ? argumentTypes[index] : null;
			Object argument = fromJson(in, parameterType);
			arguments.add(argument);
			index++;
		}
		in.endArray();
		while (index < argumentTypes.length) {
			arguments.add(null);
			index++;
		}
		return arguments;
	}

	private Object fromJson(JsonReader in, Type type) throws JsonIOException {
		if (isNullOrVoidType(type)) {
			return new JsonParser().parse(in);
		}
		return gson.fromJson(in, type);
	}

	private boolean isNullOrVoidType(Type type) {
		return type == null || Void.class == type;
	}

	private Type[] getArgumentTypes(String command) {
		return argumentTypes.getOrDefault(command, EMPTY_TYPE_ARRAY);
	}

}
