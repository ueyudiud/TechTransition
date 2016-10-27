package farcore.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import ttr.api.material.Mat;
import ttr.api.material.MatCondition;

public class ModelFileCreator
{
	static final Gson gson = new Gson();
	
	static void provideGroupItemInfo(String sourceLocate, MatCondition condition)
	{
		for(Mat material : Mat.register())
		{
			if(condition.isBelongTo(material))
			{
				String locate = condition.orePrefix.toLowerCase() + "/" + material.name;
				JsonObject object = new JsonObject();
				object.addProperty("parent", "item/generated");
				addTextures(object, "layer0", material.modid + ":items/" + locate);
				makeJson(sourceLocate, material.modid + "/models/item/"+ locate, object);
			}
		}
	}
	
	static void addCondition(JsonObject object, String prop, String value)
	{
		JsonObject object1 = new JsonObject();
		object1.addProperty(prop, value);
		object.add("when", object1);
	}

	static void addModel(JsonObject object, String locate)
	{
		JsonObject object1 = new JsonObject();
		object1.addProperty("model", locate);
		object.add("apply", object1);
	}

	static void addModel(JsonObject object, String locate, int x, int y)
	{
		JsonObject object1 = new JsonObject();
		object1.addProperty("model", locate);
		object1.addProperty("x", x * 90);
		object1.addProperty("y", y * 90);
		object.add("apply", object1);
	}

	static void addTextures(JsonObject object, String locate)
	{
		addTextures(object, "all", locate);
	}
	
	static void addTextures(JsonObject object, String name, String locate)
	{
		JsonObject object1 = new JsonObject();
		object1.addProperty(name, locate);
		object.add("textures", object1);
	}
	
	static void addTextures(JsonObject object, Entry<String, String>...locates)
	{
		JsonObject object1 = new JsonObject();
		for(Entry<String, String> entry : locates)
		{
			object1.addProperty(entry.getKey(), entry.getValue());
		}
		object.add("textures", object1);
	}

	static void makeJson(String sourceLocate, String pathName, JsonObject object)
	{
		pathName = pathName.replace(':', '/');
		if(!pathName.endsWith(".json"))
		{
			pathName += ".json";
		}
		JsonWriter writer = null;
		try
		{
			File file = new File(sourceLocate, pathName);
			File file2 = file.getParentFile();
			if (!file2.exists())
			{
				file2.mkdirs();
			}
			if (!file.exists())
			{
				file.createNewFile();
			}
			writer = new JsonWriter(new BufferedWriter(new FileWriter(file)));
			writer.setIndent("	");
			gson.toJson(object, writer);
			System.out.println("Generated json at ./" + pathName);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (Exception exception2)
				{
					exception2.printStackTrace();
				}
			}
		}
	}
}