package com.example.MAMSyncService.service;

import com.example.MAMSyncService.dto.Assets;
import com.example.MAMSyncService.respository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class SyncService {

    private final AssetsRepository assetsRepository;

    @Autowired
    public SyncService(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    public void SyncFiles(ArrayList<String> arr) throws Exception {

        ArrayList<String> array= updateData(arr);
        Iterable<Assets> it = assetsRepository.findAll();
        Iterator<Assets> iterator=it.iterator();
        while (iterator.hasNext()) {
            Assets asset = iterator.next();
            System.out.println(asset.getFilename());
        }
       if(!(isExist(arr.get(1), it.iterator())) && isNotDuplicate(array.get(1))){
            Assets assets = new Assets();
            assets.setParentId(Integer.valueOf(1));
            assets.setType(array.get(0));
            assets.setFilename(array.get(1));
            assets.setPath("/");
            assets.setMimetype(array.get(2));
            assets.setCreationDate(Integer.valueOf(1666175574));
            assets.setModificationDate(Integer.valueOf(1666175574));
            assets.setUserOwner(Integer.valueOf(2));
            assets.setCustomSettings("a:0:{}");
            assets.setUserModification(Integer.valueOf(2));
            assets.setHasMetaData(Byte.valueOf(array.get(3)));
            assets.setVersionCount(Integer.valueOf(1));
            assetsRepository.save(assets);
        }

    }

    private static ArrayList<String> updateData(ArrayList<String> arr) throws Exception {

        ArrayList<String> data = new ArrayList<>();
        String[] filePath = arr.get(0).split("/");
        String type=filePath[filePath.length-1].split("[.]")[filePath[filePath.length-1].split("[.]").length-1];
        String fileName=filePath[filePath.length-1].split("[.]")[filePath[filePath.length-1].split("[.]").length-2];
        /*if(filePath[filePath.length-1].lastIndexOf('.')-filePath[filePath.length-1].indexOf('.')==0){
            type=filePath[filePath.length-1].split("[.]")[1];
        }else{
            type=filePath[filePath.length-1].split("[.]")[filePath[filePath.length-1].split("[.]").length-2];
        }*/
        if(type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("png")){
            data.add("image");
            data.add(filePath[filePath.length-1]);
            data.add(data.get(0)+"/"+type);
        }
        if(type.equalsIgnoreCase("mp4")){
            data.add("video");
            data.add(fileName+"."+type);
            data.add(data.get(0)+"/mp4");
            data.add("0");
            }
       /* if(type.equalsIgnoreCase("mov")){
            data.add("video");
            data.add(filePath[filePath.length-1]);
            data.add(data.get(0)+"/quicktime");
            data.add("0");
        }*/
        if(type.equalsIgnoreCase("txt")){
            data.add("text");
            data.add(fileName+"."+type);
            data.add(data.get(0)+"/"+type);
            data.add("2");
        }
        /*if(type.equalsIgnoreCase("pdf")){
            data.add("application");
            data.add(filePath[filePath.length-1]);
            data.add(data.get(0)+"/"+type);
        }*/
        /*if(type.equalsIgnoreCase("docx")){
            data.add("application");
            data.add(filePath[filePath.length-1]);
            data.add(data.get(0)+"/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }*/

    return data;
    }

    public static boolean isExist(String fileName, Iterator<Assets> iterator) {

        int count = 0;
        while (iterator.hasNext()) {
            Assets asset = iterator.next();
            if (asset.getFilename().equalsIgnoreCase(fileName)) {
                count++;
                break;
            }
        }

        if(count>0)
            return true;
        return false;
    }


    private static boolean isNotDuplicate(String fileName){

        int count=0;
        for(int i=0;i<fileName.length();i++){
            if(Character.isDigit(fileName.charAt(i))){
                count++;
            }
        }

        if(count>=15)
            return false;
        return true;
    }
}
