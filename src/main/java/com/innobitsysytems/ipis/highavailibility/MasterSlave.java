package com.innobitsysytems.ipis.highavailibility;
import java.util.Timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterSlave
{

	@Bean
	public void start() {
		//sending HeartBeat
		Timer senderTime = new Timer();

		SendingHearbeats sHeartBeat = new SendingHearbeats();

		senderTime.schedule(sHeartBeat, 0, 5000);

		// Receiving HeartBeats

		Timer receiverTime = new Timer();

		ReceivingHeartBeats rHeartBeat = new ReceivingHeartBeats();

		receiverTime.schedule(rHeartBeat, 0, 5000);

	}
}
