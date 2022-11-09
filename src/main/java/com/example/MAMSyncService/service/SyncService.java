package com.example.MAMSyncService.service;

import com.example.MAMSyncService.dto.Assets;
import com.example.MAMSyncService.respository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

@Service
public class SyncService {

    private static Logger logger;

    private final AssetsRepository assetsRepository;

    @Autowired
    public SyncService(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    public void SyncFiles(ArrayList<String> arr, Logger log) {
        logger=log;
        ArrayList<String> array= fileProps(arr);
        logger.info("File Properties Extracted");
        Iterable<Assets> it = assetsRepository.findAll();
       if(!(isExist(arr.get(1), it.iterator())) && isNotDuplicate(array.get(1))){
            Assets assets = new Assets();
            assets.setParentId(Integer.valueOf(1));
            assets.setType(array.get(0));
            assets.setFilename(array.get(1));
            assets.setPath("/");
            assets.setMimetype(array.get(2));
            assets.setCreationDate(Integer.valueOf(1666175574));
            assets.setModificationDate(Integer.valueOf(1666175574));
            assets.setUserOwner(Integer.valueOf(array.get(3)));
            assets.setCustomSettings("a:0:{}");
            assets.setUserModification(Integer.valueOf(array.get(3)));
            assets.setHasMetaData(Byte.valueOf(array.get(4)));
            assets.setVersionCount(Integer.valueOf(array.get(5)));
            assetsRepository.save(assets);
            logger.info("File Data Updated in the Data Base");
        }

    }

    private static ArrayList<String> fileProps(ArrayList<String> arr){

        ArrayList<String> data = new ArrayList<>();
        String[] filePath = arr.get(0).split("/");
        String type=filePath[filePath.length-1].split("[.]")[filePath[filePath.length-1].split("[.]").length-1];
        String fileName=filePath[filePath.length-1].split("[.]")[filePath[filePath.length-1].split("[.]").length-2];

       /* if(type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("png")){
            data.add("image");
            data.add(fileName+"."+type);
            data.add(data.get(0)+"/"+type);
            data.add("2");
            data.add("0");
            data.add("1");
        }*/
        if(type.equalsIgnoreCase("mp4")){
            data.add("video");
            data.add(fileName+"."+type);
            data.add(data.get(0)+"/mp4");
            data.add("2");
            data.add("0");
            data.add("1");
            }
       if(type.equalsIgnoreCase("mov")){
            data.add("video");
            data.add(filePath[filePath.length-1]);
            data.add(data.get(0)+"/quicktime");
           data.add("2");
            data.add("0");
           data.add("1");
        }
        if(type.equalsIgnoreCase("txt")){
            data.add("text");
            data.add(fileName+"."+type);
            data.add(data.get(0)+"/"+type);
            data.add("2");
            data.add("0");
            data.add("1");
        }
        if(type.equalsIgnoreCase("pdf")){
            data.add("document");
            data.add(filePath[filePath.length-1]);
            data.add("application"+"/"+type);
            data.add("2");
            data.add("0");
            data.add("1");
        }
        if(type.equalsIgnoreCase("docx")){
            data.add("document");
            data.add(filePath[filePath.length-1]);
            data.add("application"+"/vnd.openxmlformats-officedocument.wordprocessingml.document");
            data.add("2");
            data.add("0");
            data.add("1");
        }
        if(type.equalsIgnoreCase("pptx")){
            data.add("document");
            data.add(filePath[filePath.length-1]);
            data.add("application"+"/vnd.openxmlformats-officedocument.presentationml.presentation");
            data.add("2");
            data.add("0");
            data.add("1");
        }

    return data;
    }

    private static boolean isExist(String fileName, Iterator<Assets> iterator) {

        int recordCount = 0;
        while (iterator.hasNext()) {
            Assets asset = iterator.next();
            if (asset.getFilename().equalsIgnoreCase(fileName)) {
                recordCount++;
                break;
            }
        }
        return recordCount != 0;
    }


    private static boolean isNotDuplicate(String fileName){

        int digitCount=0;
        for(int i=0;i<fileName.length();i++){
            if(Character.isDigit(fileName.charAt(i))){
                digitCount++;
            }
        }

        return digitCount < 15;
    }
}
