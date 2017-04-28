package com.axxessio.axx2sls.content.facade.to;

import java.util.List;

import com.axxessio.axx2sls.content.service.pdo.Area;
import com.axxessio.axx2sls.content.service.pdo.Picture;

public class PictureTO extends GenericTO {
	private Area area;
	private List<Picture> ptos;
	
	public PictureTO () {
	}
	
	public PictureTO (Area newArea, List<Picture> newPtos) {
		this.area = newArea;
		this.ptos = newPtos;
	}

	public Area getArea() {
		return area;
	}
	public List<Picture> getPtos() {
		return ptos;
	}
}
