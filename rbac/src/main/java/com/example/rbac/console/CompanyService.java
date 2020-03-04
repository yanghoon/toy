package com.example.rbac.console;

import java.util.List;
import java.util.Map;

import lombok.Data;

public interface CompanyService {

    public static class Company {
		private String name;
		private boolean enabled;

		public Company(String name, boolean enabled) {
			this.name = name;
			this.enabled = enabled;
		}

		public String getName(){ return name; }
		public boolean getEnabled(){ return enabled; }
	}

	@Data
    public static class Tool {
		private String kind;
		private String name;
		private String endpoint;
		private Map<String, String> attributes;
	}

	@Data
    public static class Integrations {
		private List<Tool> tools;
	}

	public List<Company> list();
	public void create(String name);
	public void enabled(String name, boolean enabled);
	public void remove(String name);

	public Integrations integrations(String name);
}