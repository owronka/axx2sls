package com.axxessio.axx2sls.content.facade.to;

import java.util.ArrayList;
import java.util.List;

import com.axxessio.axx2sls.content.service.pdo.Area;

public class AreaTO extends GenericTO {

	private List<Area> atos;
	
	public AreaTO () {
	}
	
	public AreaTO (List<Area> newAtos) {
		this.atos = newAtos;
	}

	public AreaTO (Area newArea) {
		this.atos = new ArrayList<Area>();
		
		this.atos.add(newArea);
	}

	public List<Area> getAtos() {
		return atos;
	}
}
