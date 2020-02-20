package com.example.rbac.console;

import java.util.List;

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

	public List<Company> list();
	public void create(String name);
	public void enabled(String name, boolean enabled);
	public void remove(String name);
}