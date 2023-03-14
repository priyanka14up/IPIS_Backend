/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Help Model
 */
package com.innobitsysytems.ipis.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name="help")
@EntityListeners(AuditingEntityListener.class)
public class Help {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;

	    @Column(name = "helpTopic", nullable = false)
	    private String helpTopic;

	    @Column(name = "helpAnswer", nullable = false)
	    private String helpAnswer;
	    
	    @CreationTimestamp
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "createdAt", nullable = false)
	    private Date createdAt;

	    @Column(name = "createdBy", nullable = false)
	    @CreatedBy
	    private int createdBy;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getHelpTopic() {
			return helpTopic;
		}

		public void setHelpTopic(String helpTopic) {
			this.helpTopic = helpTopic;
		}

		public String getHelpAnswer() {
			return helpAnswer;
		}

		public void setHelpAnswer(String helpAnswer) {
			this.helpAnswer = helpAnswer;
		}

		public Date getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}

		public int getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(int createdBy) {
			this.createdBy = createdBy;
		}

	   

		
	    
	

}
