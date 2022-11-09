package com.example.MAMSyncService.controller;

import com.example.MAMSyncService.resources.FolderInspect;
import com.example.MAMSyncService.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class SyncController {

    private final SyncService syncService;
    private final FolderInspect folder;
    private final Logger logger;

    @Autowired
    public SyncController(SyncService syncService) {
        this.syncService = syncService;
        this.logger=Logger.getLogger(FolderInspect.class.getName());
        this.folder=new FolderInspect(this.syncService, logger);

    }



    @GetMapping("/startSync")
    public void startFileSync() throws Exception {
        FolderInspect.main(null);
    }
}
