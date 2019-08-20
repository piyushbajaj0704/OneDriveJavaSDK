package de.tuberlin.onedrivesdk.download;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import de.tuberlin.onedrivesdk.OneDriveException;
import de.tuberlin.onedrivesdk.OneDriveSDK;
import de.tuberlin.onedrivesdk.file.OneFile;
import de.tuberlin.onedrivesdk.folder.OneFolder;
import de.tuberlin.onedrivesdk.common.TestSDKFactory;
import de.tuberlin.onedrivesdk.downloadFile.OneDownloadFile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConcreteOneDownloadIntegrationTest {

	@Test
    public void simpleDownloadTest() throws InstantiationException,IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, SecurityException, OneDriveException, IOException, InterruptedException {
		OneDriveSDK api = TestSDKFactory.getInstance();

		OneFolder folder = api.getFolderByPath("/templates");
		File directory = new File(folder.getName());
		if (!directory.exists()){
			directory.mkdirs();
		}
		List<OneFolder> folders = folder.getChildFolder();
		for(OneFolder main_folders : folders) {
			String filepath = System.getProperty("user.dir") + "/templates/" + main_folders.getName();   // LATER change to user.home
			// String templatesFolder = String.format("%s/FireEye, Inc/CAPS Templates - Documents/General/templates", System.getProperty("user.home"));
			File directory1 = new File(filepath);
			if (!directory1.exists()){
				directory1.mkdirs();
			}
			List<OneFolder> product_folders = main_folders.getChildFolder();
			for(OneFolder product_folder : product_folders) {
				String filepath1 = System.getProperty("user.dir") + "/templates/" + main_folders.getName() + "/" + product_folder.getName();
				File directory2 = new File(filepath1);
				if (!directory2.exists()){
					directory2.mkdirs();
				}
				List<OneFile> files = product_folder.getChildFiles();
				for (OneFile file : files){
					String filepath2 = System.getProperty("user.dir") + "/templates/" + main_folders.getName() + "/" +
							product_folder.getName() + "/" + file.getName();
					File localCopy = new File(filepath2);
					OneDownloadFile f = file.download(localCopy);
					f.startDownload();
					//HashCode code = Files.hash(localCopy, Hashing.sha1());
					//assertEquals(file.getName() + " mismatch", code.toString().toUpperCase(), file.getSHA1Hash());
				}
			}
		}
	}

}
