package com.example.MAMSyncService.resources;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.example.MAMSyncService.service.SyncService;
import com.opencsv.CSVWriter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;


public class FolderInspect {

    // A hardcoded path to a folder you are monitoring .
    public static final String FOLDER =
            //"/Users/rudrkrishna/Desktop/folderTest";
           "/var/www/pimcore/public/var/assets";

    public static final String csvFilePath=
            //"/Users/rudrkrishna/Desktop/fileData.csv";
            "/home/ubuntu/fileData.csv";

    public static Logger logger;

    public static SyncService syncService;

    @Autowired
    public FolderInspect(SyncService syncService, Logger log) {
        FolderInspect.syncService = syncService;
        FolderInspect.logger= log;
    }

    public static void main(String[] args) throws Exception {
        // The monitor will perform polling on the folder every 5 seconds
        final long pollingInterval = 120 * 1000;

        File folder = new File(FOLDER);
        File csvFile = new File(csvFilePath);


        if(!(csvFile.exists())){
            csvFile.createNewFile();
        }


        if (!folder.exists()) {
            logger.severe("Directory not found: " + FOLDER);
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + FOLDER);
        }

        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor =
                new FileAlterationMonitor(pollingInterval);

        FileAlterationListener listener = new FileAlterationListenerAdaptor() {

            // Is triggered when a file is created in the monitored folder

            final boolean append = true;

            final boolean autoFlush = true;
            // Create the FileOutputStream object in append mode.
            final FileOutputStream fos = new FileOutputStream("/home/ubuntu/logger.txt", append);
            final PrintStream ps = new PrintStream(fos, autoFlush);



            @Override
            public void onFileCreate(File file) {

                try {

                   CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true));

                    // "file" is the reference to the newly created file
                    // To redirect the terminal output to a logfile for extraction of data
                    // Instantiating the PrintStream class


                    System.setOut(ps);
                    System.out.println("File created: " + file.getCanonicalPath());
                    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    System.out.println("creationTime: " + attr.creationTime());
                    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

                    String[] data = new String[]{file.getCanonicalPath(), String.valueOf(attr.creationTime()), String.valueOf(attr.lastModifiedTime())};
                    writer.writeNext(data, false);

                    logger.info("File created: " + file.getCanonicalPath());
                    logger.info("creationTime: " + attr.creationTime());
                    logger.info("lastModifiedTime: " + attr.lastModifiedTime());

                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(file.getCanonicalPath());
                    arr.add(String.valueOf(attr.creationTime()));
                    arr.add(String.valueOf(attr.lastModifiedTime()));
                    arr.add(String.valueOf(attr.fileKey()));
                    syncService.SyncFiles(arr, logger);
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace(System.err);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();

    }
}
