package test;

import java.util.List;

import com.google.common.collect.Lists;

import test.client.ClientProvider;

public class ClientCache {
	// public Set<ClientProvider> providers = Sets.newHashSet();
	public static List<ClientProvider<?>> providers;

	public static <T> T get(Class<T> clazz, String key) {
		return null;
	}

	public static void setProviders(ClientProvider<?>... providers) {
		ClientCache.providers = Lists.newArrayList(providers);
	}
}