package com.innobitsysytems.ipis.model.onlineTrain;

import java.time.LocalDate;

import javax.persistence.*;

import novj.publ.util.DateTime.Date;

@Entity
@Table(name = "packetcount")
public class PacketCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "packetCount", nullable = false)
    private int packetCount;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;

	public  LocalDate getDate() {
		return date;
	}

	public void setDate( LocalDate date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPacketCount() {
		return packetCount;
	}

	public void setPacketCount(int packetCount) {
		this.packetCount = packetCount;
	}

}
