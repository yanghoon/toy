package com.example.rbac.console;

import java.util.List;
import java.util.Map;

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

    public static class CompanyConfig {
		private Map<String, String> links;
		private AuthorizationServer authorization;

		// public CompanyConfig(Map<String, String> links, AuthorizationServer authorization){
		// public CompanyConfig(Map<String, String> links){
			// this.links = links;
			// this.authorization = authorization;
		// }

		public Map<String, String> getLinks(){ return links; };
		public void setLinks(Map<String, String> links){ this.links = links; };
		public AuthorizationServer getAuthorization(){ return authorization; };
		public void setAuthorization(AuthorizationServer authorization){ this.authorization = authorization; };
	}

    public static class AuthorizationServer {
		private String kind;
		private String url;
		private Map<String, String> attributes;

		// public AuthorizationServer(String kind, String url, Map<String, String> attributes){
		// public AuthorizationServer(String kind, String url){
		// 	this.kind = kind;
		// 	this.url = url;
		// 	// this.attributes = attributes;
		// }

		public String getKind(){ return kind; }
		public void setKind(String kind){ this.kind = kind; }
		public String getUrl(){ return url; }
		public void setUrl(String url){ this.url = url; }
		public Map<String, String> getAttributes(){ return attributes; };
		public void setAttributes(Map<String, String> attributes){ this.attributes = attributes; };
	}

	public List<Company> list();
	public void create(String name);
	public void enabled(String name, boolean enabled);
	public void remove(String name);

	public CompanyConfig config(String name);
}