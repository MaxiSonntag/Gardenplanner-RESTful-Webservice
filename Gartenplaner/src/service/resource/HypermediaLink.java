package service.resource;

import java.net.URI;

public class HypermediaLink {
	
	private String rel;
	
	private URI href;
	
	private String title;
	
	private String method;
	
	

	public HypermediaLink(String rel, URI href, String title, String method) {
		super();
		this.rel = rel;
		this.href = href;
		this.title = title;
		this.method = method;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	

}
