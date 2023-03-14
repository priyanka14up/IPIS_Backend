package com.innobitsysytems.ipis.licensing;

import com.innobitsysytems.ipis.exception.HandledException;

public interface LicenseService {
	public boolean keyAuthenticate(KeyInputDto keyInputDto) throws HandledException;
	public String keyGenerate(KeyInputDto keyInputDto) throws HandledException;
	public boolean macAuthenticate(KeyInputDto keyInputDto) throws HandledException;

}
