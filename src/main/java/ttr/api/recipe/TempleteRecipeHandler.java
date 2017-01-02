package ttr.api.recipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.recipe.TempleteRecipeHandler.Recipe;
import ttr.api.recipe.TempleteRecipeHandler.UnbakedRecipe;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.ArrayStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.Log;

public abstract class TempleteRecipeHandler<R extends Recipe, U extends UnbakedRecipe<R>>
{
	protected static final JsonSerializer<AbstractStack> ABSTRACT_STACK_SERIALIZER =
			(AbstractStack src, Type typeOfSrc, JsonSerializationContext context) ->
	{
		JsonObject object = new JsonObject();
		if(src instanceof BaseStack)
		{
			BaseStack stack = (BaseStack) src;
			object.addProperty("size", stack.stack.stackSize);
			ResourceLocation location = stack.stack.getItem().getRegistryName();
			object.addProperty("item", location.toString());
			if(stack.stack.getHasSubtypes() &&
					stack.stack.getItemDamage() != OreDictionary.WILDCARD_VALUE)
			{
				object.addProperty("meta", stack.stack.getItemDamage());
			}
			return object;
		}
		else if(src instanceof ArrayStack)
		{
			ArrayStack stack = (ArrayStack) src;
			JsonArray array = new JsonArray();
			for(ItemStack stack2 : stack.array)
			{
				JsonObject object2 = new JsonObject();
				object2.addProperty("item", stack2.getItem().getRegistryName().toString());
				array.add(object2);
			}
			object.add("items", array);
			return object;
		}
		else if(src instanceof OreStack)
		{
			OreStack stack = (OreStack) src;
			object.addProperty("size", stack.size);
			object.addProperty("ore", stack.oreName);
			return object;
		}
		return null;
	};
	protected static final JsonDeserializer<AbstractStack> ABSTRACT_STACK_DESERIALIZER =
			(JsonElement json, Type typeOfT, JsonDeserializationContext context) ->
	{
		AbstractStack stack;
		JsonObject object = json.getAsJsonObject();
		int size = object.has("size") ? object.get("size").getAsInt() : 1;
		if(object.has("ore"))
		{
			String ore = object.get("ore").getAsString();
			stack = new OreStack(ore, size);
		}
		else if(object.has("items"))
		{
			List<ItemStack> list = new ArrayList();
			for(JsonElement element : object.getAsJsonArray("items"))
			{
				JsonObject object1 = element.getAsJsonObject();
				String item = object1.get("item").getAsString();
				String modid;
				if(object1.has("modid"))
				{
					modid = object1.get("modid").getAsString();
				}
				else if(item.indexOf(':') != -1)
				{
					String[] strings = item.split(":");
					modid = strings[0];
					item = strings[1];
				}
				else
				{
					modid = "minecraft";
				}
				Item it = Item.REGISTRY.getObject(new ResourceLocation(modid, item));
				if(it == null)
					throw new JsonParseException("The item name '" + modid + ":" + item + "' does not exists.");
				int meta = OreDictionary.WILDCARD_VALUE;
				if(object.has("meta"))
				{
					meta = object.get("meta").getAsInt();
				}
				list.add(new ItemStack(it, 1, meta));
			}
			stack = new ArrayStack(size, list);
		}
		else if(object.has("item"))
		{
			String item = object.get("item").getAsString();
			String modid;
			if(object.has("modid"))
			{
				modid = object.get("modid").getAsString();
			}
			else if(item.indexOf(':') != -1)
			{
				String[] strings = item.split(":");
				modid = strings[0];
				item = strings[1];
			}
			else
			{
				modid = "minecraft";
			}
			Item it = Item.REGISTRY.getObject(new ResourceLocation(modid, item));
			if(it == null)
				throw new JsonParseException("The item name '" + modid + ":" + item + "' does not exists.");
			int meta = OreDictionary.WILDCARD_VALUE;
			if(object.has("meta"))
			{
				meta = object.get("meta").getAsInt();
			}
			stack = new BaseStack(it, size, meta);
		}
		else throw new JsonParseException("No valid stack detected!");
		return stack;
	};
	private static final List<TempleteRecipeHandler> HANDLERS = new ArrayList();
	
	public static void load(File file)
	{
		for(TempleteRecipeHandler handler : HANDLERS)
		{
			try
			{
				handler.loadRecipe(file);
			}
			catch(Exception exception)
			{
				;
			}
		}
	}
	public static void reload()
	{
		for(TempleteRecipeHandler handler : HANDLERS)
		{
			handler.reloadRecipes();
		}
	}
	
	protected final Gson gson;
	protected final String name;
	private Map<String, U> unbakedRecipes = new HashMap();
	private final Map<String, R> recipes = new HashMap();
	
	public TempleteRecipeHandler(String name)
	{
		HANDLERS.add(this);
		this.name = name;
		this.gson = createGson();
	}
	
	public String name()
	{
		return this.name;
	}
	
	public void loadRecipe(File file)
	{
		JsonReader reader = null;
		JsonWriter writer = null;
		try
		{
			File file1;
			file1 = new File(file, this.name + "_auto.json");
			if(!file1.exists())
			{
				file1.createNewFile();
			}
			if(file1.canWrite())
			{
				writer = new JsonWriter(new BufferedWriter(new FileWriter(file1)));
				writer.setIndent("	");
				writer.beginArray();
				for(U unbakedRecipe : this.unbakedRecipes.values())
				{
					this.gson.toJson(unbakedRecipe, getRecipeClass(), writer);
				}
				writer.endArray();
				writer.close();
				writer = null;
			}
			file1 = new File(file, this.name + "_user.json");
			if(file1.canRead())
			{
				reader = new JsonReader(new BufferedReader(new FileReader(file1)));
				reader.beginArray();
				while(reader.hasNext())
				{
					U unbakedRecipe = this.gson.fromJson(reader, getRecipeClass());
					this.unbakedRecipes.put(unbakedRecipe.name(), unbakedRecipe);
				}
				reader.endArray();
				reader.close();
				reader = null;
			}
			else if(!file1.exists())
			{
				file1.createNewFile();
			}
		}
		catch(IOException exception)
		{
			Log.info("Unable to load from file.");
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
				}
				if(writer != null)
				{
					writer.close();
				}
			}
			catch (IOException exception2)
			{
				exception2.printStackTrace();
			}
		}
	}
	public void addRecipe(U recipe)
	{
		this.unbakedRecipes.put(recipe.name(), recipe);
	}
	public void addRecipe(R recipe)
	{
		this.recipes.put(recipe.name, recipe);
	}
	public void reloadRecipes()
	{
		for(Entry<String, U> entry : this.unbakedRecipes.entrySet())
		{
			this.recipes.put(entry.getKey(), entry.getValue().bake());
		}
	}
	public abstract Class<U> getRecipeClass();
	public abstract Gson createGson();
	public Collection<R> getRecipes()
	{
		return this.recipes.values();
	}
	public R readFromNBT(String key, NBTTagCompound nbt, R recipe)
	{
		return this.recipes.get(nbt.getString(key));
	}
	public NBTTagCompound writeToNBT(String key, NBTTagCompound nbt, R recipe)
	{
		if(recipe != null)
		{
			nbt.setString(key, recipe.name);
		}
		return nbt;
	}
	
	public static abstract class UnbakedRecipe<R extends Recipe>
	{
		public abstract String name();
		
		public abstract R bake();
	}
	
	public static abstract class Recipe
	{
		final String name;
		
		public Recipe(String name)
		{
			this.name = name;
		}
		
		public abstract boolean isValid();
	}
}