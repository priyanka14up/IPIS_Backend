/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Ntes Service
 */
package com.innobitsysytems.ipis.services.ntes;

import com.innobitsysytems.ipis.dto.NtesDto;

public interface NtesService {

    public void scheduledTrainSync(NtesDto[] apiData);
    public void liveTrainSync(NtesDto[] apiData);





}
