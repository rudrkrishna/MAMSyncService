package com.example.MAMSyncService.controller;

import com.example.MAMSyncService.resources.Logger;
import com.example.MAMSyncService.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    private SyncService syncService;
    private Logger logger;

    @Autowired
    public SyncController(SyncService syncService) {
        this.syncService = syncService;
        this.logger=new Logger(this.syncService);
    }

    @GetMapping("/startSync")
    public void startFileSync() throws Exception {
        logger.main(null);
        //syncService.SyncFiles(null);
    }
}
