package com.innobitsysytems.ipis.model.setup;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

public class RgbDetails  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	private int r;
	
	@NotBlank
	private int g;
	
	@NotBlank
	private int b;

	@Override
	public String toString() {
		return "RgbDetails [r=" + r + ", g=" + g + ", b=" + b + "]";
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

}
