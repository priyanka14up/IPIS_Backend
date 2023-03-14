
/**
 * 
 */
package com.innobitsysytems.ipis.services.announcement;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import java.time.LocalDateTime;
import java.util.*;

	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.AnnouncementPlaylistDto;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.repository.announcement.AnnouncementPlaylistRepository;
import com.innobitsysytems.ipis.repository.announcement.PublicAnnouncementRepository;
import com.innobitsysytems.ipis.utility.CustomUtil;

/**
 * @author apoorva gupta
 *
 */
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAnnouncementBean {

	@InjectMocks
	AnnouncementBean announcementservice;

	@Mock
	private TestRestTemplate restTemplate;

	@Autowired
	public PublicAnnouncementRepository publicAnnRepo;

	@Autowired
	public AnnouncementPlaylistRepository announcementPlaylistRepo;

	@Mock
	public CustomUtil customUtil;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	LocalDateTime date = LocalDateTime.now();
	Date dateTime = new Date();

	@Test
	public void testList() {
		List<PublicAnnouncement> pAData = publicAnnRepo.findAll();
		if (pAData.size() > 0) {
			List<PublicAnnouncementDto> pADataDto = announcementservice.entityToDto(pAData);
			Collections.reverse(pADataDto);
			Assert.assertNotNull(pADataDto);
		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("No Data is present in Public Announcement");

		}

	}

	@Test
	public void testCreate() throws HandledException {

		PublicAnnouncementDto publicAnn = new PublicAnnouncementDto();
		publicAnn.setId(20l);
		publicAnn.setFileName("new abc");
		publicAnn.setFileLocation("C://Users//Downloads123456");
		publicAnn.setFileUrl("d:/abc");
		publicAnn.setMessageType("newMessage");

		PublicAnnouncement pAData = publicAnnRepo.findByFileLocation(publicAnn.getFileLocation());

		PublicAnnouncement publicAnnouncement = announcementservice.dtoToEntity(publicAnn);
		if (pAData == null) {
			publicAnnRepo.save(publicAnnouncement);
			announcementservice.entityToDto(publicAnnouncement);
			Assert.assertNotNull(publicAnnouncement);
			Assert.assertEquals(publicAnn.getFileName(), publicAnnouncement.getFileName());
		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("File exist in Database");
			Assert.assertNotNull(pAData);
		}

	}

	@Test
	public void testDelete() {

		PublicAnnouncement pAData = publicAnnRepo.getById(19L);
		if (pAData == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("File not found in Database");
			Assert.assertNull(pAData);
		} else {
			publicAnnRepo.delete(pAData);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			Assert.assertNotNull(response);

		}

	}

	@Test
	public void testMoveToPlaylist() {

		PublicAnnouncement announcementData = publicAnnRepo.findByFileLocation("C://Users//Downloads123456");

		if (announcementData == null) {
			Assert.assertNull(null);
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("File not found in Database");

		} else {
			AnnouncementPlaylist announcementPlaylist = new AnnouncementPlaylist();

			announcementPlaylist.setFileName(announcementData.getFileName());
			announcementPlaylist.setCreatedAt(dateTime);
			announcementPlaylist.setUpdatedAt(dateTime);
			announcementPlaylist.setCreatedBy("1");
			Assert.assertNotNull(announcementPlaylist);
			Assert.assertEquals(announcementData.getFileName(), announcementPlaylist.getFileName());
		}
	}

	/////////////////////////////////////// Playlist
	/////////////////////////////////////// Function/////////////////////////////////////////////

	@Test
	public void testGetAll() {
		List<AnnouncementPlaylist> pAData = announcementPlaylistRepo.findAll();
		if (pAData.size() > 0) {

			List<AnnouncementPlaylistDto> pADataDto = announcementservice.playlistEntityToDto(pAData);
			Collections.reverse(pADataDto);
			Assert.assertNotNull(pADataDto);

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("No File is present in Playlist");

		}
	}

	@Test
	public void testGetSingleFile() throws HandledException {

		AnnouncementPlaylist pAData = announcementPlaylistRepo.getById(20L);
		if (pAData == null) {
			Assert.assertNull(pAData);
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Public Announcement File not found in Database");

		} else {
			Assert.assertNotNull(pAData);

		}
	}

	@Test
	public void testCreatePlaylist() throws HandledException {
		AnnouncementPlaylistDto anndto = new AnnouncementPlaylistDto();
		anndto.setId(42l);
		anndto.setFileName("new file");
		AnnouncementPlaylist publicAnnouncement = announcementservice.playlistDtoToEntity(anndto);
		announcementPlaylistRepo.save(publicAnnouncement);
		Assertions.assertNotNull(publicAnnouncement);

	}

	@Test
	public void testGetSingleAnnouncement() {

		PublicAnnouncement pAData = publicAnnRepo.getById(19L);
		if (pAData == null) {
			Assert.assertNull(pAData);
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Public Announcement File not found");

		} else {
			Assert.assertNotNull(pAData);

		}

	}

	@Test
	public void testDeleteFile() throws HandledException {

		AnnouncementPlaylist pAData = announcementPlaylistRepo.getById(48L);
		if (pAData == null) {

			Assertions.assertNull(pAData);
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Public Announcement File not found in Database");
		} else {
			announcementPlaylistRepo.delete(pAData);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			Assert.assertNotNull(response);
		}

	}

	@Test
	public void testMoveUp() throws HandledException {

		List<Long> ids = new ArrayList<Long>();
		ids.add(20L);
		ids.add(40L);
		int flag = 0;
		for (int i = 0; i < ids.size(); i++) {

			flag++;

			if (ids.get(i).equals(20L)) {

				break;
			}

		}

		List<AnnouncementPlaylist> announcementPlaylist = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {

			Optional<AnnouncementPlaylist> announcementByid = announcementPlaylistRepo.findById(ids.get(i));
			if (announcementByid == null) {
				System.out.println("file not found");
			} else {
				announcementPlaylist.add(announcementByid.get());

			}

		}
		Assert.assertNotNull(announcementPlaylist);
		;

	}

	@Test
	public void testMoveDown() throws HandledException {
		List<Long> ids = new ArrayList<Long>();
		ids.add(20L);
		ids.add(40L);
		int flag = 0;

		for (int i = 0; i < ids.size(); i++) {

			flag++;

			if (ids.get(i) == 20L) {

				break;
			}

		}

		Collections.swap(ids, flag, flag - 1);

		List<AnnouncementPlaylist> announcementPlaylist = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {

			Optional<AnnouncementPlaylist> announcementByid = announcementPlaylistRepo.findById(ids.get(i));
			announcementPlaylist.add(announcementByid.get());
		}
		Assert.assertNotNull(announcementPlaylist);

	}

//		
//	////////////////////////////////////////CustomResponse///////////////////////////////////////////

	@Test
	public void testEntityToDto() {
		PublicAnnouncement publicAnn = new PublicAnnouncement();
		publicAnn.setId(19l);
		publicAnn.setFileName("abc");
		publicAnn.setFileLocation("C://Users//Downloads123456");
		publicAnn.setMessageType("old message");
		publicAnn.setFileUrl("e:/abc");
		publicAnn.setCreatedAt(dateTime);
		publicAnn.setCreatedBy("1");
		publicAnn.setUpdatedAt(dateTime);
		publicAnn.setUpdatedBy(null);
		PublicAnnouncementDto Dto = new PublicAnnouncementDto();
		Dto.setId(publicAnn.getId());
		Dto.setFileName(publicAnn.getFileName());
		Dto.setFileLocation(publicAnn.getFileLocation());
		Dto.setMessageType(publicAnn.getMessageType());
		Dto.setFileUrl(publicAnn.getFileUrl());
		Assert.assertEquals(publicAnn.getFileLocation(), Dto.getFileLocation());

	}

	@Test
	public void testDtoToEntity() {
		PublicAnnouncementDto Dto = new PublicAnnouncementDto();
		Dto.setId(21l);
		Dto.setFileName("XYZ");
		Dto.setFileLocation("C://Users//Downloads123456");
		Dto.setFileUrl("e:/xyz");
		Dto.setMessageType("namaskar");
		PublicAnnouncement publicAnn = new PublicAnnouncement();
		publicAnn.setId(Dto.getId());
		publicAnn.setFileName(Dto.getFileName());
		publicAnn.setFileLocation(Dto.getFileLocation());
		publicAnn.setMessageType(Dto.getMessageType());
		publicAnn.setCreatedAt(dateTime);
		publicAnn.setUpdatedAt(dateTime);
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		publicAnn.setFileUrl(Dto.getFileUrl());
		Assert.assertEquals(publicAnn.getFileLocation(), Dto.getFileLocation());

	}

	////////////////////////////////////// Playlist Dto
	////////////////////////////////////// Function/////////////////////////////////////////////////
	@Test
	public void testPlaylistEntityToDto() {
		AnnouncementPlaylist publicAnn = new AnnouncementPlaylist();
		publicAnn.setId(21l);
		publicAnn.setFileName("stock");
		publicAnn.setCreatedAt(dateTime);
		publicAnn.setUpdatedAt(dateTime);
		publicAnn.setCreatedBy("1");
		publicAnn.setUpdatedBy("2");
		AnnouncementPlaylistDto Dto = new AnnouncementPlaylistDto();
		Dto.setId(publicAnn.getId());
		Dto.setFileName(publicAnn.getFileName());
		Assert.assertEquals(publicAnn.getFileName(), Dto.getFileName());

	}

	@Test
	public void testPlaylistDtoToEntity() {
		AnnouncementPlaylistDto Dto = new AnnouncementPlaylistDto();
		Dto.setId(22l);
		Dto.setFileName("train");
		AnnouncementPlaylist publicAnn = new AnnouncementPlaylist();
		publicAnn.setId(Dto.getId());
		publicAnn.setFileName(Dto.getFileName());
		publicAnn.setCreatedAt(dateTime);
		publicAnn.setUpdatedAt(dateTime);
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		Assert.assertEquals(publicAnn.getFileName(), Dto.getFileName());
	}

}
